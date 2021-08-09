package com.synectiks.asset.service.impl;

import com.synectiks.asset.service.InputConfigService;
import com.synectiks.asset.domain.InputConfig;
import com.synectiks.asset.repository.InputConfigRepository;
import com.synectiks.asset.service.dto.InputConfigDTO;
import com.synectiks.asset.service.mapper.InputConfigMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link InputConfig}.
 */
@Service
@Transactional
public class InputConfigServiceImpl implements InputConfigService {

    private final Logger log = LoggerFactory.getLogger(InputConfigServiceImpl.class);

    private final InputConfigRepository inputConfigRepository;

    private final InputConfigMapper inputConfigMapper;

    public InputConfigServiceImpl(InputConfigRepository inputConfigRepository, InputConfigMapper inputConfigMapper) {
        this.inputConfigRepository = inputConfigRepository;
        this.inputConfigMapper = inputConfigMapper;
    }

    @Override
    public InputConfigDTO save(InputConfigDTO inputConfigDTO) {
        log.debug("Request to save InputConfig : {}", inputConfigDTO);
        InputConfig inputConfig = inputConfigMapper.toEntity(inputConfigDTO);
        inputConfig = inputConfigRepository.save(inputConfig);
        return inputConfigMapper.toDto(inputConfig);
    }

    @Override
    @Transactional(readOnly = true)
    public List<InputConfigDTO> findAll() {
        log.debug("Request to get all InputConfigs");
        return inputConfigRepository.findAll().stream()
            .map(inputConfigMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<InputConfigDTO> findOne(Long id) {
        log.debug("Request to get InputConfig : {}", id);
        return inputConfigRepository.findById(id)
            .map(inputConfigMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete InputConfig : {}", id);
        inputConfigRepository.deleteById(id);
    }
}
