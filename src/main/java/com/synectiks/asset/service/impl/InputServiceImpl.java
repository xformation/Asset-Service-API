package com.synectiks.asset.service.impl;

import com.synectiks.asset.service.InputService;
import com.synectiks.asset.domain.Input;
import com.synectiks.asset.repository.InputRepository;
import com.synectiks.asset.service.dto.InputDTO;
import com.synectiks.asset.service.mapper.InputMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link Input}.
 */
@Service
@Transactional
public class InputServiceImpl implements InputService {

    private final Logger log = LoggerFactory.getLogger(InputServiceImpl.class);

    private final InputRepository inputRepository;

    private final InputMapper inputMapper;

    public InputServiceImpl(InputRepository inputRepository, InputMapper inputMapper) {
        this.inputRepository = inputRepository;
        this.inputMapper = inputMapper;
    }

    @Override
    public InputDTO save(InputDTO inputDTO) {
        log.debug("Request to save Input : {}", inputDTO);
        Input input = inputMapper.toEntity(inputDTO);
        input = inputRepository.save(input);
        return inputMapper.toDto(input);
    }

    @Override
    @Transactional(readOnly = true)
    public List<InputDTO> findAll() {
        log.debug("Request to get all Inputs");
        return inputRepository.findAll().stream()
            .map(inputMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<InputDTO> findOne(Long id) {
        log.debug("Request to get Input : {}", id);
        return inputRepository.findById(id)
            .map(inputMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Input : {}", id);
        inputRepository.deleteById(id);
    }
}
