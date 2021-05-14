package com.synectiks.asset.service.impl;

import com.synectiks.asset.service.AssetDetailsService;
import com.synectiks.asset.domain.AssetDetails;
import com.synectiks.asset.repository.AssetDetailsRepository;
import com.synectiks.asset.service.dto.AssetDetailsDTO;
import com.synectiks.asset.service.mapper.AssetDetailsMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link AssetDetails}.
 */
@Service
@Transactional
public class AssetDetailsServiceImpl implements AssetDetailsService {

    private final Logger log = LoggerFactory.getLogger(AssetDetailsServiceImpl.class);

    private final AssetDetailsRepository assetDetailsRepository;

    private final AssetDetailsMapper assetDetailsMapper;

    public AssetDetailsServiceImpl(AssetDetailsRepository assetDetailsRepository, AssetDetailsMapper assetDetailsMapper) {
        this.assetDetailsRepository = assetDetailsRepository;
        this.assetDetailsMapper = assetDetailsMapper;
    }

    @Override
    public AssetDetailsDTO save(AssetDetailsDTO assetDetailsDTO) {
        log.debug("Request to save AssetDetails : {}", assetDetailsDTO);
        AssetDetails assetDetails = assetDetailsMapper.toEntity(assetDetailsDTO);
        assetDetails = assetDetailsRepository.save(assetDetails);
        return assetDetailsMapper.toDto(assetDetails);
    }

    @Override
    @Transactional(readOnly = true)
    public List<AssetDetailsDTO> findAll() {
        log.debug("Request to get all AssetDetails");
        return assetDetailsRepository.findAll().stream()
            .map(assetDetailsMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<AssetDetailsDTO> findOne(Long id) {
        log.debug("Request to get AssetDetails : {}", id);
        return assetDetailsRepository.findById(id)
            .map(assetDetailsMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete AssetDetails : {}", id);
        assetDetailsRepository.deleteById(id);
    }
}
