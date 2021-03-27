package com.synectiks.asset.service.dto;

import java.time.Instant;
import javax.validation.constraints.*;
import java.io.Serializable;
import javax.persistence.Lob;

/**
 * A DTO for the {@link com.synectiks.asset.domain.EnvAccount} entity.
 */
public class EnvAccountDTO implements Serializable {
    
    private Long id;

    private String name;

    @Size(max = 5000)
    private String description;

    private Instant createdOn;

    private Instant updatedOn;

    private String status;

    @Size(max = 1000)
    private String clientId;

    @Size(max = 2000)
    private String clientSecret;

    private String userType;

    private String email;

    private String password;

    @Lob
    private byte[] jsonData;

    private String jsonDataContentType;
    private String updatedBy;

    private String createdBy;


    private Long environmentId;
    
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

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getClientSecret() {
        return clientSecret;
    }

    public void setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public Long getEnvironmentId() {
        return environmentId;
    }

    public void setEnvironmentId(Long environmentId) {
        this.environmentId = environmentId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof EnvAccountDTO)) {
            return false;
        }

        return id != null && id.equals(((EnvAccountDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EnvAccountDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            ", createdOn='" + getCreatedOn() + "'" +
            ", updatedOn='" + getUpdatedOn() + "'" +
            ", status='" + getStatus() + "'" +
            ", clientId='" + getClientId() + "'" +
            ", clientSecret='" + getClientSecret() + "'" +
            ", userType='" + getUserType() + "'" +
            ", email='" + getEmail() + "'" +
            ", password='" + getPassword() + "'" +
            ", jsonData='" + getJsonData() + "'" +
            ", updatedBy='" + getUpdatedBy() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", environmentId=" + getEnvironmentId() +
            "}";
    }
}
