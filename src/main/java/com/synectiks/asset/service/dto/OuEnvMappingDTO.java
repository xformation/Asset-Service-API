package com.synectiks.asset.service.dto;

import java.io.Serializable;

/**
 * A DTO for the {@link com.synectiks.asset.domain.OuEnvMapping} entity.
 */
public class OuEnvMappingDTO implements Serializable {
    
    private Long id;


    private Long organizationalUnitId;

    private Long environmentId;
    
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

    public Long getEnvironmentId() {
        return environmentId;
    }

    public void setEnvironmentId(Long environmentId) {
        this.environmentId = environmentId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof OuEnvMappingDTO)) {
            return false;
        }

        return id != null && id.equals(((OuEnvMappingDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "OuEnvMappingDTO{" +
            "id=" + getId() +
            ", organizationalUnitId=" + getOrganizationalUnitId() +
            ", environmentId=" + getEnvironmentId() +
            "}";
    }
}
