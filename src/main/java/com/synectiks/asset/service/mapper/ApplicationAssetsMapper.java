package com.synectiks.asset.service.mapper;


import com.synectiks.asset.domain.*;
import com.synectiks.asset.service.dto.ApplicationAssetsDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link ApplicationAssets} and its DTO {@link ApplicationAssetsDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ApplicationAssetsMapper extends EntityMapper<ApplicationAssetsDTO, ApplicationAssets> {



    default ApplicationAssets fromId(Long id) {
        if (id == null) {
            return null;
        }
        ApplicationAssets applicationAssets = new ApplicationAssets();
        applicationAssets.setId(id);
        return applicationAssets;
    }
}
