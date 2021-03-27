package com.synectiks.asset.service.dto;

import java.time.Instant;
import javax.validation.constraints.*;
import java.io.Serializable;
import javax.persistence.Lob;

/**
 * A DTO for the {@link com.synectiks.asset.domain.Environment} entity.
 */
public class EnvironmentDTO implements Serializable {
    
    private Long id;

    private String name;

    @Size(max = 5000)
    private String description;

    private Instant createdOn;

    private Instant updatedOn;

    private String status;

    private String scopes;

    private String authUrl;

    private String tokenUrl;

    private String apiUrl;

    private String updatedBy;

    private String createdBy;

    @Lob
    private byte[] jsonData;

    private String jsonDataContentType;
    
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

    public String getScopes() {
        return scopes;
    }

    public void setScopes(String scopes) {
        this.scopes = scopes;
    }

    public String getAuthUrl() {
        return authUrl;
    }

    public void setAuthUrl(String authUrl) {
        this.authUrl = authUrl;
    }

    public String getTokenUrl() {
        return tokenUrl;
    }

    public void setTokenUrl(String tokenUrl) {
        this.tokenUrl = tokenUrl;
    }

    public String getApiUrl() {
        return apiUrl;
    }

    public void setApiUrl(String apiUrl) {
        this.apiUrl = apiUrl;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof EnvironmentDTO)) {
            return false;
        }

        return id != null && id.equals(((EnvironmentDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EnvironmentDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            ", createdOn='" + getCreatedOn() + "'" +
            ", updatedOn='" + getUpdatedOn() + "'" +
            ", status='" + getStatus() + "'" +
            ", scopes='" + getScopes() + "'" +
            ", authUrl='" + getAuthUrl() + "'" +
            ", tokenUrl='" + getTokenUrl() + "'" +
            ", apiUrl='" + getApiUrl() + "'" +
            ", updatedBy='" + getUpdatedBy() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", jsonData='" + getJsonData() + "'" +
            "}";
    }
}
