package com.synectiks.asset.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

import java.io.Serializable;

/**
 * A OuEnvMapping.
 */
@Entity
@Table(name = "ou_env_mapping")
public class OuEnvMapping implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @ManyToOne
    @JsonIgnoreProperties(value = "ouEnvMappings", allowSetters = true)
    private OrganizationalUnit organizationalUnit;

    @ManyToOne
    @JsonIgnoreProperties(value = "ouEnvMappings", allowSetters = true)
    private Environment environment;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public OrganizationalUnit getOrganizationalUnit() {
        return organizationalUnit;
    }

    public OuEnvMapping organizationalUnit(OrganizationalUnit organizationalUnit) {
        this.organizationalUnit = organizationalUnit;
        return this;
    }

    public void setOrganizationalUnit(OrganizationalUnit organizationalUnit) {
        this.organizationalUnit = organizationalUnit;
    }

    public Environment getEnvironment() {
        return environment;
    }

    public OuEnvMapping environment(Environment environment) {
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
        if (!(o instanceof OuEnvMapping)) {
            return false;
        }
        return id != null && id.equals(((OuEnvMapping) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "OuEnvMapping{" +
            "id=" + getId() +
            "}";
    }
}
