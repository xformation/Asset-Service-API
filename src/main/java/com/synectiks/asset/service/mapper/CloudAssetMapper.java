package com.synectiks.asset.service.mapper;


import com.synectiks.asset.domain.*;
import com.synectiks.asset.service.dto.CloudAssetDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link CloudAsset} and its DTO {@link CloudAssetDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CloudAssetMapper extends EntityMapper<CloudAssetDTO, CloudAsset> {



    default CloudAsset fromId(Long id) {
        if (id == null) {
            return null;
        }
        CloudAsset cloudAsset = new CloudAsset();
        cloudAsset.setId(id);
        return cloudAsset;
    }
}
