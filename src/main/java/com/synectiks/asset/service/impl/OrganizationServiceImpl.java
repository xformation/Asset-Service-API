package com.synectiks.asset.service.impl;

import com.synectiks.asset.service.OrganizationService;
import com.synectiks.asset.domain.Organization;
import com.synectiks.asset.repository.OrganizationRepository;
import com.synectiks.asset.service.dto.OrganizationDTO;
import com.synectiks.asset.service.mapper.OrganizationMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link Organization}.
 */
@Service
@Transactional
public class OrganizationServiceImpl implements OrganizationService {

    private final Logger log = LoggerFactory.getLogger(OrganizationServiceImpl.class);

    private final OrganizationRepository organizationRepository;

    private final OrganizationMapper organizationMapper;

    public OrganizationServiceImpl(OrganizationRepository organizationRepository, OrganizationMapper organizationMapper) {
        this.organizationRepository = organizationRepository;
        this.organizationMapper = organizationMapper;
    }

    @Override
    public OrganizationDTO save(OrganizationDTO organizationDTO) {
        log.debug("Request to save Organization : {}", organizationDTO);
        Organization organization = organizationMapper.toEntity(organizationDTO);
        organization = organizationRepository.save(organization);
        return organizationMapper.toDto(organization);
    }

    @Override
    @Transactional(readOnly = true)
    public List<OrganizationDTO> findAll() {
        log.debug("Request to get all Organizations");
        return organizationRepository.findAll().stream()
            .map(organizationMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<OrganizationDTO> findOne(Long id) {
        log.debug("Request to get Organization : {}", id);
        return organizationRepository.findById(id)
            .map(organizationMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Organization : {}", id);
        organizationRepository.deleteById(id);
    }
}
