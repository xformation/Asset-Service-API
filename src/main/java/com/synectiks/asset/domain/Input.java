package com.synectiks.asset.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;

/**
 * A Input.
 */
@Entity
@Table(name = "input")
public class Input implements Serializable {

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

    @Column(name = "source")
    private String source;

    @Column(name = "type")
    private String type;

    @Column(name = "url")
    private String url;

    @Column(name = "user_id")
    private String userId;

    @Column(name = "password")
    private String password;

    @Column(name = "database")
    private String database;

    @Lob
    @Column(name = "input_data")
    private byte[] inputData;

    @Column(name = "input_data_content_type")
    private String inputDataContentType;

    @Column(name = "access")
    private String access;

    @Column(name = "basic_auth")
    private String basicAuth;

    @Column(name = "basic_auth_user")
    private String basicAuthUser;

    @Column(name = "basic_auth_password")
    private String basicAuthPassword;

    @Column(name = "with_credentials")
    private String withCredentials;

    @Lob
    @Column(name = "secure_input_data")
    private byte[] secureInputData;

    @Column(name = "secure_input_data_content_type")
    private String secureInputDataContentType;

    @Column(name = "dapr_pub_sub_name")
    private String daprPubSubName;

    @Column(name = "dapr_topic")
    private String daprTopic;

    @Column(name = "dapr_route")
    private String daprRoute;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "updated_by")
    private String updatedBy;

    @ManyToOne
    @JsonIgnoreProperties(value = "inputs", allowSetters = true)
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

    public Input name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public Input description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Instant getCreatedOn() {
        return createdOn;
    }

    public Input createdOn(Instant createdOn) {
        this.createdOn = createdOn;
        return this;
    }

    public void setCreatedOn(Instant createdOn) {
        this.createdOn = createdOn;
    }

    public Instant getUpdatedOn() {
        return updatedOn;
    }

    public Input updatedOn(Instant updatedOn) {
        this.updatedOn = updatedOn;
        return this;
    }

    public void setUpdatedOn(Instant updatedOn) {
        this.updatedOn = updatedOn;
    }

    public String getStatus() {
        return status;
    }

    public Input status(String status) {
        this.status = status;
        return this;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSource() {
        return source;
    }

    public Input source(String source) {
        this.source = source;
        return this;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getType() {
        return type;
    }

    public Input type(String type) {
        this.type = type;
        return this;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public Input url(String url) {
        this.url = url;
        return this;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUserId() {
        return userId;
    }

    public Input userId(String userId) {
        this.userId = userId;
        return this;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPassword() {
        return password;
    }

    public Input password(String password) {
        this.password = password;
        return this;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDatabase() {
        return database;
    }

    public Input database(String database) {
        this.database = database;
        return this;
    }

    public void setDatabase(String database) {
        this.database = database;
    }

    public byte[] getInputData() {
        return inputData;
    }

    public Input inputData(byte[] inputData) {
        this.inputData = inputData;
        return this;
    }

    public void setInputData(byte[] inputData) {
        this.inputData = inputData;
    }

    public String getInputDataContentType() {
        return inputDataContentType;
    }

    public Input inputDataContentType(String inputDataContentType) {
        this.inputDataContentType = inputDataContentType;
        return this;
    }

    public void setInputDataContentType(String inputDataContentType) {
        this.inputDataContentType = inputDataContentType;
    }

    public String getAccess() {
        return access;
    }

    public Input access(String access) {
        this.access = access;
        return this;
    }

    public void setAccess(String access) {
        this.access = access;
    }

    public String getBasicAuth() {
        return basicAuth;
    }

    public Input basicAuth(String basicAuth) {
        this.basicAuth = basicAuth;
        return this;
    }

    public void setBasicAuth(String basicAuth) {
        this.basicAuth = basicAuth;
    }

    public String getBasicAuthUser() {
        return basicAuthUser;
    }

    public Input basicAuthUser(String basicAuthUser) {
        this.basicAuthUser = basicAuthUser;
        return this;
    }

    public void setBasicAuthUser(String basicAuthUser) {
        this.basicAuthUser = basicAuthUser;
    }

    public String getBasicAuthPassword() {
        return basicAuthPassword;
    }

    public Input basicAuthPassword(String basicAuthPassword) {
        this.basicAuthPassword = basicAuthPassword;
        return this;
    }

    public void setBasicAuthPassword(String basicAuthPassword) {
        this.basicAuthPassword = basicAuthPassword;
    }

    public String getWithCredentials() {
        return withCredentials;
    }

    public Input withCredentials(String withCredentials) {
        this.withCredentials = withCredentials;
        return this;
    }

    public void setWithCredentials(String withCredentials) {
        this.withCredentials = withCredentials;
    }

    public byte[] getSecureInputData() {
        return secureInputData;
    }

    public Input secureInputData(byte[] secureInputData) {
        this.secureInputData = secureInputData;
        return this;
    }

    public void setSecureInputData(byte[] secureInputData) {
        this.secureInputData = secureInputData;
    }

    public String getSecureInputDataContentType() {
        return secureInputDataContentType;
    }

    public Input secureInputDataContentType(String secureInputDataContentType) {
        this.secureInputDataContentType = secureInputDataContentType;
        return this;
    }

    public void setSecureInputDataContentType(String secureInputDataContentType) {
        this.secureInputDataContentType = secureInputDataContentType;
    }

    public String getDaprPubSubName() {
        return daprPubSubName;
    }

    public Input daprPubSubName(String daprPubSubName) {
        this.daprPubSubName = daprPubSubName;
        return this;
    }

    public void setDaprPubSubName(String daprPubSubName) {
        this.daprPubSubName = daprPubSubName;
    }

    public String getDaprTopic() {
        return daprTopic;
    }

    public Input daprTopic(String daprTopic) {
        this.daprTopic = daprTopic;
        return this;
    }

    public void setDaprTopic(String daprTopic) {
        this.daprTopic = daprTopic;
    }

    public String getDaprRoute() {
        return daprRoute;
    }

    public Input daprRoute(String daprRoute) {
        this.daprRoute = daprRoute;
        return this;
    }

    public void setDaprRoute(String daprRoute) {
        this.daprRoute = daprRoute;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public Input createdBy(String createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public Input updatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
        return this;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Environment getEnvironment() {
        return environment;
    }

    public Input environment(Environment environment) {
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
        if (!(o instanceof Input)) {
            return false;
        }
        return id != null && id.equals(((Input) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Input{" +
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
            ", inputDataContentType='" + getInputDataContentType() + "'" +
            ", access='" + getAccess() + "'" +
            ", basicAuth='" + getBasicAuth() + "'" +
            ", basicAuthUser='" + getBasicAuthUser() + "'" +
            ", basicAuthPassword='" + getBasicAuthPassword() + "'" +
            ", withCredentials='" + getWithCredentials() + "'" +
            ", secureInputData='" + getSecureInputData() + "'" +
            ", secureInputDataContentType='" + getSecureInputDataContentType() + "'" +
            ", daprPubSubName='" + getDaprPubSubName() + "'" +
            ", daprTopic='" + getDaprTopic() + "'" +
            ", daprRoute='" + getDaprRoute() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", updatedBy='" + getUpdatedBy() + "'" +
            "}";
    }
}
