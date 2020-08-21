package com.phenix.data;

import com.phenix.exception.NotSupportedException;
import com.phenix.farmer.signal.SignalType;
import com.phenix.util.Alert;
import com.phenix.util.Util;
import com.phenix.message.ExecutionReport;
import com.phenix.message.OrderCancelRequest;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Comparator;
import java.util.Objects;
import java.util.UUID;

public class Order {
	private final static transient Logger Logger = LoggerFactory.getLogger(Order.class);

	@Getter
	private String clOrdId;
	@Getter
	@Setter
	private OrderSide orderSide;
	@Getter
	@Setter
	private OrderType orderType;
	@Getter
	@Setter
	private Security sec;
	@Getter
	@Setter
	private double orderQty;
	@Getter
	@Setter
	private double cumQty;
	@Getter
	@Setter
	private double leavesQty;
	@Getter
	@Setter
	private double avgPrice;
	@Getter
	@Setter
	private double price;
	@Getter
	@Setter
	private double spread;
	@Getter
	@Setter
	private String exDestination;
	@Getter
	@Setter
	private String account;
	@Getter
	@Setter
	private LocalDateTime inPendingTime;
	@Getter
	@Setter
	private LocalDateTime closedTime;
	@Getter
	@Setter
	private OrderStatus orderStatus;
	@Getter
	@Setter
	private OrderStatus prevOrderStatus;	
	@Getter
	@Setter
	private String placementReason;
	@Getter
	@Setter
	private SignalType signalType;
	@Getter
	@Setter
	private int cancelPeriodInSecs;
	@Getter
	@Setter
	private Direction direction;
	@Getter
	@Setter
	private int numCancelReject;
	@Getter
	@Setter
	private double highPx;
	@Getter
	@Setter
	private double lowPx;
	@Getter
	@Setter
	private double refPx;
	@Getter
	@Setter
	private double maxRevert;
	@Getter
	@Setter
	private double maxRevertRenewable;
	@Getter
	@Setter
	private LocalTime highTime;
	@Setter
	@Getter
	private LocalTime lowTime;
	@Getter
	@Setter
	private int counter;

	
	protected Order(String clOrdId_, Security sec_, double px_, double qty_, LocalDateTime pendingTime_) {
		clOrdId = clOrdId_;
		sec = sec_;
		price = px_;
		orderQty = qty_;
		leavesQty = qty_;
		inPendingTime = pendingTime_;
		cancelPeriodInSecs = 5;
		orderStatus = OrderStatus.PENDING_NEW;
		prevOrderStatus = OrderStatus.PENDING_NEW;
		lowPx = Double.MAX_VALUE;
		highPx = Double.MIN_VALUE;
		maxRevert = 0;
		spread = Double.NaN;
		highTime = pendingTime_.toLocalTime();
		lowTime = pendingTime_.toLocalTime();
	}

	public static Order of(Security sec_, double px_, double qty_, LocalDateTime pendingTime_) {
		return of(UUID.randomUUID().toString(), sec_, px_, qty_, pendingTime_);
	}

	public static Order of(String clOrdId_, Security sec_, double px_, double qty_, LocalDateTime pendingTime_) {
		return new Order(clOrdId_, sec_, px_, qty_, pendingTime_);
	}

	public static Order of() {
		return of(UUID.randomUUID().toString(),null, -1, -1, LocalDateTime.MAX);
	}

	public LocalDate getDate() {
		return inPendingTime.toLocalDate();
	}

	public LocalTime getTime() {
		return inPendingTime.toLocalTime();
	}

	public int getTimeInSecs() {
		return inPendingTime.toLocalTime().toSecondOfDay();
	}

	public SecurityType getSecType() {
		return sec.getType();
	}

	public boolean isFilled() {
		return orderStatus == OrderStatus.FILLED;
	}
	
	public boolean isInPendingCancel() {
		return orderStatus == OrderStatus.PENDING_CANCEL;
	}
	
	public boolean isInPendingNew() {
		return orderStatus == OrderStatus.PENDING_NEW;
	}

	public String getSymbol() {
		return sec.getSymbol();
	}

	public void applyCancel() {
		if(Util.isOpenStatus(orderStatus)) {
			exitPendingCancel();
			updateOrderStatus(OrderStatus.CANCELED);
			leavesQty = 0;
			closedTime = LocalDateTime.now();
			publishOrderInfo();
		} else {
			if(Logger.isDebugEnabled()) {
				Logger.debug(String.format("exchange slice [%s] is in [%s], can't be cancelled", clOrdId, orderStatus));
			}
		}
	}
	
	public void place() {
		//CTPOrderRouterManager.getInstance().placeOrder(this);
	}
	
	public void cancel() {
		cancel(false);
	}
	
	public void cancel(boolean force_) {	
		if(isInPendingCancel() && !force_) {
			if(Logger.isDebugEnabled()) {
				Logger.debug(String.format("[%s] is already in pending cancel state and not forced to cancel, skip the cancel request", clOrdId));
			}
			return;
		}
		
		if(isInPendingNew() && !force_ && numCancelReject >= 20) {
			if(Logger.isDebugEnabled()) {
				Logger.debug(String.format("[%s] is already in pending new state and not forced to cancel, skip the cancel request", clOrdId));
			}
			return;
		}			
		
		enterPendingCancel();
		//Order order = generateCancelOrderMsg();
		//CTPOrderRouterManager.getInstance().placeOrder(order);
	}
	
	public OrderCancelRequest generateCancelOrderMsg() {
		OrderCancelRequest r = new OrderCancelRequest(clOrdId);
		return r;
	}
		
	private void updateOrderStatus(OrderStatus status_) {
		if(status_ != OrderStatus.PARTIAL_FILLED && orderStatus == status_) {//CTP may return the same status
			return;
		}
		
        if (Util.getOrderStatusPrecedence(status_) >= Util.getOrderStatusPrecedence(orderStatus)) {
            orderStatus = status_;
        }

        if (status_ != OrderStatus.PENDING_CANCEL && Util.getOrderStatusPrecedence(status_) >= Util.getOrderStatusPrecedence(prevOrderStatus)) {
            prevOrderStatus = status_;
        }
    }
	
	public void exitPendingCancel() {
		if(orderStatus == OrderStatus.PENDING_CANCEL) {
			orderStatus = prevOrderStatus;
		}
		
		publishOrderInfo();
	}
	
	public void enterPendingCancel() {
		if(orderStatus != OrderStatus.PENDING_CANCEL) {
			prevOrderStatus = orderStatus;
			orderStatus = OrderStatus.PENDING_CANCEL;
			publishOrderInfo();
		}
	}
	
	public void handleCanceled(ExecutionReport rpt_) {
		exitPendingCancel();
		updateOrderStatus(rpt_.getOrderStatus());
		leavesQty = 0;
		cumQty = Math.round(cumQty);
		closedTime = rpt_.getTransactionTime();
		
		publishOrderInfo();
	}
	
	public void handleExecution(ExecutionReport rpt_) {
		if(rpt_.getLastShares() > 0) {
			avgPrice = (avgPrice * cumQty + rpt_.getLastPx() * rpt_.getLastShares()) / (cumQty + rpt_.getLastShares());
			leavesQty -= rpt_.getLastShares();
			cumQty += rpt_.getLastShares();
			leavesQty = Math.round(leavesQty);
			cumQty = Math.round(cumQty);
		}
		
		OrderStatus status = leavesQty <= 0 ? OrderStatus.FILLED : OrderStatus.PARTIAL_FILLED;
		updateOrderStatus(status);
		
		if(leavesQty < 0) {
			Alert.fireAlert(Alert.Severity.Major, "OverExecution." + clOrdId, "cumQty[" + cumQty + "] ordQty[" + orderQty + "] leavesQty[" + leavesQty + "]");
		}
		
		if(status == OrderStatus.FILLED) {
			closedTime = rpt_.getTransactionTime();
		}
		
		publishOrderInfo();
	}
	
	public void handleNewRejected(ExecutionReport rpt_) {
		updateOrderStatus(rpt_.getOrderStatus());
		publishOrderInfo();
	}
	
	public void handleNewAck(ExecutionReport rpt_) {
		updateOrderStatus(rpt_.getOrderStatus());
		publishOrderInfo();
	}	

	public void handlePendingNew(ExecutionReport rpt_) {
		updateOrderStatus(rpt_.getOrderStatus());
		publishOrderInfo();
	}
	
	public void handlePendingCancel(ExecutionReport rpt_) {
		orderStatus = rpt_.getOrderStatus();
	}	
	
	public void handlePendingReplace(ExecutionReport rpt_) {
		String msg = String.format("Unexpected pending replace received: [%s]", rpt_.toString());
		Logger.error(msg);
		Alert.fireAlert(Alert.Severity.Major, "UnexpectedPendingReplace_" + clOrdId, msg);
	}
	
	public void handleCancelRejected(ExecutionReport rpt_) {
		exitPendingCancel();		
		publishOrderInfo();		
		numCancelReject++;
	}
	
	public void handleExpired(ExecutionReport rpt_) {
		updateOrderStatus(rpt_.getOrderStatus());
		leavesQty = 0;
		cumQty = Math.round(cumQty);
		closedTime = rpt_.getTransactionTime();
		publishOrderInfo();
	}
	
	public void handleReplaced(ExecutionReport rpt_) {
		String msg = String.format("Unexpected replaced received: [%s]", rpt_.toString());
		Logger.error(msg);
		Alert.fireAlert(Alert.Severity.Major, "UnexpectedReplaced_" + clOrdId, msg);
	}
	
	public void handleReplaceRejected(ExecutionReport rpt_) {
		String msg = String.format("Unexpected replaced rejected: [%s]", rpt_.toString());
		Logger.error(msg);
		Alert.fireAlert(Alert.Severity.Major, "UnexpectedReplaceRejected_" + clOrdId, msg);
	}
	
	public void handleRestated(ExecutionReport rpt_) {
		Logger.info(String.format("Exchange Slice [{0}] received restate message", clOrdId));
		if (rpt_.getOrderStatus() != null) {
			orderStatus = rpt_.getOrderStatus();
		}
		if (Util.isValidQty(rpt_.getCumQty())) {
			cumQty = rpt_.getCumQty();
		}
		if (Util.isValidQty(rpt_.getLeavesQty())) {
			leavesQty = rpt_.getLeavesQty();
		}
		if (Util.isValidLimitPrice(rpt_.getAvgPrice())) {
			avgPrice = rpt_.getAvgPrice();
		}
	}

	public void handleExecutionReport(ExecutionReport rpt_) {
		switch (rpt_.getExecType()) {
			case CANCELLED:
				handleCanceled(rpt_);
				break;
			case EXPIRED:
				handleExpired(rpt_);
				break;
			case FILL:
			case PARTIAL_FILL:
				handleExecution(rpt_);
				break;
			case NEW:
				handleExecution(rpt_);
				break;
			case PENDING_CANCEL:
				handlePendingCancel(rpt_);
				break;
			case PENDING_NEW:
				handlePendingNew(rpt_);
				break;
			case PENDING_REPLACE:
				handlePendingReplace(rpt_);
				break;
			case REJECTED:
				handleNewRejected(rpt_);
				break;
			case REPLACED:
				handleReplaced(rpt_);
				break;
			case RESTATED:
				handleRestated(rpt_);
				break;
			default:
				throw new NotSupportedException("Unsupported execution type " + rpt_.getExecType());

		}
	}

	//TODO
	public void publishOrderInfo() {
		
	}
	
	public ExecutionReport generateNewAckReport() {
		ExecutionReport rpt = new ExecutionReport();
		rpt.setOrderStatus(OrderStatus.NEW);
		rpt.setSecurity(sec);
		rpt.setClOrdId(clOrdId);
		return rpt;
	}
	
	public ExecutionReport generateExecutionReport() {
		return generateExecutionReport(-1, 1);
	}
	
	public ExecutionReport generateExecutionReport(double px_, double fillPercentage) {
		ExecutionReport rpt = new ExecutionReport();
		rpt.setClOrdId(clOrdId);		
		rpt.setLastPx(px_ == -1 ? price : px_);
		rpt.setLastShares(orderQty * fillPercentage);
		rpt.setOrderStatus(fillPercentage == 1 ? OrderStatus.FILLED : OrderStatus.PARTIAL_FILLED);
		rpt.setSecurity(sec);
		return rpt;
	}
	

	public ExecutionReport generateCancelRejectReport() {
		ExecutionReport rpt = new ExecutionReport();
		rpt.setClOrdId(clOrdId);
		rpt.setOrderStatus(OrderStatus.REJECTED);
		return rpt;
	}
	
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

	@Override
	public boolean equals(Object obj_) {
		if (obj_ == null) {
			return false;
		}
		if (obj_ == this) {
			return true;
		}
		if (obj_.getClass() != getClass()) {
			return false;
		}
		Order rhs = (Order)obj_;
		return clOrdId.equals(rhs.clOrdId);
	}

	@Override
	public int hashCode() {
		//throw new NotSupportedException("Order is not allowed to be used as hash key");
		return Objects.hash(clOrdId);
	}

	public boolean isClosePosOrder() {
		return false;
	}

	public boolean isOpenPosOrder() {
		return !isClosePosOrder();
	}

	public final static Comparator<Order> TimeComparator = new Comparator<Order>() {
		public int compare(Order o1, Order o2) {
			if (o1 == o2)
				return 0;
			if (o1 == null)
				return -1;
			if (o2 == null)
				return +1;
			return o1.getInPendingTime().compareTo(o2.getInPendingTime());
		}
	};

	public ClosePosOrder createCloseOrder(LocalDateTime t_, double px_) {
		if(isClosePosOrder()) {
			throw new IllegalArgumentException("CloseOrder[" + clOrdId + "] is not allowed for close order creation");
		}

		ClosePosOrder co = new ClosePosOrder();

		co.setSec(sec);
		co.setRootClOrdId(clOrdId);
		co.setPrice(px_);
		co.setOrderQty(cumQty);
		co.setOrderSide(orderSide == OrderSide.BUY ? OrderSide.SELL : OrderSide.BUY);
		co.setDirection(direction == Direction.LONG ? Direction.SHORT : Direction.LONG);
		co.setInPendingTime(t_);
		co.setAccount(account);
		co.setExDestination(exDestination);
		co.setCloseIntraDay(false);
		co.setOrderType(orderType);
		co.setLeavesQty(cumQty);
		co.setHighTime(t_.toLocalTime());
		co.setLowTime(t_.toLocalTime());
		return co;
	}

	public final static Comparator<Order> ORDER_COMPARATOR_BY_PENDINGTIME = (e1, e2) -> {
		int res = e1.getInPendingTime().compareTo(e2.getInPendingTime());
		if (res != 0) return res;
		if (e1.isClosePosOrder() && !e2.isClosePosOrder())
			return -1;
		if (!e1.isClosePosOrder() && e2.isClosePosOrder())
			return 1;
		return 0;
	};
	public final static Comparator<Order> ORDER_COMPARATOR_BY_SIGNALTYPE =  Comparator.comparing(Order::getSignalType);
	public final static Comparator<Order> ORDER_COMPARATOR_SIGNALTYPE_AND_PENDINGTIME = (o1, o2) -> {
		int res = ORDER_COMPARATOR_BY_SIGNALTYPE.compare(o1, o2);
		return res != 0 ? res : ORDER_COMPARATOR_BY_PENDINGTIME.compare(o1, o2);
	};
}
