package com.synectiks.asset.service.mapper;


import com.synectiks.asset.domain.*;
import com.synectiks.asset.service.dto.OuEnvMappingDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link OuEnvMapping} and its DTO {@link OuEnvMappingDTO}.
 */
@Mapper(componentModel = "spring", uses = {OrganizationalUnitMapper.class, EnvironmentMapper.class})
public interface OuEnvMappingMapper extends EntityMapper<OuEnvMappingDTO, OuEnvMapping> {

    @Mapping(source = "organizationalUnit.id", target = "organizationalUnitId")
    @Mapping(source = "environment.id", target = "environmentId")
    OuEnvMappingDTO toDto(OuEnvMapping ouEnvMapping);

    @Mapping(source = "organizationalUnitId", target = "organizationalUnit")
    @Mapping(source = "environmentId", target = "environment")
    OuEnvMapping toEntity(OuEnvMappingDTO ouEnvMappingDTO);

    default OuEnvMapping fromId(Long id) {
        if (id == null) {
            return null;
        }
        OuEnvMapping ouEnvMapping = new OuEnvMapping();
        ouEnvMapping.setId(id);
        return ouEnvMapping;
    }
}
