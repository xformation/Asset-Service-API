package com.synectiks.asset.domain;

import java.io.Serializable;

public class AwsDiscoveredAssetKey implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String accountId;
	private String type;
	
	public AwsDiscoveredAssetKey (String accountId, String type) {
		this.accountId = accountId;
		this.type = type;
	}
	
	public AwsDiscoveredAssetKey () {}
	
	public String getAccountId() {
		return accountId;
	}
	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((accountId == null) ? 0 : accountId.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AwsDiscoveredAssetKey other = (AwsDiscoveredAssetKey) obj;
		if (accountId == null) {
			if (other.accountId != null)
				return false;
		} else if (!accountId.equals(other.accountId))
			return false;
		if (type == null) {
			if (other.type != null)
				return false;
		} else if (!type.equals(other.type))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "AwsDiscoveredAssetKey [accountId=" + accountId + ", type=" + type + "]";
	}
	
	
	
	
}
