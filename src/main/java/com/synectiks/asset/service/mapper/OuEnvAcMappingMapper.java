package com.synectiks.asset.service.mapper;


import com.synectiks.asset.domain.*;
import com.synectiks.asset.service.dto.OuEnvAcMappingDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link OuEnvAcMapping} and its DTO {@link OuEnvAcMappingDTO}.
 */
@Mapper(componentModel = "spring", uses = {OrganizationalUnitMapper.class, EnvAccountMapper.class})
public interface OuEnvAcMappingMapper extends EntityMapper<OuEnvAcMappingDTO, OuEnvAcMapping> {

    @Mapping(source = "organizationalUnit.id", target = "organizationalUnitId")
    @Mapping(source = "envAccount.id", target = "envAccountId")
    OuEnvAcMappingDTO toDto(OuEnvAcMapping ouEnvAcMapping);

    @Mapping(source = "organizationalUnitId", target = "organizationalUnit")
    @Mapping(source = "envAccountId", target = "envAccount")
    OuEnvAcMapping toEntity(OuEnvAcMappingDTO ouEnvAcMappingDTO);

    default OuEnvAcMapping fromId(Long id) {
        if (id == null) {
            return null;
        }
        OuEnvAcMapping ouEnvAcMapping = new OuEnvAcMapping();
        ouEnvAcMapping.setId(id);
        return ouEnvAcMapping;
    }
}
