package com.synectiks.asset.service.impl;

import com.synectiks.asset.service.CloudAssetService;
import com.synectiks.asset.domain.CloudAsset;
import com.synectiks.asset.repository.CloudAssetRepository;
import com.synectiks.asset.service.dto.CloudAssetDTO;
import com.synectiks.asset.service.mapper.CloudAssetMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link CloudAsset}.
 */
@Service
@Transactional
public class CloudAssetServiceImpl implements CloudAssetService {

    private final Logger log = LoggerFactory.getLogger(CloudAssetServiceImpl.class);

    private final CloudAssetRepository cloudAssetRepository;

    private final CloudAssetMapper cloudAssetMapper;

    public CloudAssetServiceImpl(CloudAssetRepository cloudAssetRepository, CloudAssetMapper cloudAssetMapper) {
        this.cloudAssetRepository = cloudAssetRepository;
        this.cloudAssetMapper = cloudAssetMapper;
    }

    @Override
    public CloudAssetDTO save(CloudAssetDTO cloudAssetDTO) {
        log.debug("Request to save CloudAsset : {}", cloudAssetDTO);
        CloudAsset cloudAsset = cloudAssetMapper.toEntity(cloudAssetDTO);
        cloudAsset = cloudAssetRepository.save(cloudAsset);
        return cloudAssetMapper.toDto(cloudAsset);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CloudAssetDTO> findAll() {
        log.debug("Request to get all CloudAssets");
        return cloudAssetRepository.findAll().stream()
            .map(cloudAssetMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<CloudAssetDTO> findOne(Long id) {
        log.debug("Request to get CloudAsset : {}", id);
        return cloudAssetRepository.findById(id)
            .map(cloudAssetMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete CloudAsset : {}", id);
        cloudAssetRepository.deleteById(id);
    }
}
