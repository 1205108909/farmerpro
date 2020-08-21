package com.phenix.data;

import com.phenix.message.ExecType;
import com.phenix.message.ExecutionReport;
import com.phenix.message.FixMessageType;
import com.phenix.message.OrderCancelRequest;
import com.phenix.util.Alert;
import mockit.Mock;
import mockit.MockUp;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.UUID;

import static com.phenix.util.Util.DELTA;
import static org.junit.Assert.assertEquals;

public class OrderTest {
	private Order order;
	private Security sec = Security.of("600000.sh", SecurityType.STOCK);
	private double price = 10.73;
	private double qty = 10000.0;
	private LocalDateTime dt = LocalDateTime.of(LocalDate.of(2018, 11, 13), LocalTime.of(10, 0, 0));
	private final String orderId = "abc___DDD___111";

	@BeforeClass
	public static void setUpClass() {
		new MockUp<Order>() {
			@Mock
			public void publishOrderInfo() {
			}
		};

		new MockUp<Alert>() {
			@Mock
			public void fireAlert(Alert.Severity severity_, String key_, String msg_) {
			}
		};
	}

	@Before
	public void setUp() {
		order = Order.of(orderId, sec, price, qty, dt);
		order.setOrderSide(OrderSide.BUY);
	}

	@Test
	public void testGenerateCancelOrderMsg() {
		OrderCancelRequest r = order.generateCancelOrderMsg();
		OrderCancelRequest expected = new OrderCancelRequest(order.getClOrdId(), r.getClClOrdID());
		assertEquals(expected, r);
	}

	@Test
	public void testEnterExitPendingCancel() {
		order.setOrderStatus(OrderStatus.NEW);
		order.enterPendingCancel();
		assertEquals(OrderStatus.NEW, order.getPrevOrderStatus());
		assertEquals(OrderStatus.PENDING_CANCEL, order.getOrderStatus());

		order.exitPendingCancel();
		assertEquals(OrderStatus.NEW, order.getPrevOrderStatus());
		assertEquals(OrderStatus.NEW, order.getOrderStatus());
	}

	@Test
	public void testHandleCanceled() {
		//pending cancel msg
		ExecutionReport r = new ExecutionReport();
		r.setClOrdId(UUID.randomUUID().toString());
		r.setExecType(ExecType.PENDING_CANCEL);
		r.setOrderStatus(OrderStatus.PENDING_CANCEL);
		r.setSecurity(order.getSec());
		r.setCumQty(0);
		r.setAvgPrice(0);
		r.setLeavesQty(order.getLeavesQty());
		r.setOrigClOrdId(order.getClOrdId());
		r.setTransactionTime(dt.plusHours(1));
		order.handlePendingCancel(r);
		assertEquals(OrderStatus.PENDING_CANCEL, order.getOrderStatus());

		//cancelled
		r = new ExecutionReport();
		r.setClOrdId(UUID.randomUUID().toString());
		r.setExecType(ExecType.CANCELLED);
		r.setOrderStatus(OrderStatus.CANCELED);
		r.setSecurity(order.getSec());
		r.setTransactionTime(dt.plusHours(1));
		order.handleCanceled(r);
		assertEquals(OrderStatus.CANCELED, order.getOrderStatus());
	}

	@Test
	public void testHandleUnsolicitedCancel() {
		ExecutionReport r = new ExecutionReport();
		r.setClOrdId(order.getClOrdId());
		r.setExecType(ExecType.CANCELLED);
		r.setOrderStatus(OrderStatus.CANCELED);
		r.setSecurity(order.getSec());
		order.handleCanceled(r);

		assertEquals(OrderStatus.CANCELED, order.getOrderStatus());
	}

	@Test
	public void testHandleExecution() {
		//1.0 partial filled
		ExecutionReport r = new ExecutionReport();
		r.setClOrdId(order.getClOrdId());
		r.setExecId(UUID.randomUUID().toString());
		r.setExecType(ExecType.PARTIAL_FILL);
		r.setOrderStatus(OrderStatus.PARTIAL_FILLED);
		r.setSecurity(order.getSec());
		r.setLeavesQty(order.getLeavesQty());
		r.setCumQty(4000);
		r.setAvgPrice(10.72);
		r.setLastPx(10.72);
		r.setLastShares(4000);
		order.handleExecution(r);

		assertEquals(OrderStatus.PARTIAL_FILLED, order.getOrderStatus());
		assertEquals(4000, order.getCumQty(), DELTA);
		assertEquals(6000, order.getLeavesQty(), DELTA);
		assertEquals(10.72, order.getAvgPrice(), DELTA);

		//2.0 Fully Filled
		r = new ExecutionReport();
		r.setClOrdId(order.getClOrdId());
		r.setExecId(UUID.randomUUID().toString());
		r.setExecType(ExecType.FILL);
		r.setOrderStatus(OrderStatus.FILLED);
		r.setSecurity(order.getSec());
		r.setLeavesQty(0);
		r.setCumQty(6000);
		r.setAvgPrice(10.71);
		r.setLastPx(10.71);
		r.setLastShares(6000);
		r.setTransactionTime(dt.plusHours(1));
		order.handleExecution(r);
		assertEquals(OrderStatus.FILLED, order.getOrderStatus());
		assertEquals(10000, order.getCumQty(), DELTA);
		assertEquals(0, order.getLeavesQty(), DELTA);
		assertEquals(10.714, order.getAvgPrice(), DELTA);

		//3.0 New -> Pending CANCEL -> Full Filled -> CANCEL Rejected
		order = Order.of(orderId, sec, price, qty, dt);
		order.setOrderSide(OrderSide.BUY);
		order.cancel();
		assertEquals(OrderStatus.PENDING_CANCEL, order.getOrderStatus());

		//fully filled
		r = new ExecutionReport();
		r.setClOrdId(order.getClOrdId());
		r.setExecId(UUID.randomUUID().toString());
		r.setExecType(ExecType.FILL);
		r.setOrderStatus(OrderStatus.FILLED);
		r.setSecurity(order.getSec());
		r.setLeavesQty(0);
		r.setCumQty(10000);
		r.setAvgPrice(10.71);
		r.setLastPx(10.71);
		r.setLastShares(10000);
		r.setTransactionTime(dt.plusHours(1));
		order.handleExecution(r);
		assertEquals(10000, order.getCumQty(), DELTA);
		assertEquals(OrderStatus.PENDING_CANCEL, order.getOrderStatus());

		//cancel rejected
		ExecutionReport reject = new ExecutionReport();
		reject.setMsgType(FixMessageType.ORDER_CANCEL_REJECT);
		reject.setClOrdId(order.getClOrdId());
		order.handleCancelRejected(reject);
		assertEquals(OrderStatus.FILLED, order.getOrderStatus());

		//4.0 Pending New -> Pending CANCEL -> Fully Filled -> CANCEL Rejected
		order = Order.of(orderId, sec, price, qty, dt);
		order.setOrderSide(OrderSide.BUY);
		order.cancel();
		assertEquals(OrderStatus.PENDING_CANCEL, order.getOrderStatus());

		//fully filled
		r = new ExecutionReport();
		r.setClOrdId(order.getClOrdId());
		r.setExecId(UUID.randomUUID().toString());
		r.setExecType(ExecType.FILL);
		r.setOrderStatus(OrderStatus.FILLED);
		r.setCumQty(10000);
		r.setAvgPrice(10.73);
		r.setLastPx(10.73);
		r.setLastShares(10000);
		r.setTransactionTime(dt.plusHours(1));
		order.handleExecution(r);
		assertEquals(OrderStatus.PENDING_CANCEL, order.getOrderStatus());
		assertEquals(10000, order.getCumQty(), DELTA);

		//cancel rejected
		reject = new ExecutionReport();
		reject.setMsgType(FixMessageType.ORDER_CANCEL_REJECT);
		reject.setClOrdId(order.getClOrdId());
		order.handleCancelRejected(reject);
		assertEquals(OrderStatus.FILLED, order.getOrderStatus());

		//5.0 Pending New -> Pending CANCEL -> Fully Filled (with order status = filled) -> CANCEL Rejected
		order = Order.of(orderId, sec, price, qty, dt);
		order.setOrderSide(OrderSide.BUY);
		order.cancel();
		assertEquals(OrderStatus.PENDING_CANCEL, order.getOrderStatus());

		//fully filled
		r = new ExecutionReport();
		r.setClOrdId(order.getClOrdId());
		r.setExecId(UUID.randomUUID().toString());
		r.setExecType(ExecType.FILL);
		r.setOrderStatus(OrderStatus.FILLED);
		r.setCumQty(10000);
		r.setAvgPrice(10.73);
		r.setLastPx(10.73);
		r.setLastShares(10000);
		r.setTransactionTime(dt.plusHours(1));
		order.handleExecution(r);
		assertEquals(OrderStatus.PENDING_CANCEL, order.getOrderStatus());
		assertEquals(10000, order.getCumQty(), DELTA);

		//cancel reject
		reject = new ExecutionReport();
		reject.setMsgType(FixMessageType.ORDER_CANCEL_REJECT);
		reject.setClOrdId(order.getClOrdId());
		order.handleCancelRejected(reject);
		assertEquals(OrderStatus.FILLED, order.getOrderStatus());

	    //6.0 Over Execution
		order = Order.of(orderId, sec, price, qty, dt);
		order.setOrderSide(OrderSide.BUY);

		//partial fill
		r = new ExecutionReport();
		r.setClOrdId(order.getClOrdId());
		r.setExecId(UUID.randomUUID().toString());
		r.setExecType(ExecType.PARTIAL_FILL);
		r.setOrderStatus(OrderStatus.PARTIAL_FILLED);
		r.setCumQty(9000);
		r.setAvgPrice(10.73);
		r.setLastPx(10.73);
		r.setLastShares(9000);
		order.handleExecution(r);

		//over fill
		r = new ExecutionReport();
		r.setClOrdId(order.getClOrdId());
		r.setExecId(UUID.randomUUID().toString());
		r.setExecType(ExecType.FILL);
		r.setOrderStatus(OrderStatus.FILLED);
		r.setCumQty(2000);
		r.setAvgPrice(10.71);
		r.setLastPx(10.71);
		r.setLastShares(2000);
		order.handleExecution(r);
		assertEquals(OrderStatus.FILLED, order.getOrderStatus());
		assertEquals(11000, order.getCumQty(), DELTA);
		assertEquals(-1000, order.getLeavesQty(), DELTA);

		//7.0 Check Avg Price
		order = Order.of(orderId, sec, price, qty, dt);
		order.setOrderSide(OrderSide.BUY);
		r = new ExecutionReport();
		r.setClOrdId(order.getClOrdId());
		r.setExecId(UUID.randomUUID().toString());
		r.setExecType(ExecType.PARTIAL_FILL);
		r.setOrderStatus(OrderStatus.PARTIAL_FILLED);
		r.setCumQty(9000);
		r.setAvgPrice(10.73);
		r.setLastPx(10.73);
		r.setLastShares(9000);
		order.handleExecution(r);
		assertEquals(10.73, order.getAvgPrice(), DELTA);

		//fully fill
		r = new ExecutionReport();
		r.setClOrdId(order.getClOrdId());
		r.setExecId(UUID.randomUUID().toString());
		r.setExecType(ExecType.FILL);
		r.setOrderStatus(OrderStatus.FILLED);
		r.setCumQty(1000);
		r.setAvgPrice(10.71);
		r.setLastPx(10.71);
		r.setLastShares(1000);
		order.handleExecution(r);
		assertEquals(OrderStatus.FILLED, order.getOrderStatus());
		assertEquals(10000, order.getCumQty(), DELTA);
		assertEquals(0, order.getLeavesQty(), DELTA);
		assertEquals(10.728, order.getAvgPrice(), DELTA);
	}

	@Test
	public void testHandleNewRejected() {
		ExecutionReport r = new ExecutionReport();
		r.setClOrdId(order.getClOrdId());
		r.setExecId(UUID.randomUUID().toString());
		r.setExecType(ExecType.REJECTED);
		r.setOrderStatus(OrderStatus.REJECTED);
		r.setSecurity(order.getSec());
		r.setLeavesQty(order.getLeavesQty());
		r.setCumQty(0);
		r.setAvgPrice(0);

		order.handleNewRejected(r);
		assertEquals(OrderStatus.REJECTED, order.getOrderStatus());

		//pending cancel when rejected
		order.setOrderStatus(OrderStatus.PENDING_NEW);
		order.cancel();
		order.handleNewRejected(r);
		assertEquals(OrderStatus.PENDING_CANCEL, order.getOrderStatus());

		//cancel reject
		ExecutionReport reject = new ExecutionReport();
		reject.setOrigClOrdId(order.getClOrdId());
		reject.setClOrdId(UUID.randomUUID().toString());
		reject.setOrderStatus(OrderStatus.REJECTED);
		reject.setMsgType(FixMessageType.ORDER_CANCEL_REJECT);
		order.handleCancelRejected(reject);
		assertEquals(OrderStatus.REJECTED, order.getOrderStatus());
	}

	@Test
	public void testHandleNewAck() {
		ExecutionReport r = new ExecutionReport();
		r.setClOrdId(order.getClOrdId());
		r.setExecId(UUID.randomUUID().toString());
		r.setExecType(ExecType.NEW);
		r.setOrderStatus(OrderStatus.NEW);
		r.setSecurity(order.getSec());
		r.setLeavesQty(order.getLeavesQty());
		r.setCumQty(0);
		r.setAvgPrice(0);

		//nomal new ack
		order.handleNewAck(r);
		assertEquals(OrderStatus.NEW, order.getOrderStatus());
		assertEquals(10000, order.getOrderQty(), DELTA);
		assertEquals(10000, order.getLeavesQty(), DELTA);
		assertEquals(0, order.getCumQty(), DELTA);
		assertEquals(0, order.getAvgPrice(), DELTA);

		//order in pending cancel when recived pending new
		order.setOrderStatus(OrderStatus.PENDING_NEW);
		order.enterPendingCancel();
		order.handlePendingNew(r);
		assertEquals(OrderStatus.PENDING_CANCEL, order.getOrderStatus());
		order.handleNewAck(r);
		assertEquals(OrderStatus.PENDING_CANCEL, order.getOrderStatus());

		// when received a cancel reject, the status should become New
		// cancel rejected
		ExecutionReport reject = new ExecutionReport();
		reject.setOrigClOrdId(order.getClOrdId());
		reject.setClOrdId(UUID.randomUUID().toString());
		reject.setOrderStatus(OrderStatus.REJECTED);
		reject.setMsgType(FixMessageType.ORDER_CANCEL_REJECT);
		order.handleCancelRejected(reject);
		assertEquals(OrderStatus.NEW, order.getOrderStatus());
	}

	@Test
	public void testHandlePendingNew() {
		//by default pending_new
		assertEquals(OrderStatus.PENDING_NEW, order.getOrderStatus());

		//handle 1st pendingNew
		ExecutionReport e = new ExecutionReport();
		e.setClOrdId(order.getClOrdId());
		e.setExecId(UUID.randomUUID().toString());
		e.setExecType(ExecType.PENDING_NEW);
		e.setOrderStatus(OrderStatus.PENDING_NEW);
		order.handlePendingNew(e);
		assertEquals(OrderStatus.PENDING_NEW, order.getOrderStatus());

		//order in pending cancel when recived pending new
		order.enterPendingCancel();
		order.handlePendingNew(e);
		assertEquals(OrderStatus.PENDING_CANCEL, order.getOrderStatus());

		// when received a cancel reject, the status should become New
		// cancel rejected
		e = new ExecutionReport();
		e.setMsgType(FixMessageType.ORDER_CANCEL_REJECT);
		e.setClOrdId(order.getClOrdId());
		e.setOrderStatus(OrderStatus.REJECTED);
		order.handleCancelRejected(e);
		assertEquals(OrderStatus.PENDING_NEW, order.getOrderStatus());
		assertEquals(1, order.getNumCancelReject());
	}

	@Test
	public void testHandlePendingCancel() {
		ExecutionReport r = new ExecutionReport();
		r.setClOrdId(order.getClOrdId());
		r.setTransactionTime(LocalDateTime.now());
		r.setOrderStatus(OrderStatus.PENDING_CANCEL);
		order.handlePendingCancel(r);

		assertEquals(OrderStatus.PENDING_CANCEL, order.getOrderStatus());
	}

	@Test
	public void testHandlePendingReplace() {
	}

	@Test
	public void testHandleExpired() {
		ExecutionReport r = new ExecutionReport();
		r.setClOrdId(order.getClOrdId());
		r.setMsgType(FixMessageType.ORDER_EXPIRED);
		r.setTransactionTime(LocalDateTime.now());
		r.setOrderStatus(OrderStatus.EXPIRED);
		order.handleExpired(r);

		assertEquals(0, order.getLeavesQty(), DELTA);
	}

	@Test
	public void testHandleReplaced() {
		order.handleReplaced(new ExecutionReport());
	}

	@Test
	public void testHandleReplaceRejected() {
		order.handleReplaceRejected(new ExecutionReport());
	}

	@Test
	public void testHandleRestated() {
		ExecutionReport r = new ExecutionReport();
		r.setClOrdId(order.getClOrdId());
		r.setTransactionTime(LocalDateTime.now());
		r.setOrderStatus(OrderStatus.CANCELED);
		r.setExecType(ExecType.RESTATED);
		r.setCumQty(500);
		r.setLeavesQty(9500);
		r.setAvgPrice(10.73);
		order.handleRestated(r);

		assertEquals(OrderStatus.CANCELED, order.getOrderStatus());
		assertEquals(500, order.getCumQty(), DELTA);
		assertEquals(9500, order.getLeavesQty(), DELTA);
		assertEquals(10.73, order.getAvgPrice(), DELTA);
	}

	@Test
	public void testApplyCancel() {
		order.setOrderStatus(OrderStatus.PENDING_NEW);
		order.setPrevOrderStatus(OrderStatus.PENDING_NEW);
		order.setOrderQty(1000);
		order.setLeavesQty(500);
		order.setCumQty(500);

		//cancel suceed
		order.applyCancel();
		assertEquals(0, order.getLeavesQty(), DELTA);
		assertEquals(OrderStatus.CANCELED, order.getOrderStatus());
		assertEquals(OrderStatus.CANCELED, order.getPrevOrderStatus());

		//fill so cancel fail
		order.setCumQty(0);
		order.setLeavesQty(1000);
		order.setOrderQty(1000);
		order.setOrderStatus(OrderStatus.FILLED);
		order.setPrevOrderStatus(OrderStatus.FILLED);
		order.applyCancel();
		assertEquals(1000, order.getLeavesQty(), DELTA);
		assertEquals(OrderStatus.FILLED, order.getOrderStatus());
		assertEquals(OrderStatus.FILLED, order.getPrevOrderStatus());

		//order canceled, then apply cancel again
		order.setOrderStatus(OrderStatus.CANCELED);
		order.setPrevOrderStatus(OrderStatus.PENDING_NEW);
		order.applyCancel();
		assertEquals(1000, order.getLeavesQty(), DELTA);
		assertEquals(OrderStatus.CANCELED, order.getOrderStatus());

		//order pendingCancel
		order.setOrderStatus(OrderStatus.PENDING_CANCEL);
		order.setPrevOrderStatus(OrderStatus.PENDING_NEW);
		order.applyCancel();
		assertEquals(0, order.getLeavesQty(), DELTA);
		assertEquals(OrderStatus.CANCELED, order.getOrderStatus());
	}

	@Test
	public void testCancelWithREstate() {
		//cancel while restated with 39 = 4
		ExecutionReport r = new ExecutionReport();
		r.setClOrdId(order.getClOrdId());
		r.setExecType(ExecType.RESTATED);
		r.setSecurity(order.getSec());
		r.setCumQty(0);
		r.setAvgPrice(0);
		r.setOrderStatus(OrderStatus.CANCELED);
		order.handleRestated(r);
		assertEquals(OrderStatus.CANCELED, order.getOrderStatus());

		ExecutionReport reject = new ExecutionReport();
		reject.setMsgType(FixMessageType.ORDER_CANCEL_REJECT);
		reject.setOrigClOrdId(order.getClOrdId());
		reject.setClOrdId(UUID.randomUUID().toString());
		order.handleCancelRejected(reject);
		assertEquals(OrderStatus.CANCELED, order.getOrderStatus());
	}
}
