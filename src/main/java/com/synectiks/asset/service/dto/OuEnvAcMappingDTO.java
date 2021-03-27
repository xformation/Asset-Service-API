package com.synectiks.asset.service.dto;

import java.io.Serializable;

/**
 * A DTO for the {@link com.synectiks.asset.domain.OuEnvAcMapping} entity.
 */
public class OuEnvAcMappingDTO implements Serializable {
    
    private Long id;


    private Long organizationalUnitId;

    private Long envAccountId;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getOrganizationalUnitId() {
        return organizationalUnitId;
    }

    public void setOrganizationalUnitId(Long organizationalUnitId) {
        this.organizationalUnitId = organizationalUnitId;
    }

    public Long getEnvAccountId() {
        return envAccountId;
    }

    public void setEnvAccountId(Long envAccountId) {
        this.envAccountId = envAccountId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof OuEnvAcMappingDTO)) {
            return false;
        }

        return id != null && id.equals(((OuEnvAcMappingDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "OuEnvAcMappingDTO{" +
            "id=" + getId() +
            ", organizationalUnitId=" + getOrganizationalUnitId() +
            ", envAccountId=" + getEnvAccountId() +
            "}";
    }
}
