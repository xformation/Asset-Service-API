package com.synectiks.asset.domain;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * A InputConfig.
 */
@Entity
@Table(name = "input_config")
public class InputConfig implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "input_type")
    private String inputType;

    @Column(name = "status")
    private String status;

    @Column(name = "tenant_id")
    private String tenantId;

    @Lob
    @Column(name = "view_json")
    private byte[] viewJson;

    @Column(name = "view_json_content_type")
    private String viewJsonContentType;

    @ManyToOne
    @JsonIgnoreProperties(value = "inputConfigs", allowSetters = true)
    private Accounts accounts;

    @Transient
    @JsonProperty
    private List<Dashboard> enabledDashboardList;
    
    @Transient
    @JsonProperty
    private String enabledDashboard;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getInputType() {
        return inputType;
    }

    public InputConfig inputType(String inputType) {
        this.inputType = inputType;
        return this;
    }

    public void setInputType(String inputType) {
        this.inputType = inputType;
    }

    public String getStatus() {
        return status;
    }

    public InputConfig status(String status) {
        this.status = status;
        return this;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTenantId() {
        return tenantId;
    }

    public InputConfig tenantId(String tenantId) {
        this.tenantId = tenantId;
        return this;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    public byte[] getViewJson() {
        return viewJson;
    }

    public InputConfig viewJson(byte[] viewJson) {
        this.viewJson = viewJson;
        return this;
    }

    public void setViewJson(byte[] viewJson) {
        this.viewJson = viewJson;
    }

    public String getViewJsonContentType() {
        return viewJsonContentType;
    }

    public InputConfig viewJsonContentType(String viewJsonContentType) {
        this.viewJsonContentType = viewJsonContentType;
        return this;
    }

    public void setViewJsonContentType(String viewJsonContentType) {
        this.viewJsonContentType = viewJsonContentType;
    }

    public Accounts getAccounts() {
        return accounts;
    }

    public InputConfig accounts(Accounts accounts) {
        this.accounts = accounts;
        return this;
    }

    public void setAccounts(Accounts accounts) {
        this.accounts = accounts;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof InputConfig)) {
            return false;
        }
        return id != null && id.equals(((InputConfig) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "InputConfig{" +
            "id=" + getId() +
            ", inputType='" + getInputType() + "'" +
            ", status='" + getStatus() + "'" +
            ", tenantId='" + getTenantId() + "'" +
            ", viewJson='" + getViewJson() + "'" +
            ", viewJsonContentType='" + getViewJsonContentType() + "'" +
            "}";
    }

	public List<Dashboard> getEnabledDashboardList() {
		return enabledDashboardList;
	}

	public void setEnabledDashboardList(List<Dashboard> enabledDashboardList) {
		this.enabledDashboardList = enabledDashboardList;
	}

	public String getEnabledDashboard() {
		return enabledDashboard;
	}

	public void setEnabledDashboard(String enabledDashboard) {
		this.enabledDashboard = enabledDashboard;
	}
}
