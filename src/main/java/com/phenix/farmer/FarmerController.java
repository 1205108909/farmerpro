package com.phenix.farmer;

import com.phenix.data.KLineData;
import com.phenix.data.Order;
import com.phenix.data.Security;
import com.phenix.farmer.event.*;
import com.phenix.farmer.signal.ISignal;
import com.phenix.farmer.signal.PositionBalanceManager;
import com.phenix.farmer.signal.SignalMode;
import com.phenix.orderbook.OrderBook;
import com.phenix.orderbook.OrderFlow;
import com.phenix.orderbook.Transaction;
import com.phenix.util.StoredSignalUtil;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Consumer;

/**
 * TODO
 * 1. add a Disruptor here replicate all events to make sure any prod issue re-producible
 * 2. add at least 2 Event Handler
 * a) copy or logging the events
 * b）assign the event to different controllers
 */
public final class FarmerController {
    private final static transient Logger LOGGER = LoggerFactory.getLogger(FarmerController.class);

    //private TDFClient tdfClient = new TDFClient();
    private int noOfController;
    private List<Controller> controllers;
    @Getter
    private PositionBalanceManager positionBalanceManager;
    private final AtomicBoolean stopFlag;
    @Getter
    private SignalMode signalMode;

    @Getter
    private final static FarmerController instance = new FarmerController();
    @Getter
    private final Consumer<IEngineEvent> wakeupEventConsumer = (e) -> handleWakeUpEvent(e);
    @Getter
    private final Consumer<IEngineEvent> adminEventConsumer = (e) -> handleAdminEvent(e);
    @Getter
    private final Consumer<IEngineEvent> evaluationEventConsumer = (e) -> handleEvaluationEvent(e);
    @Getter
    private final Consumer<IEngineEvent> publishEventConsumer = (e) -> handlePublishEvent(e);
    @Getter
    private final Consumer<IEngineEvent> orderBookUpdateEventConsumer = (e) -> handleOrderBookUpdateEvent(e);
    @Getter
    private final Consumer<IEngineEvent> transactionUpdateEventConsumer = (e) -> handleTransactionUpdateEvent(e);
    @Getter
    private final Consumer<IEngineEvent> kDataUpdateEventConsumer = (e) -> handleKDataUpdateEvent(e);
    @Getter
    private final Consumer<IEngineEvent> orderFlowUpdateEventConsumer = (e) -> handleOrderFlowUpdateEvent(e);
    @Getter
    private final Consumer<IEngineEvent> executionReportEventConsumer = (e) -> handleExecutionReportEvent(e);
    @Getter
    private final Consumer<IEngineEvent> syncTaskEventConsumer = (e) -> handleSyncTaskEvent(e);
    @Getter
    private final Consumer<IEngineEvent> persistSignalEventConsumer = (e) -> handlePersistSignalEvent(e);
    @Getter
    private final Consumer<IEngineEvent> CloseOrderEventConsumer = (e) -> handleCloseOrderEvent(e);
    @Getter
    private final Consumer<IEngineEvent> timeSeriesDataUpdateEventConsumer = (e) -> handleTimeSeriesDataUpdateEvent(e);
    @Getter
    private final Consumer<IEngineEvent> marketOpenEventConsumer = (e) -> handleMarketOpenEvent(e);
    @Getter
    private final Consumer<IEngineEvent> dailySettleEventConsumer = (e) -> handleDailySettleEvent(e);
    @Getter
    private final Consumer<IEngineEvent> storedSignalEvent = (e) -> handleStoredSignalEvent(e);

    private FarmerController(int noOfController_, int eventBufferSize_) {
        stopFlag = new AtomicBoolean(false);
        noOfController = noOfController_;
        controllers = new ArrayList<>();
        positionBalanceManager = new PositionBalanceManager(noOfController_, FarmerConfigManager.getInstance().getPbConfig());

        for (int i = 0; i < noOfController_; i++) {
            final Controller controller = new Controller(i, eventBufferSize_, positionBalanceManager);
            controllers.add(controller);
        }

        positionBalanceManager.init();
    }

    private FarmerController() {
        this(FarmerConfigManager.getInstance().getInstanceConfig().getNumOfWorker(), FarmerConfigManager.getInstance().getInstanceConfig().getEventBufferSize());
    }

    private void init() {
		/*Executors.newSingleThreadScheduledExecutor().scheduleAtFixedRate(new Runnable() {
			@Override
			public void run() {

			}
		}, 10, 60, TimeUnit.SECONDS);*/
        // enqueue an publishEvent to publish latested data after market close
    }

    public Controller getController(IEngineEvent event_) {
        return controllers.get(getControllerNo(event_));
    }

    public int getControllerNo(IEngineEvent event_) {
        //return Math.abs(Objects.hash(event_.hashCode())) % noOfController;
        return Math.abs(event_.hashCode()) % noOfController;
    }

    public void enqueueEvent(IEngineEvent event_) {
        //LOGGER.info("Dispatching Event: " + event_);
        event_.handleEvent(event_);
    }

    public void handleOrderBookUpdateEvent(IEngineEvent e_) {
        OrderBookUpdateEvent e = (OrderBookUpdateEvent) e_;
        FarmerDataManager.getInstance().updateOrderBook(e.getOrderBook());

        Controller c = getController(e_);
        e_.setConsumer(c.getOrderBookUpdateEventConsumer());
        c.enqueueEvent(e_);
    }

    public void handleOrderFlowUpdateEvent(IEngineEvent e_) {
        Controller c = getController(e_);
        e_.setConsumer(c.getOrderFlowUpdateEventConsumer());
        c.enqueueEvent(e_);
    }


    public void handleTimeSeriesDataUpdateEvent(IEngineEvent e_) {
        TimeSeriesDataUpdateEvent e = (TimeSeriesDataUpdateEvent) e_;
        if (e.getData() instanceof OrderBook)
            handleOrderBookUpdateEvent(new OrderBookUpdateEvent((OrderBook) e.getData()));
        else if (e.getData() instanceof OrderFlow)
            handleOrderFlowUpdateEvent(new OrderFlowUpdateEvent((OrderFlow) e.getData()));
        else if (e.getData() instanceof Transaction)
            handleTransactionUpdateEvent(new TransactionUpdateEvent((Transaction) e.getData()));
        else if (e.getData() instanceof KLineData)
            handleKDataUpdateEvent(new KDataUpdateEvent((KLineData) e.getData()));
    }

    public void handleTransactionUpdateEvent(IEngineEvent e_) {
        Controller c = getController(e_);
        e_.setConsumer(c.getTransactionUpdateEventConsumer());
        c.enqueueEvent(e_);
    }

    public void handleKDataUpdateEvent(IEngineEvent e_) {
        Controller c = getController(e_);
        e_.setConsumer(c.getKDataUpdateEventConsumer());
        c.enqueueEvent(e_);
    }

    public void handleExecutionReportEvent(IEngineEvent e_) {
        Controller c = getController(e_);
        e_.setConsumer(c.getExecutionReportEventConsumer());
        c.enqueueEvent(e_);
    }

    public void handleMarketOpenEvent(IEngineEvent e_) {
        LocalDate d = ((MarketOpenEvent) e_).getDate();
        positionBalanceManager.handleMarketOpen(d);

        //this is a broad cast event, copy and publish to all controller
        controllers.forEach(e -> {
            final MarketOpenEvent moe = new MarketOpenEvent(d);
            moe.setConsumer(e.getMarketOpenEventConsumer());
            e.enqueueEvent(moe);
        });
    }

    public void handleDailySettleEvent(IEngineEvent e_) {
        LocalDate d = ((DailySettleEvent) e_).getDate();

        //this is a broad cast event, copy and publish to all controller
        controllers.forEach(e -> {
            final DailySettleEvent moe = new DailySettleEvent(d);
            moe.setConsumer(e.getDailySettleEventConsumer());
            e.enqueueEvent(moe);
        });

        positionBalanceManager.handleDailySettle(d);
    }

    public void handleWakeUpEvent(IEngineEvent e_) {
    }

    public void handleAdminEvent(IEngineEvent e_) {
        AdminEvent e = (AdminEvent) e_;

    }

    public void handleEvaluationEvent(IEngineEvent e_) {
    }

    public void handlePublishEvent(IEngineEvent e_) {
    }

    public void handleSyncTaskEvent(IEngineEvent e_) {
        Controller c = getController(e_);
        e_.setConsumer(c.getSyncTaskEventConsumer());
        c.enqueueEvent(e_);
    }

    public void handleCloseOrderEvent(IEngineEvent e_) {
        Controller c = getController(e_);
        e_.setConsumer(c.getCloseOrderEventConsumer());
        c.enqueueEvent(e_);
    }

    public void handlePersistSignalEvent(IEngineEvent e_) {
        LOGGER.info(String.format("handling PersistSignalEvent"));
        PersistSignalEvent e = (PersistSignalEvent) e_;
        Path p = Paths.get(e.getPath());
        if (!Files.exists(p)) {
            try {
                Files.createDirectories(p);
            } catch (IOException ex_) {
                LOGGER.error(ex_.getMessage(), ex_);
                throw new RuntimeException(ex_);
            }
        }

        for (Controller c : controllers) {
            final PersistSignalEvent pse = new PersistSignalEvent(e.getPath());
            pse.setConsumer(c.getPersistSignalEventConsumer());
            c.enqueueEvent(pse);
        }
    }

    public void handleStoredSignalEvent(IEngineEvent e_){
        LOGGER.info(String.format("handling StoredSignalEvent"));
        StoredSignalEvent e = (StoredSignalEvent) e_;
        for (Controller c : controllers) {
            StoredSignalEvent sse = new StoredSignalEvent();
            sse.setConsumer(c.getStoredSignalEventConsumer());
            c.enqueueEvent(sse);
        }
    }

    public void storeSignal2DB(){
        LOGGER.info(String.format("Going store singals to database"));
        List<Order> orders = getOrders();
        StoredSignalUtil.storedAllSignals(orders);

    }

    public void setSignalMode(SignalMode m_) {
        SignalMode sm = signalMode;
        signalMode = m_;
        LOGGER.info("Signal Mode[" + sm + "] is updated to be [" + signalMode + "]");
    }

    public void work() {
        init();
        controllers.forEach(e -> e.work());
        LOGGER.info(String.format("%s started to work with [%s] controllers.", getClass().getSimpleName(), noOfController));
    }

    public void shutdown() {
        controllers.forEach(e -> e.shutdown());
    }

    public void reinitSignal() {
        controllers.forEach(e -> e.reinitSignal());
    }

    public int getNumOfController() {
        return noOfController;
    }

    public List<ISignal> getSignals() {
        List<ISignal> signals = new ArrayList<>();
        for (Controller c : controllers) {
            signals.addAll(c.getSignals());
        }
        return signals;
    }

    public ISignal getSignal(IEngineEvent e_, Class<?> type_) {
        Controller c = getController(e_);
        return c.getSignal(type_);
    }

    public List<Order> getOrders() {
        List<Order> orders = new ArrayList<>();
        for (Controller c : controllers) {
            orders.addAll(c.getOrders());
        }
        return orders;
    }

    public Order getOrder(String orderId_) {
        Order o;
        for (Controller c : controllers) {
            o = c.getOrder(orderId_);
            if (o != null)
                return o;
        }
        return null;
    }

    public LocalTime getTimeNow() {
        LocalTime t = LocalTime.MIN;
        for (Controller c : controllers) {
            if (c.getTimeNow().toLocalTime().isAfter(t)) {
                t = c.getTimeNow().toLocalTime();
            }
        }
        return t;
    }

    public List<OrderBook> getOrderBooks() {
        List<OrderBook> obs = new ArrayList<>();
        for (Controller c : controllers) {
            obs.addAll(c.getOrderBooks());
        }
        return obs;
    }

    public Set<Security> getSecurities() {
        Set<Security> s = new HashSet<>();
        for (Controller c : controllers) {
            s.addAll(c.getSecurities());
        }
        return s;
    }

    public void closeOrder(Security s_) {
        ObjectWrapperEvent<Security> e = ObjectWrapperEvent.of(s_);
        Controller c = getController(e);
        c.closeOrder(s_);
    }

    public OrderBook getOrderBook(Security sec_) {
        OrderBook ob;
        for (Controller c : controllers) {
            ob = c.getOrderBook(sec_);
            if (ob != null)
                return ob;
        }
        return null;
    }

    public boolean getStopFlag() {
        return stopFlag.get();
    }

    public void setStopFlag(boolean flag_) {
        stopFlag.set(flag_);
    }
}
