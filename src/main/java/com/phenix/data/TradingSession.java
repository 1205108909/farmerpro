package com.phenix.data;

import com.google.common.collect.ImmutableList;
import com.phenix.util.Constants;
import com.phenix.util.FormattedTable;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.time.LocalDateTime;
import java.util.*;

public class TradingSession implements Comparable<TradingSession> {
	@Getter
	private String sessionGroup;
	@Getter
	private SessionType sessionType;
	@Getter
	private String date;
	@Getter
	private LocalDateTime startTime;
	@Getter
	private LocalDateTime endTime;
	@Getter
	private ImmutableList<Operation> operations;
	@Getter
	private AuctionType auctionType;
	@Getter
	private boolean isTradable;

	// All means
	public boolean isTradable2(AuctionType type_) {
		return isTradable && auctionType == type_;
	}

	public boolean isAuction() {
		return isAMAuction() || isPMAuction() || isClsAuction();
	}

	public boolean isAMAuction() {
		return auctionType == AuctionType.AMAuction;
	}

	public boolean isPMAuction() {
		return auctionType == AuctionType.PMAuction;
	}

	public boolean isClsAuction() {
		return auctionType == AuctionType.CloseAuction;
	}

	public boolean isLunchBreak() {
		return sessionType == SessionType.LunchBreak;
	}

	public boolean isPreMarketOpen() {
		return sessionType == SessionType.PreMarketOpen;
	}

	public boolean isAMContinuous() {
		return sessionType == SessionType.AMContinuous;
	}

	public boolean isPMContinuous() {
		return sessionType == SessionType.PMContinuous;
	}

	public boolean isMarketClose() {
		return sessionType == SessionType.MarketClose;
	}

	public boolean isAuctionMatch(AuctionType type_) {
		return (!isAuction()) || type_ == AuctionType.All || (isAuction() && auctionType == type_);
	}

	public static ImmutableList<Operation> EmptyOperation = null;
	static {
		EmptyOperation = ImmutableList.of(Operation.NONE);
	}

	public TradingSession(String date_, String sessionGroup_, SessionType sessionType_, LocalDateTime startTime_, LocalDateTime endTime_, List<Operation> supportedOperions_, AuctionType auctionType_,
			boolean isTradable_) {
		date = date_;
		sessionGroup = sessionGroup_;
		sessionType = sessionType_;
		startTime = startTime_;
		endTime = endTime_;
		operations = ImmutableList.copyOf(supportedOperions_);
		auctionType = auctionType_;
		isTradable = isTradable_;
	}

	public TradingSession(String date_, String sessionGroup_, SessionType sessionType_, LocalDateTime startTime_, LocalDateTime endTime_, String supportedOperions_, AuctionType auctionType_, boolean isTradable_) {
		date = date_;
		sessionGroup = sessionGroup_;
		sessionType = sessionType_;
		startTime = startTime_;
		endTime = endTime_;

		String[] opstr = supportedOperions_.split(",");
		List<Operation> ops = new ArrayList<Operation>();
		Operation op;
		for (int i = 0; i < opstr.length; i++) {
			op = Enum.valueOf(Operation.class, opstr[i].toUpperCase());
			ops.add(op);
		}

		operations = ImmutableList.copyOf(ops);
		auctionType = auctionType_;
		isTradable = isTradable_;
	}

	@Override
	public boolean equals(Object obj_) {
		if (obj_ == null)
			return false;
		if (obj_ == this)
			return true;
		if (obj_.getClass() != getClass())
			return false;
		TradingSession rhs = (TradingSession) obj_;
		return new EqualsBuilder().append(sessionGroup, rhs.sessionGroup).append(sessionType, rhs.sessionType).append(date, rhs.date).append(startTime, rhs.startTime).append(endTime, rhs.endTime)
				.append(operations, rhs.operations).append(auctionType, rhs.auctionType).append(isTradable, rhs.isTradable).isEquals();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(sessionGroup).append(sessionType.toString()).append(date).toHashCode();
	}

	@Override
	public int compareTo(TradingSession other_) {
		if (!sessionGroup.equals(other_.sessionGroup)) {
			return sessionGroup.compareTo(other_.sessionGroup);
		}
		if (startTime.isBefore(other_.startTime)) {
			return -1;
		} else if (startTime.isAfter(other_.startTime)) {
			return 1;
		} else {
			return 0;
		}
	}

	/**
	 * key = groupName
	 */
	public static Map<String, SessionGroup> session2SessionGroup(List<TradingSession> sessions_) {
		List<TradingSession> tradingSessions = new ArrayList<TradingSession>(sessions_);
		Collections.sort(tradingSessions);

		Map<String, List<TradingSession>> tmpCache = new HashMap<String, List<TradingSession>>();
		Map<String, SessionGroup> sessionGroups = new HashMap<String, SessionGroup>();

		for (TradingSession ts : tradingSessions) {
			List<TradingSession> tsessions = null;
			if (tmpCache.containsKey(ts.sessionGroup)) {
				tsessions = tmpCache.get(ts.sessionGroup);
			} else {
				tsessions = new ArrayList<TradingSession>();
				tmpCache.put(ts.sessionGroup, tsessions);
			}
			tsessions.add(ts);
		}

		for (String key : tmpCache.keySet()) {
			sessionGroups.put(key, new SessionGroup(key, tmpCache.get(key)));
		}

		return sessionGroups;
	}

	public static String symbol2SessionGroup(String symbol_) {
		return Constants.DEFAULT + "." + StringUtils.substringAfter(symbol_, ".");
	}

	@Override
	public String toString() {
		return FormattedTable.toString(this);
	}



}
