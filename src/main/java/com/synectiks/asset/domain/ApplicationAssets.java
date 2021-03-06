package com.synectiks.asset.domain;


import javax.persistence.*;

import java.io.Serializable;
import java.time.Instant;

/**
 * A ApplicationAssets.
 */
@Entity
@Table(name = "application_assets")
public class ApplicationAssets implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "tenant_id")
    private String tenantId;

    @Column(name = "dashboard_uuid")
    private String dashboardUuid;

    @Column(name = "file_name")
    private String fileName;

    @Column(name = "cloud_type")
    private String cloudType;

    @Column(name = "element_type")
    private String elementType;

    @Column(name = "element_sub_type")
    private String elementSubType;

    @Column(name = "input_type")
    private String inputType;

    @Column(name = "dashboard_nature")
    private String dashboardNature;

    @Column(name = "status")
    private String status;

    @Column(name = "created_on")
    private Instant createdOn;

    @Column(name = "updated_on")
    private Instant updatedOn;

    @Column(name = "updated_by")
    private String updatedBy;

    @Column(name = "created_by")
    private String createdBy;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTenantId() {
        return tenantId;
    }

    public ApplicationAssets tenantId(String tenantId) {
        this.tenantId = tenantId;
        return this;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    public String getDashboardUuid() {
        return dashboardUuid;
    }

    public ApplicationAssets dashboardUuid(String dashboardUuid) {
        this.dashboardUuid = dashboardUuid;
        return this;
    }

    public void setDashboardUuid(String dashboardUuid) {
        this.dashboardUuid = dashboardUuid;
    }

    public String getFileName() {
        return fileName;
    }

    public ApplicationAssets fileName(String fileName) {
        this.fileName = fileName;
        return this;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getCloudType() {
        return cloudType;
    }

    public ApplicationAssets cloudType(String cloudType) {
        this.cloudType = cloudType;
        return this;
    }

    public void setCloudType(String cloudType) {
        this.cloudType = cloudType;
    }

    public String getElementType() {
        return elementType;
    }

    public ApplicationAssets elementType(String elementType) {
        this.elementType = elementType;
        return this;
    }

    public void setElementType(String elementType) {
        this.elementType = elementType;
    }

    public String getElementSubType() {
        return elementSubType;
    }

    public ApplicationAssets elementSubType(String elementSubType) {
        this.elementSubType = elementSubType;
        return this;
    }

    public void setElementSubType(String elementSubType) {
        this.elementSubType = elementSubType;
    }

    public String getInputType() {
        return inputType;
    }

    public ApplicationAssets inputType(String inputType) {
        this.inputType = inputType;
        return this;
    }

    public void setInputType(String inputType) {
        this.inputType = inputType;
    }

    public String getDashboardNature() {
        return dashboardNature;
    }

    public ApplicationAssets dashboardNature(String dashboardNature) {
        this.dashboardNature = dashboardNature;
        return this;
    }

    public void setDashboardNature(String dashboardNature) {
        this.dashboardNature = dashboardNature;
    }

    public String getStatus() {
        return status;
    }

    public ApplicationAssets status(String status) {
        this.status = status;
        return this;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Instant getCreatedOn() {
        return createdOn;
    }

    public ApplicationAssets createdOn(Instant createdOn) {
        this.createdOn = createdOn;
        return this;
    }

    public void setCreatedOn(Instant createdOn) {
        this.createdOn = createdOn;
    }

    public Instant getUpdatedOn() {
        return updatedOn;
    }

    public ApplicationAssets updatedOn(Instant updatedOn) {
        this.updatedOn = updatedOn;
        return this;
    }

    public void setUpdatedOn(Instant updatedOn) {
        this.updatedOn = updatedOn;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public ApplicationAssets updatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
        return this;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public ApplicationAssets createdBy(String createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ApplicationAssets)) {
            return false;
        }
        return id != null && id.equals(((ApplicationAssets) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ApplicationAssets{" +
            "id=" + getId() +
            ", tenantId='" + getTenantId() + "'" +
            ", dashboardUuid='" + getDashboardUuid() + "'" +
            ", fileName='" + getFileName() + "'" +
            ", cloudType='" + getCloudType() + "'" +
            ", elementType='" + getElementType() + "'" +
            ", elementSubType='" + getElementSubType() + "'" +
            ", inputType='" + getInputType() + "'" +
            ", dashboardNature='" + getDashboardNature() + "'" +
            ", status='" + getStatus() + "'" +
            ", createdOn='" + getCreatedOn() + "'" +
            ", updatedOn='" + getUpdatedOn() + "'" +
            ", updatedBy='" + getUpdatedBy() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            "}";
    }
}
