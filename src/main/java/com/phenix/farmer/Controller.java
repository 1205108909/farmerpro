package com.phenix.farmer;

import com.google.common.collect.ImmutableList;
import com.lmax.disruptor.*;
import com.lmax.disruptor.dsl.Disruptor;
import com.phenix.cache.TimeSeriesDataCache;
import com.phenix.data.*;
import com.phenix.farmer.config.Environment;
import com.phenix.farmer.event.*;
import com.phenix.farmer.signal.*;
import com.phenix.orderbook.OrderBook;
import com.phenix.orderbook.OrderFlow;
import com.phenix.orderbook.Transaction;
import com.phenix.util.Alert;
import com.phenix.util.StoredSignalUtil;
import lombok.Getter;
import net.openhft.affinity.AffinityLock;
import org.jdom2.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;

public class Controller implements EventFactory<EngineEventWrapper>, EventHandler<EngineEventWrapper>, LifecycleAware {
	private AffinityLock cpuLock = null;
	private long eventCounter = 0;

	private static final transient Logger LOGGER = LoggerFactory.getLogger(Controller.class);
	private final ExecutorService executorService;
	private final Disruptor<EngineEventWrapper> disruptor;
	private Set<Security> securities = new HashSet<>();

	@Getter
	private final Consumer<IEngineEvent> orderBookUpdateEventConsumer = (e) -> handleOrderBookUpdateEvent(e);
	@Getter
	private final Consumer<IEngineEvent> transactionUpdateEventConsumer = (e) -> handleTransactionUpdateEvent( e);
	@Getter
	private final Consumer<IEngineEvent> kDataUpdateEventConsumer = (e) -> handleKDataUpdateEvent(e);
	@Getter
	private final Consumer<IEngineEvent> orderFlowUpdateEventConsumer = (e) -> handleOrderFlowUpdateEvent(e);
	@Getter
	private final Consumer<IEngineEvent> marketOpenEventConsumer = (e) -> handleMarketOpenEvent(e);
	@Getter
	private final Consumer<IEngineEvent> executionReportEventConsumer = (e) -> handleExecutionReportEvent(e);
	@Getter
	private final Consumer<IEngineEvent> dailySettleEventConsumer = (e) -> handleDailySettleEvent(e);
	@Getter
	private final Consumer<IEngineEvent> adminEventConsumer = (e) -> handleAdminEvent(e);
	@Getter
	private final Consumer<IEngineEvent> persistSignalEventConsumer = (e) -> handlePersistSignalEvent(e);
	@Getter
	private final Consumer<IEngineEvent> syncTaskEventConsumer = (e) -> handleSyncTaskEvent(e);
	@Getter
	private final Consumer<IEngineEvent> closeOrderEventConsumer = (e) -> handleCloseOrderEvent(e);
	@Getter
    private final Consumer<IEngineEvent> storedSignalEventConsumer = (e) -> handleStoredSignalEvent(e);

	private final String errorMsgHeadHandlingEvent;
	private final Map<Class<? extends ISignal>, ISignal> signalMap;

	private List<ISignal> signals;
	@Getter
	private boolean ready;

	private TimeSeriesDataCache<Transaction> transactions = new TimeSeriesDataCache<>();	
	private TimeSeriesDataCache<OrderBook> orderBooks = new TimeSeriesDataCache<>();
	private TimeSeriesDataCache<OrderFlow> orderFlows = new TimeSeriesDataCache<>();

	@Getter
	private final int id;
	@Getter
	private final int eventBufferSize;
	@Getter
	private final PositionBalanceManager pbManager;
	@Getter
	private LocalDateTime timeNow;

	@SuppressWarnings("unchecked")
	public Controller(int id_, int eventBufferSize_, PositionBalanceManager positionBalanceManager_) {
		id = id_;
		pbManager = positionBalanceManager_;
		errorMsgHeadHandlingEvent = "Controller[" + id + "]: Error in Handling Event -- ";
		eventBufferSize = eventBufferSize_;
		executorService = Executors.newSingleThreadExecutor();
		signalMap = new HashMap<>();

		//TODO: Test Alternative Wait Strategies
		disruptor = new Disruptor<>(this, eventBufferSize, executorService);
		disruptor.handleExceptionsWith(new ExceptionHandler<Object>() {
			@Override
			public void handleOnStartException(Throwable ex_) {
			    LOGGER.error("Error on disruptor start up", ex_);
                Alert.fireAlert(Alert.Severity.Major, "Error on disruptor start up", ex_.toString());
                if(Environment.DEV == FarmerConfigManager.getInstance().getInstanceConfig().getEnvironment()) {
                    throw new RuntimeException(ex_);
                }
			}

			@Override
			public void handleOnShutdownException(Throwable ex_) {
                LOGGER.error("Error on disruptor shutdown", ex_);
                Alert.fireAlert(Alert.Severity.Major, "Error on disruptor shutdown", ex_.toString());
                if(Environment.DEV == FarmerConfigManager.getInstance().getInstanceConfig().getEnvironment()) {
                    throw new RuntimeException(ex_);
                }
			}

			@Override
			public void handleEventException(Throwable ex_, long sequence_, Object event_) {
				LOGGER.error("Error on handling event -- " +  event_.toString(), ex_);
				//ex_.printStackTrace();

				Alert.fireAlert(Alert.Severity.Major, errorMsgHeadHandlingEvent + ex_.getMessage(), ex_.toString());
				if(Environment.DEV == FarmerConfigManager.getInstance().getInstanceConfig().getEnvironment()) {
					throw new RuntimeException(ex_);
				}
			}
		});

		signals = initSignal(FarmerConfigManager.getInstance().getSignalElement(), positionBalanceManager_);
		disruptor.handleEventsWith(this);
 	}

 	public void init() {
		signals = initSignal(FarmerConfigManager.getInstance().getSignalElement(), pbManager);
		for(ISignal s : signals) {
			signalMap.put(s.getClass(), s);
		}
		ready = true;
	}

 	private List<ISignal> initSignal(Element signalNode_, PositionBalanceManager pbManager_) {
		List<ISignal> signals = new ArrayList<>(signalNode_.getChildren().size());
		try {
			for (Element e : signalNode_.getChildren()) {
				Class<?>[] clazz = new Class[]{Controller.class, PositionBalanceManager.class};
				ISignal s = (ISignal) Class.forName(e.getAttributeValue("class"))
						.getDeclaredConstructor(clazz)
						.newInstance(this, pbManager_);
				signals.add(s);
			}
		} catch (Exception e_) {
			throw new RuntimeException(e_);
		}

		return signals;
	}

	@Override
	public EngineEventWrapper newInstance() {
		return EngineEventWrapper.newInstance();
	}

	@Override
	public void onStart() {
		//LOGGER.info("Going to binding the thread to CPU");
		//cpuLock = AffinityLock.acquireLock();
		//LOGGER.info(String.format("Controller[%s] started and binded to CPU[%s].", id, cpuLock.cpuId()));
	}

	@Override
	public void onShutdown() {
		//LOGGER.info("Going to release the thread to CPU");
		//cpuLock.release();
		//LOGGER.info(String.format("Release done for Controller[%s].", id));
	}

	public LocalDate getTransactionDate() {
		//null means no security event is dispatched on this controller
		return null == timeNow ? null : timeNow.toLocalDate();
	}

	public void enqueueEvent(IEngineEvent event_) {
		//!!! do not handle event here, not thread safe
		RingBuffer<EngineEventWrapper> ringBuffer = disruptor.getRingBuffer();
		long sequence = ringBuffer.next();
		try {
			EngineEventWrapper event = ringBuffer.get(sequence);
			event.setEvent(event_);
		} finally {
			ringBuffer.publish(sequence);
		}

		long remainingCapacity = ringBuffer.remainingCapacity();
		if(remainingCapacity < 100) {
			throw new IllegalStateException("RingBuffer is almost full with remainingCapacity: " + remainingCapacity);
		}
		long eventCount = disruptor.getBufferSize() - remainingCapacity;
		eventCounter++;
		if(eventCounter >= 240 && eventCounter / 240 == 0) {
			LOGGER.info("Event " + event_.getClass() + " enqueued, controller [" + id + "] has totally " + eventCount + " events in queue, currentDate=" + getTransactionDate().toString());
		}

		if(eventCount > 100) {
			Alert.fireAlert(Alert.Severity.Info, "TooManyEventInQueue_Controller[" + id + "]", String.format("too many event in queue[%s] and eventSize=[%s]", id, eventCount));
		}
	}

	public List<ISignal> findSignals() {
		return ImmutableList.copyOf(signals);
	}

	public<T extends ISignal> T findSignal(Class<T> class_) {
		for (ISignal s : signals) {
			if(s.getClass() == class_) {
				return (T)s;
			}
		}
		return null;
	}

	public void work() {
		LOGGER.info(String.format("Start Controller[%s].", id));
		disruptor.start();
	}

	public void shutdown() {
		disruptor.shutdown();
		executorService.shutdown();
		LOGGER.info(String.format("Controller[%s] shutdown.", id));
	}

	public void reinitSignal() {
		signals.forEach(e -> e.reinit());
		transactions.clear();
		orderBooks.clear();
		orderFlows.clear();
		securities.clear();
	}

	@Override
	public void onEvent(EngineEventWrapper event_, long sequence_, boolean endOfBatch_) throws Exception {
//	    long time1 =  LocalDateTime.now().atZone(ZoneOffset.systemDefault()).toInstant().toEpochMilli();
		if(!SignalMode.isTestingMode(FarmerController.getInstance().getSignalMode())) {
			timeNow = LocalDateTime.now();
		}

		IEngineEvent event = event_.getEvent();
		event.handleEvent(event);
		event_.setEvent(null);
//		LOGGER.debug("handle Event spend: " + (LocalDateTime.now().atZone(ZoneOffset.systemDefault()).toInstant().toEpochMilli() - time1) );
	}

	public void handleOrderBookUpdateEvent(IEngineEvent e_) {
		OrderBook ob = ((OrderBookUpdateEvent)e_).getOrderBook();

		//update volume, all exchange
		if(SignalMode.BACK_TEST != FarmerController.getInstance().getSignalMode()) {
			OrderBook prevOb = getOrderBook(ob.getSecurity());
			if (prevOb != null) {
				double vol = ob.getAccVolume() - prevOb.getAccVolume();
				double turnover = ob.getAccTurnover() - prevOb.getAccTurnover();
				ob.setVolume(vol);
				ob.setTurnover(turnover);
			}
		}

		orderBooks.put(ob.getSecurity(), ob);
		securities.add(ob.getSecurity());

		if(SignalMode.isTestingMode(FarmerController.getInstance().getSignalMode())) {
			timeNow = ob.getDateTime();
		}

		signals.forEach(e -> e.handleOrderBookUpdateEvent(e_));
	}

	public void handleTransactionUpdateEvent(IEngineEvent e_) {
		Transaction t = ((TransactionUpdateEvent)e_).getTransaction();
		transactions.put(t.getSecurity(), t);	
		securities.add(t.getSecurity());

		if(SignalMode.isTestingMode(FarmerController.getInstance().getSignalMode())) {
			timeNow = t.getDateTime();
		}

		signals.forEach(e -> e.handleTransactionUpdateEvent(e_));
	}

	public void handleOrderFlowUpdateEvent(IEngineEvent e_) {
		OrderFlow t = ((OrderFlowUpdateEvent)e_).getOrderFlow();
		orderFlows.put(t.getSecurity(), t);
		securities.add(t.getSecurity());

		if(SignalMode.isTestingMode(FarmerController.getInstance().getSignalMode())) {
			timeNow = t.getDateTime();
		}

		signals.forEach(e -> e.handleOrderFlowUpdateEvent(e_));
	}

	public void handleKDataUpdateEvent(IEngineEvent e_) {
		if(SignalMode.isTestingMode(FarmerController.getInstance().getSignalMode())) {
			KLineData k = ((KDataUpdateEvent)e_).getKLineData();
			timeNow = LocalDateTime.of(k.getDate(), k.getTime());
		}

		signals.forEach(e -> e.handleKDataUpdateEvent(e_));
	}

	public void handleExecutionReportEvent(IEngineEvent e_) {
		signals.forEach(e -> e.handleExecutionReportEvent(e_));
	}

	public void handleAdminEvent(IEngineEvent e_) {
	}

	public void handleMarketOpenEvent(IEngineEvent e_) {
		LOGGER.info(String.format("handling MarketOpenEvent" + ((MarketOpenEvent)e_).getDate()));
		signals.forEach(e -> e.handleMarketOpenEvent(e_));
	}

	public void handleDailySettleEvent(IEngineEvent e_) {
		LOGGER.info(String.format("handling DailySettleEvent" + ((DailySettleEvent)e_).getDate()));
		signals.forEach(e -> e.handleDailySettleEvent(e_));

		//clear unused data
		orderBooks.clear();
		transactions.clear();
		orderFlows.clear();
	}

	public void handlePersistSignalEvent(IEngineEvent e_) {
        LOGGER.info(String.format("handling PersistSignalEvent"));
		PersistSignalEvent e = (PersistSignalEvent)e_;
		for(ISignal s : signals) {
			s.persistSignal(e.getPath());
		}
	}

	public void handleSyncTaskEvent(IEngineEvent e_) {
		SyncTaskEvent se = (SyncTaskEvent)e_;
		se.getTask().run();
	}

	public void handleCloseOrderEvent(IEngineEvent e_) {
		CloseOrderEvent e = (CloseOrderEvent) e_;
		for(ISignal s : signals) {
			s.closeOrder(e.getSec());
		}
	}

	public void handleStoredSignalEvent(IEngineEvent e_){
        LOGGER.info(String.format("handling StoredSignalEvent"));
	    List<Order> orders = getOrders();
        StoredSignalUtil.storedAllSignals(orders);
    }

	public OrderBook getOrderBook(Security s_) {
		return orderBooks.getLast(s_);
	}

	public OrderFlow getOrderFlow(Security s_) {
		return orderFlows.getLast(s_);
	}

	public OrderBook getPreviousOrderBook(Security s_) {
		return orderBooks.getPrevious(s_);
	}

	public Transaction getTransaction(Security s_) {
		return transactions.getLast(s_);
	}

	public Transaction getPreTransaction(Security s_) {
		return transactions.getPrevious(s_);
	}

	public List<ISignal> getSignals() {
		return ImmutableList.copyOf(signals);
	}

	public ISignal getSignal(Class<?> type_) {
		for(ISignal s : signals) {
			if(type_.equals(s.getClass())) {
				return s;
			}
		}
		return null;
	}

	public List<Order> getOrders() {
		List<Order> orders = new ArrayList<>();
		for(ISignal s : signals) {
			orders.addAll(s.getOrders());
		}
		return orders;
	}

	public Order getOrder(String orderId_) {
		Order o;
		for(ISignal s : signals) {
			o = s.getOrder(orderId_);
			if(o != null)
				return o;
		}
		return null;
	}

	public List<OrderBook> getOrderBooks() {
		List<OrderBook> obs = new ArrayList<>();
		for(Security s : securities) {
			obs.add(orderBooks.getLast(s));
		}
		return obs;
	}

	public boolean future2IndexPxValidationCheck(Security sec_, IndexFuture future_, int timeSpanInSecs_) {
		OrderBook obs = getOrderBook(sec_);
		OrderBook obi = getOrderBook(future_);
		if(obs == null || obi == null)
			return false;
		int timeSpan = obi.getTimeInSecs() - obs.getTimeInSecs();
		if(Math.abs(timeSpan) > timeSpanInSecs_) {
			LOGGER.warn("OrderBook Delay = " + timeSpan + "s, Future OrderBook Time:[" + future_.getSymbol() + ", time = " + obi.getTime()
					+ "], Index OrderBook Time:[" + sec_.getSymbol() + ", time = " + obs.getTime() + "]");
			return false;
		} else {
			return true;
		}
	}

	public List<OrderBook> getOrderBooks(Security sec_) {
		return orderBooks.get(sec_);
	}

	public Set<Security> getSecurities() {
		return Collections.unmodifiableSet(securities);
	}

	public boolean allowAutoOrderHandling() {
		return SignalMode.MANUAL != FarmerController.getInstance().getSignalMode();
	}

	public void closeOrder(Security s_) {
		enqueueEvent(new CloseOrderEvent(s_));
	}

}
