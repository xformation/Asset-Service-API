package com.synectiks.asset.service.impl;

import com.synectiks.asset.service.OuEnvMappingService;
import com.synectiks.asset.domain.OuEnvMapping;
import com.synectiks.asset.repository.OuEnvMappingRepository;
import com.synectiks.asset.service.dto.OuEnvMappingDTO;
import com.synectiks.asset.service.mapper.OuEnvMappingMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link OuEnvMapping}.
 */
@Service
@Transactional
public class OuEnvMappingServiceImpl implements OuEnvMappingService {

    private final Logger log = LoggerFactory.getLogger(OuEnvMappingServiceImpl.class);

    private final OuEnvMappingRepository ouEnvMappingRepository;

    private final OuEnvMappingMapper ouEnvMappingMapper;

    public OuEnvMappingServiceImpl(OuEnvMappingRepository ouEnvMappingRepository, OuEnvMappingMapper ouEnvMappingMapper) {
        this.ouEnvMappingRepository = ouEnvMappingRepository;
        this.ouEnvMappingMapper = ouEnvMappingMapper;
    }

    @Override
    public OuEnvMappingDTO save(OuEnvMappingDTO ouEnvMappingDTO) {
        log.debug("Request to save OuEnvMapping : {}", ouEnvMappingDTO);
        OuEnvMapping ouEnvMapping = ouEnvMappingMapper.toEntity(ouEnvMappingDTO);
        ouEnvMapping = ouEnvMappingRepository.save(ouEnvMapping);
        return ouEnvMappingMapper.toDto(ouEnvMapping);
    }

    @Override
    @Transactional(readOnly = true)
    public List<OuEnvMappingDTO> findAll() {
        log.debug("Request to get all OuEnvMappings");
        return ouEnvMappingRepository.findAll().stream()
            .map(ouEnvMappingMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<OuEnvMappingDTO> findOne(Long id) {
        log.debug("Request to get OuEnvMapping : {}", id);
        return ouEnvMappingRepository.findById(id)
            .map(ouEnvMappingMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete OuEnvMapping : {}", id);
        ouEnvMappingRepository.deleteById(id);
    }
}
