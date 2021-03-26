package com.synectiks.asset.domain;

import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A Environment.
 */
@Entity
@Table(name = "environment")
public class Environment implements Serializable {

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

  @Column(name = "scopes")
  private String scopes;

  @Column(name = "auth_url")
  private String authUrl;

  @Column(name = "token_url")
  private String tokenUrl;

  @Column(name = "api_url")
  private String apiUrl;

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

  public Environment id(Long id) {
    this.id = id;
    return this;
  }

  public String getName() {
    return this.name;
  }

  public Environment name(String name) {
    this.name = name;
    return this;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getDescription() {
    return this.description;
  }

  public Environment description(String description) {
    this.description = description;
    return this;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public Instant getCreatedOn() {
    return this.createdOn;
  }

  public Environment createdOn(Instant createdOn) {
    this.createdOn = createdOn;
    return this;
  }

  public void setCreatedOn(Instant createdOn) {
    this.createdOn = createdOn;
  }

  public Instant getUpdatedOn() {
    return this.updatedOn;
  }

  public Environment updatedOn(Instant updatedOn) {
    this.updatedOn = updatedOn;
    return this;
  }

  public void setUpdatedOn(Instant updatedOn) {
    this.updatedOn = updatedOn;
  }

  public String getStatus() {
    return this.status;
  }

  public Environment status(String status) {
    this.status = status;
    return this;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public String getScopes() {
    return this.scopes;
  }

  public Environment scopes(String scopes) {
    this.scopes = scopes;
    return this;
  }

  public void setScopes(String scopes) {
    this.scopes = scopes;
  }

  public String getAuthUrl() {
    return this.authUrl;
  }

  public Environment authUrl(String authUrl) {
    this.authUrl = authUrl;
    return this;
  }

  public void setAuthUrl(String authUrl) {
    this.authUrl = authUrl;
  }

  public String getTokenUrl() {
    return this.tokenUrl;
  }

  public Environment tokenUrl(String tokenUrl) {
    this.tokenUrl = tokenUrl;
    return this;
  }

  public void setTokenUrl(String tokenUrl) {
    this.tokenUrl = tokenUrl;
  }

  public String getApiUrl() {
    return this.apiUrl;
  }

  public Environment apiUrl(String apiUrl) {
    this.apiUrl = apiUrl;
    return this;
  }

  public void setApiUrl(String apiUrl) {
    this.apiUrl = apiUrl;
  }

  public String getUpdatedBy() {
    return this.updatedBy;
  }

  public Environment updatedBy(String updatedBy) {
    this.updatedBy = updatedBy;
    return this;
  }

  public void setUpdatedBy(String updatedBy) {
    this.updatedBy = updatedBy;
  }

  public String getCreatedBy() {
    return this.createdBy;
  }

  public Environment createdBy(String createdBy) {
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
    if (!(o instanceof Environment)) {
      return false;
    }
    return id != null && id.equals(((Environment) o).id);
  }

  @Override
  public int hashCode() {
    // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
    return getClass().hashCode();
  }

  // prettier-ignore
    @Override
    public String toString() {
        return "Environment{" +
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
            "}";
    }
}
