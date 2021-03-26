package com.synectiks.asset.service.impl;

import com.synectiks.asset.domain.EnvAccount;
import com.synectiks.asset.repository.EnvAccountRepository;
import com.synectiks.asset.service.EnvAccountService;
import com.synectiks.asset.service.dto.EnvAccountDTO;
import com.synectiks.asset.service.mapper.EnvAccountMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link EnvAccount}.
 */
@Service
@Transactional
public class EnvAccountServiceImpl implements EnvAccountService {

  private final Logger log = LoggerFactory.getLogger(EnvAccountServiceImpl.class);

  private final EnvAccountRepository envAccountRepository;

  private final EnvAccountMapper envAccountMapper;

  public EnvAccountServiceImpl(EnvAccountRepository envAccountRepository, EnvAccountMapper envAccountMapper) {
    this.envAccountRepository = envAccountRepository;
    this.envAccountMapper = envAccountMapper;
  }

  @Override
  public EnvAccountDTO save(EnvAccountDTO envAccountDTO) {
    log.debug("Request to save EnvAccount : {}", envAccountDTO);
    EnvAccount envAccount = envAccountMapper.toEntity(envAccountDTO);
    envAccount = envAccountRepository.save(envAccount);
    return envAccountMapper.toDto(envAccount);
  }

  @Override
  public Optional<EnvAccountDTO> partialUpdate(EnvAccountDTO envAccountDTO) {
    log.debug("Request to partially update EnvAccount : {}", envAccountDTO);

    return envAccountRepository
      .findById(envAccountDTO.getId())
      .map(
        existingEnvAccount -> {
          envAccountMapper.partialUpdate(existingEnvAccount, envAccountDTO);
          return existingEnvAccount;
        }
      )
      .map(envAccountRepository::save)
      .map(envAccountMapper::toDto);
  }

  @Override
  @Transactional(readOnly = true)
  public List<EnvAccountDTO> findAll() {
    log.debug("Request to get all EnvAccounts");
    return envAccountRepository.findAll().stream().map(envAccountMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
  }

  @Override
  @Transactional(readOnly = true)
  public Optional<EnvAccountDTO> findOne(Long id) {
    log.debug("Request to get EnvAccount : {}", id);
    return envAccountRepository.findById(id).map(envAccountMapper::toDto);
  }

  @Override
  public void delete(Long id) {
    log.debug("Request to delete EnvAccount : {}", id);
    envAccountRepository.deleteById(id);
  }
}
