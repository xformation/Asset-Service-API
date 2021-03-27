package com.synectiks.asset.service.impl;

import com.synectiks.asset.service.OrganizationalUnitService;
import com.synectiks.asset.domain.OrganizationalUnit;
import com.synectiks.asset.repository.OrganizationalUnitRepository;
import com.synectiks.asset.service.dto.OrganizationalUnitDTO;
import com.synectiks.asset.service.mapper.OrganizationalUnitMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link OrganizationalUnit}.
 */
@Service
@Transactional
public class OrganizationalUnitServiceImpl implements OrganizationalUnitService {

    private final Logger log = LoggerFactory.getLogger(OrganizationalUnitServiceImpl.class);

    private final OrganizationalUnitRepository organizationalUnitRepository;

    private final OrganizationalUnitMapper organizationalUnitMapper;

    public OrganizationalUnitServiceImpl(OrganizationalUnitRepository organizationalUnitRepository, OrganizationalUnitMapper organizationalUnitMapper) {
        this.organizationalUnitRepository = organizationalUnitRepository;
        this.organizationalUnitMapper = organizationalUnitMapper;
    }

    @Override
    public OrganizationalUnitDTO save(OrganizationalUnitDTO organizationalUnitDTO) {
        log.debug("Request to save OrganizationalUnit : {}", organizationalUnitDTO);
        OrganizationalUnit organizationalUnit = organizationalUnitMapper.toEntity(organizationalUnitDTO);
        organizationalUnit = organizationalUnitRepository.save(organizationalUnit);
        return organizationalUnitMapper.toDto(organizationalUnit);
    }

    @Override
    @Transactional(readOnly = true)
    public List<OrganizationalUnitDTO> findAll() {
        log.debug("Request to get all OrganizationalUnits");
        return organizationalUnitRepository.findAll().stream()
            .map(organizationalUnitMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<OrganizationalUnitDTO> findOne(Long id) {
        log.debug("Request to get OrganizationalUnit : {}", id);
        return organizationalUnitRepository.findById(id)
            .map(organizationalUnitMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete OrganizationalUnit : {}", id);
        organizationalUnitRepository.deleteById(id);
    }
}
