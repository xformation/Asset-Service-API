package com.synectiks.asset.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;

/**
 * A Accounts.
 */
@Entity
@Table(name = "accounts")
public class Accounts implements Serializable {

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

    @Column(name = "account_id")
    private String accountId;

    @Column(name = "tenant_id")
    private String tenantId;

    @Column(name = "access_key")
    private String accessKey;

    @Column(name = "secret_key")
    private String secretKey;

    @Column(name = "region")
    private String region;

    @Column(name = "bucket")
    private String bucket;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "cloud_type")
    private String cloudType;

    @Size(max = 1000)
    @Column(name = "source_json_ref", length = 1000)
    private String sourceJsonRef;

    @Lob
    @Column(name = "source_json")
    private byte[] sourceJson;

    @Column(name = "source_json_content_type")
    private String sourceJsonContentType;

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

    @ManyToOne
    @JsonIgnoreProperties(value = "accounts", allowSetters = true)
    private Organization organization;

    @ManyToOne
    @JsonIgnoreProperties(value = "accounts", allowSetters = true)
    private OrganizationalUnit organizationalUnit;

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

    public Accounts name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public Accounts description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAccountId() {
        return accountId;
    }

    public Accounts accountId(String accountId) {
        this.accountId = accountId;
        return this;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getTenantId() {
        return tenantId;
    }

    public Accounts tenantId(String tenantId) {
        this.tenantId = tenantId;
        return this;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    public String getAccessKey() {
        return accessKey;
    }

    public Accounts accessKey(String accessKey) {
        this.accessKey = accessKey;
        return this;
    }

    public void setAccessKey(String accessKey) {
        this.accessKey = accessKey;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public Accounts secretKey(String secretKey) {
        this.secretKey = secretKey;
        return this;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public String getRegion() {
        return region;
    }

    public Accounts region(String region) {
        this.region = region;
        return this;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getBucket() {
        return bucket;
    }

    public Accounts bucket(String bucket) {
        this.bucket = bucket;
        return this;
    }

    public void setBucket(String bucket) {
        this.bucket = bucket;
    }

    public String getEmail() {
        return email;
    }

    public Accounts email(String email) {
        this.email = email;
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public Accounts password(String password) {
        this.password = password;
        return this;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCloudType() {
        return cloudType;
    }

    public Accounts cloudType(String cloudType) {
        this.cloudType = cloudType;
        return this;
    }

    public void setCloudType(String cloudType) {
        this.cloudType = cloudType;
    }

    public String getSourceJsonRef() {
        return sourceJsonRef;
    }

    public Accounts sourceJsonRef(String sourceJsonRef) {
        this.sourceJsonRef = sourceJsonRef;
        return this;
    }

    public void setSourceJsonRef(String sourceJsonRef) {
        this.sourceJsonRef = sourceJsonRef;
    }

    public byte[] getSourceJson() {
        return sourceJson;
    }

    public Accounts sourceJson(byte[] sourceJson) {
        this.sourceJson = sourceJson;
        return this;
    }

    public void setSourceJson(byte[] sourceJson) {
        this.sourceJson = sourceJson;
    }

    public String getSourceJsonContentType() {
        return sourceJsonContentType;
    }

    public Accounts sourceJsonContentType(String sourceJsonContentType) {
        this.sourceJsonContentType = sourceJsonContentType;
        return this;
    }

    public void setSourceJsonContentType(String sourceJsonContentType) {
        this.sourceJsonContentType = sourceJsonContentType;
    }

    public String getStatus() {
        return status;
    }

    public Accounts status(String status) {
        this.status = status;
        return this;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Instant getCreatedOn() {
        return createdOn;
    }

    public Accounts createdOn(Instant createdOn) {
        this.createdOn = createdOn;
        return this;
    }

    public void setCreatedOn(Instant createdOn) {
        this.createdOn = createdOn;
    }

    public Instant getUpdatedOn() {
        return updatedOn;
    }

    public Accounts updatedOn(Instant updatedOn) {
        this.updatedOn = updatedOn;
        return this;
    }

    public void setUpdatedOn(Instant updatedOn) {
        this.updatedOn = updatedOn;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public Accounts updatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
        return this;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public Accounts createdBy(String createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Organization getOrganization() {
        return organization;
    }

    public Accounts organization(Organization organization) {
        this.organization = organization;
        return this;
    }

    public void setOrganization(Organization organization) {
        this.organization = organization;
    }

    public OrganizationalUnit getOrganizationalUnit() {
        return organizationalUnit;
    }

    public Accounts organizationalUnit(OrganizationalUnit organizationalUnit) {
        this.organizationalUnit = organizationalUnit;
        return this;
    }

    public void setOrganizationalUnit(OrganizationalUnit organizationalUnit) {
        this.organizationalUnit = organizationalUnit;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Accounts)) {
            return false;
        }
        return id != null && id.equals(((Accounts) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Accounts{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            ", accountId='" + getAccountId() + "'" +
            ", tenantId='" + getTenantId() + "'" +
            ", accessKey='" + getAccessKey() + "'" +
            ", secretKey='" + getSecretKey() + "'" +
            ", region='" + getRegion() + "'" +
            ", bucket='" + getBucket() + "'" +
            ", email='" + getEmail() + "'" +
            ", password='" + getPassword() + "'" +
            ", cloudType='" + getCloudType() + "'" +
            ", sourceJsonRef='" + getSourceJsonRef() + "'" +
            ", sourceJson='" + getSourceJson() + "'" +
            ", sourceJsonContentType='" + getSourceJsonContentType() + "'" +
            ", status='" + getStatus() + "'" +
            ", createdOn='" + getCreatedOn() + "'" +
            ", updatedOn='" + getUpdatedOn() + "'" +
            ", updatedBy='" + getUpdatedBy() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            "}";
    }
}
