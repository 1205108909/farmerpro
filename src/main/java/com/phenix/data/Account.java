package com.phenix.data;

import java.util.Objects;

import org.apache.commons.lang3.builder.EqualsBuilder;

import lombok.Getter;

public class Account implements Cloneable {
	@Getter
	private String accountId;
	@Getter
	private double positionPercentage;

	public Account(String accountId_) {
		this(accountId_, 1);
	}
	
	public Account(String accountId_, double positionPercentage_) {
		super();
		this.accountId = accountId_;
		this.positionPercentage = positionPercentage_;
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
		Account rhs = (Account) obj_;
		return new EqualsBuilder().append(accountId, rhs.accountId).isEquals();
	}
	
	@Override
	public int hashCode() {		
		return Objects.hash(accountId);
	}
	
	@Override
	public Account clone() {
		return new Account(accountId, positionPercentage);
	}
}
