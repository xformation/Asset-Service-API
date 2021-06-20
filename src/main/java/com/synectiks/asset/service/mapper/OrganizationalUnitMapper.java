package com.synectiks.asset.service.mapper;


import com.synectiks.asset.domain.*;
import com.synectiks.asset.service.dto.OrganizationalUnitDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link OrganizationalUnit} and its DTO {@link OrganizationalUnitDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface OrganizationalUnitMapper extends EntityMapper<OrganizationalUnitDTO, OrganizationalUnit> {



    default OrganizationalUnit fromId(Long id) {
        if (id == null) {
            return null;
        }
        OrganizationalUnit organizationalUnit = new OrganizationalUnit();
        organizationalUnit.setId(id);
        return organizationalUnit;
    }
}
