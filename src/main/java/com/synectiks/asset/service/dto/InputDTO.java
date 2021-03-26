package com.synectiks.asset.service.dto;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;
import javax.persistence.Lob;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.synectiks.asset.domain.Input} entity.
 */
public class InputDTO implements Serializable {

  private Long id;

  private String name;

  @Size(max = 5000)
  private String description;

  private Instant createdOn;

  private Instant updatedOn;

  private String status;

  private String source;

  private String type;

  private String url;

  private String userId;

  private String password;

  private String database;

  @Lob
  private byte[] inputData;

  private String inputDataContentType;
  private String access;

  private String basicAuth;

  private String basicAuthUser;

  private String basicAuthPassword;

  private String withCredentials;

  private String secureInputData;

  private EnvAccountDTO envAccount;

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

  public String getSource() {
    return source;
  }

  public void setSource(String source) {
    this.source = source;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public String getUserId() {
    return userId;
  }

  public void setUserId(String userId) {
    this.userId = userId;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getDatabase() {
    return database;
  }

  public void setDatabase(String database) {
    this.database = database;
  }

  public byte[] getInputData() {
    return inputData;
  }

  public void setInputData(byte[] inputData) {
    this.inputData = inputData;
  }

  public String getInputDataContentType() {
    return inputDataContentType;
  }

  public void setInputDataContentType(String inputDataContentType) {
    this.inputDataContentType = inputDataContentType;
  }

  public String getAccess() {
    return access;
  }

  public void setAccess(String access) {
    this.access = access;
  }

  public String getBasicAuth() {
    return basicAuth;
  }

  public void setBasicAuth(String basicAuth) {
    this.basicAuth = basicAuth;
  }

  public String getBasicAuthUser() {
    return basicAuthUser;
  }

  public void setBasicAuthUser(String basicAuthUser) {
    this.basicAuthUser = basicAuthUser;
  }

  public String getBasicAuthPassword() {
    return basicAuthPassword;
  }

  public void setBasicAuthPassword(String basicAuthPassword) {
    this.basicAuthPassword = basicAuthPassword;
  }

  public String getWithCredentials() {
    return withCredentials;
  }

  public void setWithCredentials(String withCredentials) {
    this.withCredentials = withCredentials;
  }

  public String getSecureInputData() {
    return secureInputData;
  }

  public void setSecureInputData(String secureInputData) {
    this.secureInputData = secureInputData;
  }

  public EnvAccountDTO getEnvAccount() {
    return envAccount;
  }

  public void setEnvAccount(EnvAccountDTO envAccount) {
    this.envAccount = envAccount;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof InputDTO)) {
      return false;
    }

    InputDTO inputDTO = (InputDTO) o;
    if (this.id == null) {
      return false;
    }
    return Objects.equals(this.id, inputDTO.id);
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.id);
  }

  // prettier-ignore
    @Override
    public String toString() {
        return "InputDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            ", createdOn='" + getCreatedOn() + "'" +
            ", updatedOn='" + getUpdatedOn() + "'" +
            ", status='" + getStatus() + "'" +
            ", source='" + getSource() + "'" +
            ", type='" + getType() + "'" +
            ", url='" + getUrl() + "'" +
            ", userId='" + getUserId() + "'" +
            ", password='" + getPassword() + "'" +
            ", database='" + getDatabase() + "'" +
            ", inputData='" + getInputData() + "'" +
            ", access='" + getAccess() + "'" +
            ", basicAuth='" + getBasicAuth() + "'" +
            ", basicAuthUser='" + getBasicAuthUser() + "'" +
            ", basicAuthPassword='" + getBasicAuthPassword() + "'" +
            ", withCredentials='" + getWithCredentials() + "'" +
            ", secureInputData='" + getSecureInputData() + "'" +
            ", envAccount=" + getEnvAccount() +
            "}";
    }
}
