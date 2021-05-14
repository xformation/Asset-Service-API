package com.synectiks.asset.service.mapper;


import com.synectiks.asset.domain.*;
import com.synectiks.asset.service.dto.AssetDetailsDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link AssetDetails} and its DTO {@link AssetDetailsDTO}.
 */
@Mapper(componentModel = "spring", uses = {CloudAssetMapper.class})
public interface AssetDetailsMapper extends EntityMapper<AssetDetailsDTO, AssetDetails> {

    @Mapping(source = "cloudAsset.id", target = "cloudAssetId")
    AssetDetailsDTO toDto(AssetDetails assetDetails);

    @Mapping(source = "cloudAssetId", target = "cloudAsset")
    AssetDetails toEntity(AssetDetailsDTO assetDetailsDTO);

    default AssetDetails fromId(Long id) {
        if (id == null) {
            return null;
        }
        AssetDetails assetDetails = new AssetDetails();
        assetDetails.setId(id);
        return assetDetails;
    }
}
