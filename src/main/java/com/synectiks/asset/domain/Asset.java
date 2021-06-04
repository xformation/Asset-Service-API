package com.synectiks.asset.domain;


import java.io.Serializable;
import java.time.Instant;
import java.util.Arrays;


public class Asset implements Serializable {

    private static final long serialVersionUID = 1L;
    
    private Long id;
    private String accountId;
    private String type;
    private String title;
    private String description;
    private String sourceJsonRef;
    private byte[] sourceJson;
    private String sourceJsonContentType;
    private boolean status;
    private Instant createdOn;
    private Instant updatedOn;
    private String updatedBy;
    private String createdBy;
    private String unit;
    private String instance;
    private boolean isOpened = false;
    private Asset[] subData;
    
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
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
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getSourceJsonRef() {
		return sourceJsonRef;
	}
	public void setSourceJsonRef(String sourceJsonRef) {
		this.sourceJsonRef = sourceJsonRef;
	}
	public byte[] getSourceJson() {
		return sourceJson;
	}
	public void setSourceJson(byte[] sourceJson) {
		this.sourceJson = sourceJson;
	}
	public String getSourceJsonContentType() {
		return sourceJsonContentType;
	}
	public void setSourceJsonContentType(String sourceJsonContentType) {
		this.sourceJsonContentType = sourceJsonContentType;
	}
	public boolean isStatus() {
		return status;
	}
	public void setStatus(boolean status) {
		this.status = status;
	}
	public Instant getCreatedOn() {
		return createdOn;
	}
	public void setCreatedOn(Instant createdOn) {
		this.createdOn = createdOn;
	}
	public Instant getUpdatedOn() {
		return updatedOn;
	}
	public void setUpdatedOn(Instant updatedOn) {
		this.updatedOn = updatedOn;
	}
	public String getUpdatedBy() {
		return updatedBy;
	}
	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
	public String getInstance() {
		return instance;
	}
	public void setInstance(String instance) {
		this.instance = instance;
	}
	public boolean isOpened() {
		return isOpened;
	}
	public void setOpened(boolean isOpened) {
		this.isOpened = isOpened;
	}
	public Asset[] getSubData() {
		return subData;
	}
	public void setSubData(Asset[] subData) {
		this.subData = subData;
	}
	
	@Override
	public int hashCode() {
		final int prime = 41;
		int result = 1;
		result = prime * result + ((accountId == null) ? 0 : accountId.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((title == null) ? 0 : title.hashCode());
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
		Asset other = (Asset) obj;
		if (accountId == null) {
			if (other.accountId != null)
				return false;
		} else if (!accountId.equals(other.accountId))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (title == null) {
			if (other.title != null)
				return false;
		} else if (!title.equals(other.title))
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
		return "Asset [id=" + id + ", accountId=" + accountId + ", type=" + type + ", title=" + title + ", description="
				+ description + ", sourceJsonRef=" + sourceJsonRef + ", status=" + status + ", createdOn=" + createdOn
				+ ", updatedOn=" + updatedOn + ", updatedBy=" + updatedBy + ", createdBy=" + createdBy + ", unit="
				+ unit + ", instance=" + instance + ", isOpened=" + isOpened + ", subData=" + Arrays.toString(subData)
				+ "]";
	}
    
    
    
    
}