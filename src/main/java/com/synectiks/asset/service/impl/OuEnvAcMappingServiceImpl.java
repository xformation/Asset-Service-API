package com.synectiks.asset.service.impl;

import com.synectiks.asset.service.OuEnvAcMappingService;
import com.synectiks.asset.domain.OuEnvAcMapping;
import com.synectiks.asset.repository.OuEnvAcMappingRepository;
import com.synectiks.asset.service.dto.OuEnvAcMappingDTO;
import com.synectiks.asset.service.mapper.OuEnvAcMappingMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link OuEnvAcMapping}.
 */
@Service
@Transactional
public class OuEnvAcMappingServiceImpl implements OuEnvAcMappingService {

    private final Logger log = LoggerFactory.getLogger(OuEnvAcMappingServiceImpl.class);

    private final OuEnvAcMappingRepository ouEnvAcMappingRepository;

    private final OuEnvAcMappingMapper ouEnvAcMappingMapper;

    public OuEnvAcMappingServiceImpl(OuEnvAcMappingRepository ouEnvAcMappingRepository, OuEnvAcMappingMapper ouEnvAcMappingMapper) {
        this.ouEnvAcMappingRepository = ouEnvAcMappingRepository;
        this.ouEnvAcMappingMapper = ouEnvAcMappingMapper;
    }

    @Override
    public OuEnvAcMappingDTO save(OuEnvAcMappingDTO ouEnvAcMappingDTO) {
        log.debug("Request to save OuEnvAcMapping : {}", ouEnvAcMappingDTO);
        OuEnvAcMapping ouEnvAcMapping = ouEnvAcMappingMapper.toEntity(ouEnvAcMappingDTO);
        ouEnvAcMapping = ouEnvAcMappingRepository.save(ouEnvAcMapping);
        return ouEnvAcMappingMapper.toDto(ouEnvAcMapping);
    }

    @Override
    @Transactional(readOnly = true)
    public List<OuEnvAcMappingDTO> findAll() {
        log.debug("Request to get all OuEnvAcMappings");
        return ouEnvAcMappingRepository.findAll().stream()
            .map(ouEnvAcMappingMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<OuEnvAcMappingDTO> findOne(Long id) {
        log.debug("Request to get OuEnvAcMapping : {}", id);
        return ouEnvAcMappingRepository.findById(id)
            .map(ouEnvAcMappingMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete OuEnvAcMapping : {}", id);
        ouEnvAcMappingRepository.deleteById(id);
    }
}
