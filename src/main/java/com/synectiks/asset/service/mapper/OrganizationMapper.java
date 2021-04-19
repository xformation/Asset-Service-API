package com.synectiks.asset.service.mapper;


import com.synectiks.asset.domain.*;
import com.synectiks.asset.service.dto.OrganizationDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Organization} and its DTO {@link OrganizationDTO}.
 */
@Mapper(componentModel = "spring", uses = {EnvironmentMapper.class})
public interface OrganizationMapper extends EntityMapper<OrganizationDTO, Organization> {

    @Mapping(source = "environment.id", target = "environmentId")
    OrganizationDTO toDto(Organization organization);

    @Mapping(source = "environmentId", target = "environment")
    Organization toEntity(OrganizationDTO organizationDTO);

    default Organization fromId(Long id) {
        if (id == null) {
            return null;
        }
        Organization organization = new Organization();
        organization.setId(id);
        return organization;
    }
}
