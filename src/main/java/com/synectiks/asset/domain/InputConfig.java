package com.synectiks.asset.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

import java.io.Serializable;

/**
 * A InputConfig.
 */
@Entity
@Table(name = "input_config")
public class InputConfig implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "input_type")
    private String inputType;

    @Column(name = "status")
    private String status;

    @ManyToOne
    @JsonIgnoreProperties(value = "inputConfigs", allowSetters = true)
    private Accounts accounts;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getInputType() {
        return inputType;
    }

    public InputConfig inputType(String inputType) {
        this.inputType = inputType;
        return this;
    }

    public void setInputType(String inputType) {
        this.inputType = inputType;
    }

    public String getStatus() {
        return status;
    }

    public InputConfig status(String status) {
        this.status = status;
        return this;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Accounts getAccounts() {
        return accounts;
    }

    public InputConfig accounts(Accounts accounts) {
        this.accounts = accounts;
        return this;
    }

    public void setAccounts(Accounts accounts) {
        this.accounts = accounts;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof InputConfig)) {
            return false;
        }
        return id != null && id.equals(((InputConfig) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "InputConfig{" +
            "id=" + getId() +
            ", inputType='" + getInputType() + "'" +
            ", status='" + getStatus() + "'" +
            "}";
    }
}
