package com.synectiks.asset.service.impl;

import com.synectiks.asset.service.InputsService;
import com.synectiks.asset.domain.Inputs;
import com.synectiks.asset.repository.InputsRepository;
import com.synectiks.asset.service.dto.InputsDTO;
import com.synectiks.asset.service.mapper.InputsMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link Inputs}.
 */
@Service
@Transactional
public class InputsServiceImpl implements InputsService {

    private final Logger log = LoggerFactory.getLogger(InputsServiceImpl.class);

    private final InputsRepository inputsRepository;

    private final InputsMapper inputsMapper;

    public InputsServiceImpl(InputsRepository inputsRepository, InputsMapper inputsMapper) {
        this.inputsRepository = inputsRepository;
        this.inputsMapper = inputsMapper;
    }

    @Override
    public InputsDTO save(InputsDTO inputsDTO) {
        log.debug("Request to save Inputs : {}", inputsDTO);
        Inputs inputs = inputsMapper.toEntity(inputsDTO);
        inputs = inputsRepository.save(inputs);
        return inputsMapper.toDto(inputs);
    }

    @Override
    @Transactional(readOnly = true)
    public List<InputsDTO> findAll() {
        log.debug("Request to get all Inputs");
        return inputsRepository.findAll().stream()
            .map(inputsMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<InputsDTO> findOne(Long id) {
        log.debug("Request to get Inputs : {}", id);
        return inputsRepository.findById(id)
            .map(inputsMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Inputs : {}", id);
        inputsRepository.deleteById(id);
    }
}
