package com.synectiks.asset.service.impl;

import com.synectiks.asset.service.EnvironmentService;
import com.synectiks.asset.domain.Environment;
import com.synectiks.asset.repository.EnvironmentRepository;
import com.synectiks.asset.service.dto.EnvironmentDTO;
import com.synectiks.asset.service.mapper.EnvironmentMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link Environment}.
 */
@Service
@Transactional
public class EnvironmentServiceImpl implements EnvironmentService {

    private final Logger log = LoggerFactory.getLogger(EnvironmentServiceImpl.class);

    private final EnvironmentRepository environmentRepository;

    private final EnvironmentMapper environmentMapper;

    public EnvironmentServiceImpl(EnvironmentRepository environmentRepository, EnvironmentMapper environmentMapper) {
        this.environmentRepository = environmentRepository;
        this.environmentMapper = environmentMapper;
    }

    @Override
    public EnvironmentDTO save(EnvironmentDTO environmentDTO) {
        log.debug("Request to save Environment : {}", environmentDTO);
        Environment environment = environmentMapper.toEntity(environmentDTO);
        environment = environmentRepository.save(environment);
        return environmentMapper.toDto(environment);
    }

    @Override
    @Transactional(readOnly = true)
    public List<EnvironmentDTO> findAll() {
        log.debug("Request to get all Environments");
        return environmentRepository.findAll().stream()
            .map(environmentMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<EnvironmentDTO> findOne(Long id) {
        log.debug("Request to get Environment : {}", id);
        return environmentRepository.findById(id)
            .map(environmentMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Environment : {}", id);
        environmentRepository.deleteById(id);
    }
}
