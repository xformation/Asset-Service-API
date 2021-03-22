package com.synectiks.asset.web.rest;

import com.synectiks.asset.service.EnvAccountService;
import com.synectiks.asset.web.rest.errors.BadRequestAlertException;
import com.synectiks.asset.service.dto.EnvAccountDTO;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.synectiks.asset.domain.EnvAccount}.
 */
@RestController
@RequestMapping("/api")
public class EnvAccountResource {

    private final Logger log = LoggerFactory.getLogger(EnvAccountResource.class);

    private static final String ENTITY_NAME = "assetserviceEnvAccount";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EnvAccountService envAccountService;

    public EnvAccountResource(EnvAccountService envAccountService) {
        this.envAccountService = envAccountService;
    }

    /**
     * {@code POST  /env-accounts} : Create a new envAccount.
     *
     * @param envAccountDTO the envAccountDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new envAccountDTO, or with status {@code 400 (Bad Request)} if the envAccount has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/env-accounts")
    public ResponseEntity<EnvAccountDTO> createEnvAccount(@Valid @RequestBody EnvAccountDTO envAccountDTO) throws URISyntaxException {
        log.debug("REST request to save EnvAccount : {}", envAccountDTO);
        if (envAccountDTO.getId() != null) {
            throw new BadRequestAlertException("A new envAccount cannot already have an ID", ENTITY_NAME, "idexists");
        }
        EnvAccountDTO result = envAccountService.save(envAccountDTO);
        return ResponseEntity.created(new URI("/api/env-accounts/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /env-accounts} : Updates an existing envAccount.
     *
     * @param envAccountDTO the envAccountDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated envAccountDTO,
     * or with status {@code 400 (Bad Request)} if the envAccountDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the envAccountDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/env-accounts")
    public ResponseEntity<EnvAccountDTO> updateEnvAccount(@Valid @RequestBody EnvAccountDTO envAccountDTO) throws URISyntaxException {
        log.debug("REST request to update EnvAccount : {}", envAccountDTO);
        if (envAccountDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        EnvAccountDTO result = envAccountService.save(envAccountDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, envAccountDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /env-accounts} : get all the envAccounts.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of envAccounts in body.
     */
    @GetMapping("/env-accounts")
    public List<EnvAccountDTO> getAllEnvAccounts() {
        log.debug("REST request to get all EnvAccounts");
        return envAccountService.findAll();
    }

    /**
     * {@code GET  /env-accounts/:id} : get the "id" envAccount.
     *
     * @param id the id of the envAccountDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the envAccountDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/env-accounts/{id}")
    public ResponseEntity<EnvAccountDTO> getEnvAccount(@PathVariable Long id) {
        log.debug("REST request to get EnvAccount : {}", id);
        Optional<EnvAccountDTO> envAccountDTO = envAccountService.findOne(id);
        return ResponseUtil.wrapOrNotFound(envAccountDTO);
    }

    /**
     * {@code DELETE  /env-accounts/:id} : delete the "id" envAccount.
     *
     * @param id the id of the envAccountDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/env-accounts/{id}")
    public ResponseEntity<Void> deleteEnvAccount(@PathVariable Long id) {
        log.debug("REST request to delete EnvAccount : {}", id);
        envAccountService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
