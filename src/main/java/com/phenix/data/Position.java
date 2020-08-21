package com.phenix.data;

import com.google.common.collect.Sets;
import com.phenix.farmer.signal.SignalType;
import com.phenix.util.Util;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.DoubleBinaryOperator;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Position {
	@Getter
	private double cash;
	@Getter
	private Account account;
	@Getter
	private List<PositionEntry> positions;
	
	public Position(Account account_, List<PositionEntry> positions_, double cash_) {
		super();
		this.account = account_;
		this.positions = positions_;
		this.cash = cash_;
	}
	public Position(Account account_, List<PositionEntry> positions_) {
		this(account_, positions_, 0);
	}
	
	public Position(Account account_) {
		this(account_, new ArrayList<>());
	}
	
	public void add(PositionEntry entry_) {
		Objects.requireNonNull(entry_);
		positions.add(entry_);		
	}
	
	private Position compute(Position pos_, DoubleBinaryOperator op_) {
		if(!account.equals(pos_.account)) {//do not support cross account operation
			return null;
		}
	
		Map<Security, PositionEntry> left = positions.stream()
                .collect(Collectors.toMap(PositionEntry::getSec, Function.identity()));
		Map<Security, PositionEntry> right = pos_.getPositions().stream()
                .collect(Collectors.toMap(e -> e.getSec(), e -> e));
		List<Security> union = Sets.union(left.keySet(), right.keySet()).stream()
                .collect(Collectors.toList());
		
		List<PositionEntry> e = new ArrayList<>();		
		for (Security sec : union) {
			double l = 0;
			double r = 0;
			Direction d = Direction.UNKNOWN;
			if(left.containsKey(sec)) {
				l = left.get(sec).getQty();
				d = left.get(sec).getDirection();
			}
			if(right.containsKey(sec)) {
				r = right.get(sec).getQty();
				d = right.get(sec).getDirection();
			}
			
			double res = op_.applyAsDouble(l, r);			
			if(res <= 0) {				
				if(l == 0) {//sell all - normalize to be 1
					res = Util.roundQtyNear2Digit(res);
				} else { //other wise , normalize to be 100
					res = Util.roundQtyNear2Digit(res);
				}
			} else {
				res = Util.roundQtyNear2Digit(res);
			}
			
			e.add(new PositionEntry(sec, res, UUID.randomUUID().toString(), d));
		}
		Collections.sort(e);
		
		return new Position(new Account(account.getAccountId()), e, cash - pos_.getCash());
	}
	
	public Position add(Position pos_) {
		return compute(pos_, (l, r) -> l + r); 
	}
	
	public Position minus(Position pos_) {
		return compute(pos_, (l, r) -> l - r); 
	}

	public static class PositionEntry implements Comparable<PositionEntry>, Serializable {

		private static final long serialVersionUID = 1725378414969999694L;
		@Getter
		protected Security sec;
		@Getter
		@Setter
		private double qty;
		@Getter
		@Setter
		private double highestPx;
		@Getter
		@Setter
		private double lowestPx;
		@Getter
		@Setter
		private double openPx;
		@Getter
		@Setter
		private double closePx;
		@Getter
		@Setter
		private double profit;
		@Getter
		@Setter
		private double closedQty;
		@Getter
		@Setter
		private String closeReason;
		@Getter
		@Setter
		private LocalDateTime openTime;
		@Getter
		@Setter
		private LocalDateTime closeTime;
		@Getter
		@Setter
		private SignalType signalType;
		@Getter
		@Setter
		private boolean closed;

		@Getter
		protected String id;
		@Getter
        private Direction direction;

		public PositionEntry(Security sec_, double qty_, String id_, Direction d_) {
			super();
			sec = sec_;
			qty = qty_;
			//closedQty = 0;
			closed = false;
			id = id_;
			direction = d_;
			//open = 0;
			//closePx = 0;
			//profit = 0;
		}

		@Override
		public int compareTo(PositionEntry o) {
			return sec.compareTo(o.getSec());
		}

		public SecurityType getSecType() {
			return sec.getType();
		}

		public String getSymbol() {
			return sec.getSymbol();
		}

		// NOTE:qty_ here must be rounded, otherwise may caused severe precision problem
		public double addAndGet(double qty_) {
			throw new UnsupportedOperationException("Not supported");
		}

		private final static String CASH_ID = "I AM CASH, DON'T TOUCH ME, OTHERWISE FUCK U";
		public final static PositionEntry Cash = new PositionEntryCash(Security.Cash, -1, CASH_ID, Direction.UNKNOWN);
		static {
			Cash.setOpenPx(1);
			Cash.setHighestPx(1);
			Cash.setLowestPx(1);
			Cash.setClosePx(1);
			Cash.setCloseReason("CASH");
			Cash.setOpenTime(LocalDateTime.MIN);
		}
	}

	private final static class PositionEntryCash extends PositionEntry {
		private static final long serialVersionUID = -5446852238113222842L;
		private static final long MULTIPLIER = 10000L;

		public PositionEntryCash(Security sec_, double qty_, String id_, Direction d_) {
			super(sec_, qty_, id_, d_);
			qty = new AtomicLong(Math.round(qty_ * MULTIPLIER));
		}

		private transient AtomicLong qty;

		@Override
		public double addAndGet(double qty_) {
			long lQty = qty.addAndGet(Math.round(qty_ * MULTIPLIER));
			return ((double)lQty) / MULTIPLIER;
		}

		@Override
		public double getQty() {
			long lQty = qty.get();
			return ((double)lQty) / MULTIPLIER;
		}

		@Override
		public void setQty(double qty_) {
			qty.set(Math.round(qty_ * MULTIPLIER));
		}

		private void readObject(java.io.ObjectInputStream s_) throws java.io.IOException, ClassNotFoundException {
			s_.defaultReadObject();
			qty = new AtomicLong(s_.readLong());
		}

		private void writeObject(java.io.ObjectOutputStream s_) throws java.io.IOException {
			s_.defaultWriteObject();
			s_.writeLong(qty.get());
		}
	}
}
