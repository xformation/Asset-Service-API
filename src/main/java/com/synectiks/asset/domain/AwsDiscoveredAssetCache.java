package com.synectiks.asset.domain;

import java.io.Serializable;
import java.util.List;

import com.synectiks.aws.entities.vpc.XformVpc;

public class AwsDiscoveredAssetCache implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String accountId;
	private List<XformVpc> vpcList;
	
	public String getAccountId() {
		return accountId;
	}
	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}
	public List<XformVpc> getVpcList() {
		return vpcList;
	}
	public void setVpcList(List<XformVpc> vpcList) {
		this.vpcList = vpcList;
	}
	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((accountId == null) ? 0 : accountId.hashCode());
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
		AwsDiscoveredAssetCache other = (AwsDiscoveredAssetCache) obj;
		if (accountId == null) {
			if (other.accountId != null)
				return false;
		} else if (!accountId.equals(other.accountId))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return "AwsDiscoveredAssetCache [accountId=" + accountId + "]";
	}
	
	
}
