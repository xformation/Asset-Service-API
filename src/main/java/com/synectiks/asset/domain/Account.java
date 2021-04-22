package com.synectiks.asset.domain;


import java.io.Serializable;
import java.util.List;


public class Account implements Serializable {

    private static final long serialVersionUID = 1L;
    private Environment environment;
    private Organization organization;
    private OrganizationalUnit organizationalUnit;
    private List<OrganizationalUnit> organizationalUnitList;
    
	public List<OrganizationalUnit> getOrganizationalUnitList() {
		return organizationalUnitList;
	}
	public void setOrganizationalUnitList(List<OrganizationalUnit> organizationalUnitList) {
		this.organizationalUnitList = organizationalUnitList;
	}
	public Environment getEnvironment() {
		return environment;
	}
	public void setEnvironment(Environment environment) {
		this.environment = environment;
	}
	public Organization getOrganization() {
		return organization;
	}
	public void setOrganization(Organization organization) {
		this.organization = organization;
	}
	public OrganizationalUnit getOrganizationalUnit() {
		return organizationalUnit;
	}
	public void setOrganizationalUnit(OrganizationalUnit organizationalUnit) {
		this.organizationalUnit = organizationalUnit;
	}
    
    
}
