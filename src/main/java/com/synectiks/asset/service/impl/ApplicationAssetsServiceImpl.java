package com.synectiks.asset.service.impl;

import com.synectiks.asset.service.ApplicationAssetsService;
import com.synectiks.asset.domain.ApplicationAssets;
import com.synectiks.asset.repository.ApplicationAssetsRepository;
import com.synectiks.asset.service.dto.ApplicationAssetsDTO;
import com.synectiks.asset.service.mapper.ApplicationAssetsMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link ApplicationAssets}.
 */
@Service
@Transactional
public class ApplicationAssetsServiceImpl implements ApplicationAssetsService {

    private final Logger log = LoggerFactory.getLogger(ApplicationAssetsServiceImpl.class);

    private final ApplicationAssetsRepository applicationAssetsRepository;

    private final ApplicationAssetsMapper applicationAssetsMapper;

    public ApplicationAssetsServiceImpl(ApplicationAssetsRepository applicationAssetsRepository, ApplicationAssetsMapper applicationAssetsMapper) {
        this.applicationAssetsRepository = applicationAssetsRepository;
        this.applicationAssetsMapper = applicationAssetsMapper;
    }

    @Override
    public ApplicationAssetsDTO save(ApplicationAssetsDTO applicationAssetsDTO) {
        log.debug("Request to save ApplicationAssets : {}", applicationAssetsDTO);
        ApplicationAssets applicationAssets = applicationAssetsMapper.toEntity(applicationAssetsDTO);
        applicationAssets = applicationAssetsRepository.save(applicationAssets);
        return applicationAssetsMapper.toDto(applicationAssets);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ApplicationAssetsDTO> findAll() {
        log.debug("Request to get all ApplicationAssets");
        return applicationAssetsRepository.findAll().stream()
            .map(applicationAssetsMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<ApplicationAssetsDTO> findOne(Long id) {
        log.debug("Request to get ApplicationAssets : {}", id);
        return applicationAssetsRepository.findById(id)
            .map(applicationAssetsMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete ApplicationAssets : {}", id);
        applicationAssetsRepository.deleteById(id);
    }
}
