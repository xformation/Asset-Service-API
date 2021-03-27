package com.synectiks.asset.service.dto;

import java.time.Instant;
import javax.validation.constraints.*;
import java.io.Serializable;
import javax.persistence.Lob;

/**
 * A DTO for the {@link com.synectiks.asset.domain.Dashboard} entity.
 */
public class DashboardDTO implements Serializable {
    
    private Long id;

    private String name;

    @Size(max = 5000)
    private String description;

    private Instant createdOn;

    private Instant updatedOn;

    private String status;

    @Size(max = 3)
    private String isMonitored;

    @Lob
    private byte[] jsonData;

    private String jsonDataContentType;
    private String createdBy;

    private String updatedBy;


    private Long inputId;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getIsMonitored() {
        return isMonitored;
    }

    public void setIsMonitored(String isMonitored) {
        this.isMonitored = isMonitored;
    }

    public byte[] getJsonData() {
        return jsonData;
    }

    public void setJsonData(byte[] jsonData) {
        this.jsonData = jsonData;
    }

    public String getJsonDataContentType() {
        return jsonDataContentType;
    }

    public void setJsonDataContentType(String jsonDataContentType) {
        this.jsonDataContentType = jsonDataContentType;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Long getInputId() {
        return inputId;
    }

    public void setInputId(Long inputId) {
        this.inputId = inputId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DashboardDTO)) {
            return false;
        }

        return id != null && id.equals(((DashboardDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DashboardDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            ", createdOn='" + getCreatedOn() + "'" +
            ", updatedOn='" + getUpdatedOn() + "'" +
            ", status='" + getStatus() + "'" +
            ", isMonitored='" + getIsMonitored() + "'" +
            ", jsonData='" + getJsonData() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", updatedBy='" + getUpdatedBy() + "'" +
            ", inputId=" + getInputId() +
            "}";
    }
}
