package com.synectiks.asset.domain;

import java.io.Serializable;


public class Dashboard implements Serializable {

    private static final long serialVersionUID = 1L;
    
    private String cloudName;
    private String elementType;
    private String tenantId;
    private String accountId;
    private String inputType;
    private String fileName;
	
    private String inputSourceId; 
    private String title;
    private String slug;
    private String uid;
    private String data;
    private boolean isCloud = true;
    
	public String getCloudName() {
		return cloudName;
	}
	public void setCloudName(String cloudName) {
		this.cloudName = cloudName;
	}
	public String getElementType() {
		return elementType;
	}
	public void setElementType(String elementType) {
		this.elementType = elementType;
	}
	public String getTenantId() {
		return tenantId;
	}
	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}
	public String getAccountId() {
		return accountId;
	}
	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}
	public String getInputType() {
		return inputType;
	}
	public void setInputType(String inputType) {
		this.inputType = inputType;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getInputSourceId() {
		return inputSourceId;
	}
	public void setInputSourceId(String inputSourceId) {
		this.inputSourceId = inputSourceId;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getSlug() {
		return slug;
	}
	public void setSlug(String slug) {
		this.slug = slug;
	}
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}
	public boolean isCloud() {
		return isCloud;
	}
	public void setCloud(boolean isCloud) {
		this.isCloud = isCloud;
	}
	
 }
