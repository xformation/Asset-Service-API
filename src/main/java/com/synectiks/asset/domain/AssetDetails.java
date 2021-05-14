package com.synectiks.asset.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;

/**
 * A AssetDetails.
 */
@Entity
@Table(name = "asset_details")
public class AssetDetails implements Serializable {

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

    @Column(name = "total_instances")
    private Integer totalInstances;

    @Size(max = 1000)
    @Column(name = "view_json_ref", length = 1000)
    private String viewJsonRef;

    @Lob
    @Column(name = "view_json")
    private byte[] viewJson;

    @Column(name = "view_json_content_type")
    private String viewJsonContentType;

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
    @JsonIgnoreProperties(value = "assetDetails", allowSetters = true)
    private CloudAsset cloudAsset;

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

    public AssetDetails name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public AssetDetails description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getTotalInstances() {
        return totalInstances;
    }

    public AssetDetails totalInstances(Integer totalInstances) {
        this.totalInstances = totalInstances;
        return this;
    }

    public void setTotalInstances(Integer totalInstances) {
        this.totalInstances = totalInstances;
    }

    public String getViewJsonRef() {
        return viewJsonRef;
    }

    public AssetDetails viewJsonRef(String viewJsonRef) {
        this.viewJsonRef = viewJsonRef;
        return this;
    }

    public void setViewJsonRef(String viewJsonRef) {
        this.viewJsonRef = viewJsonRef;
    }

    public byte[] getViewJson() {
        return viewJson;
    }

    public AssetDetails viewJson(byte[] viewJson) {
        this.viewJson = viewJson;
        return this;
    }

    public void setViewJson(byte[] viewJson) {
        this.viewJson = viewJson;
    }

    public String getViewJsonContentType() {
        return viewJsonContentType;
    }

    public AssetDetails viewJsonContentType(String viewJsonContentType) {
        this.viewJsonContentType = viewJsonContentType;
        return this;
    }

    public void setViewJsonContentType(String viewJsonContentType) {
        this.viewJsonContentType = viewJsonContentType;
    }

    public String getSourceJsonRef() {
        return sourceJsonRef;
    }

    public AssetDetails sourceJsonRef(String sourceJsonRef) {
        this.sourceJsonRef = sourceJsonRef;
        return this;
    }

    public void setSourceJsonRef(String sourceJsonRef) {
        this.sourceJsonRef = sourceJsonRef;
    }

    public byte[] getSourceJson() {
        return sourceJson;
    }

    public AssetDetails sourceJson(byte[] sourceJson) {
        this.sourceJson = sourceJson;
        return this;
    }

    public void setSourceJson(byte[] sourceJson) {
        this.sourceJson = sourceJson;
    }

    public String getSourceJsonContentType() {
        return sourceJsonContentType;
    }

    public AssetDetails sourceJsonContentType(String sourceJsonContentType) {
        this.sourceJsonContentType = sourceJsonContentType;
        return this;
    }

    public void setSourceJsonContentType(String sourceJsonContentType) {
        this.sourceJsonContentType = sourceJsonContentType;
    }

    public String getStatus() {
        return status;
    }

    public AssetDetails status(String status) {
        this.status = status;
        return this;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Instant getCreatedOn() {
        return createdOn;
    }

    public AssetDetails createdOn(Instant createdOn) {
        this.createdOn = createdOn;
        return this;
    }

    public void setCreatedOn(Instant createdOn) {
        this.createdOn = createdOn;
    }

    public Instant getUpdatedOn() {
        return updatedOn;
    }

    public AssetDetails updatedOn(Instant updatedOn) {
        this.updatedOn = updatedOn;
        return this;
    }

    public void setUpdatedOn(Instant updatedOn) {
        this.updatedOn = updatedOn;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public AssetDetails updatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
        return this;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public AssetDetails createdBy(String createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public CloudAsset getCloudAsset() {
        return cloudAsset;
    }

    public AssetDetails cloudAsset(CloudAsset cloudAsset) {
        this.cloudAsset = cloudAsset;
        return this;
    }

    public void setCloudAsset(CloudAsset cloudAsset) {
        this.cloudAsset = cloudAsset;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AssetDetails)) {
            return false;
        }
        return id != null && id.equals(((AssetDetails) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AssetDetails{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            ", totalInstances=" + getTotalInstances() +
            ", viewJsonRef='" + getViewJsonRef() + "'" +
            ", viewJson='" + getViewJson() + "'" +
            ", viewJsonContentType='" + getViewJsonContentType() + "'" +
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
