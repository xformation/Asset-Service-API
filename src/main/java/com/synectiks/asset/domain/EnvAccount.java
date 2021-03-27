package com.synectiks.asset.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;

/**
 * A EnvAccount.
 */
@Entity
@Table(name = "env_account")
public class EnvAccount implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "name")
    private String name;

    @Size(max = 5000)
    @Column(name = "description", length = 5000)
    private String description;

    @Column(name = "created_on")
    private Instant createdOn;

    @Column(name = "updated_on")
    private Instant updatedOn;

    @Column(name = "status")
    private String status;

    @Size(max = 1000)
    @Column(name = "client_id", length = 1000)
    private String clientId;

    @Size(max = 2000)
    @Column(name = "client_secret", length = 2000)
    private String clientSecret;

    @Column(name = "user_type")
    private String userType;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Lob
    @Column(name = "json_data")
    private byte[] jsonData;

    @Column(name = "json_data_content_type")
    private String jsonDataContentType;

    @Column(name = "updated_by")
    private String updatedBy;

    @Column(name = "created_by")
    private String createdBy;

    @ManyToOne
    @JsonIgnoreProperties(value = "envAccounts", allowSetters = true)
    private Environment environment;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public EnvAccount name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public EnvAccount description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Instant getCreatedOn() {
        return createdOn;
    }

    public EnvAccount createdOn(Instant createdOn) {
        this.createdOn = createdOn;
        return this;
    }

    public void setCreatedOn(Instant createdOn) {
        this.createdOn = createdOn;
    }

    public Instant getUpdatedOn() {
        return updatedOn;
    }

    public EnvAccount updatedOn(Instant updatedOn) {
        this.updatedOn = updatedOn;
        return this;
    }

    public void setUpdatedOn(Instant updatedOn) {
        this.updatedOn = updatedOn;
    }

    public String getStatus() {
        return status;
    }

    public EnvAccount status(String status) {
        this.status = status;
        return this;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getClientId() {
        return clientId;
    }

    public EnvAccount clientId(String clientId) {
        this.clientId = clientId;
        return this;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getClientSecret() {
        return clientSecret;
    }

    public EnvAccount clientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
        return this;
    }

    public void setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
    }

    public String getUserType() {
        return userType;
    }

    public EnvAccount userType(String userType) {
        this.userType = userType;
        return this;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getEmail() {
        return email;
    }

    public EnvAccount email(String email) {
        this.email = email;
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public EnvAccount password(String password) {
        this.password = password;
        return this;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public byte[] getJsonData() {
        return jsonData;
    }

    public EnvAccount jsonData(byte[] jsonData) {
        this.jsonData = jsonData;
        return this;
    }

    public void setJsonData(byte[] jsonData) {
        this.jsonData = jsonData;
    }

    public String getJsonDataContentType() {
        return jsonDataContentType;
    }

    public EnvAccount jsonDataContentType(String jsonDataContentType) {
        this.jsonDataContentType = jsonDataContentType;
        return this;
    }

    public void setJsonDataContentType(String jsonDataContentType) {
        this.jsonDataContentType = jsonDataContentType;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public EnvAccount updatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
        return this;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public EnvAccount createdBy(String createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Environment getEnvironment() {
        return environment;
    }

    public EnvAccount environment(Environment environment) {
        this.environment = environment;
        return this;
    }

    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof EnvAccount)) {
            return false;
        }
        return id != null && id.equals(((EnvAccount) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EnvAccount{" +
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
            ", jsonDataContentType='" + getJsonDataContentType() + "'" +
            ", updatedBy='" + getUpdatedBy() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            "}";
    }
}
