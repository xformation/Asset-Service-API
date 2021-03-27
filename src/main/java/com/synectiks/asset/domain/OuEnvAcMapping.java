package com.synectiks.asset.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

import java.io.Serializable;

/**
 * A OuEnvAcMapping.
 */
@Entity
@Table(name = "ou_env_ac_mapping")
public class OuEnvAcMapping implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @ManyToOne
    @JsonIgnoreProperties(value = "ouEnvAcMappings", allowSetters = true)
    private OrganizationalUnit organizationalUnit;

    @ManyToOne
    @JsonIgnoreProperties(value = "ouEnvAcMappings", allowSetters = true)
    private EnvAccount envAccount;

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

    public OuEnvAcMapping organizationalUnit(OrganizationalUnit organizationalUnit) {
        this.organizationalUnit = organizationalUnit;
        return this;
    }

    public void setOrganizationalUnit(OrganizationalUnit organizationalUnit) {
        this.organizationalUnit = organizationalUnit;
    }

    public EnvAccount getEnvAccount() {
        return envAccount;
    }

    public OuEnvAcMapping envAccount(EnvAccount envAccount) {
        this.envAccount = envAccount;
        return this;
    }

    public void setEnvAccount(EnvAccount envAccount) {
        this.envAccount = envAccount;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof OuEnvAcMapping)) {
            return false;
        }
        return id != null && id.equals(((OuEnvAcMapping) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "OuEnvAcMapping{" +
            "id=" + getId() +
            "}";
    }
}
