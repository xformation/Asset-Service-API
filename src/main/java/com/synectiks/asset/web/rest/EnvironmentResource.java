package com.synectiks.asset.web.rest;

import com.synectiks.asset.service.EnvironmentService;
import com.synectiks.asset.web.rest.errors.BadRequestAlertException;
import com.synectiks.asset.service.dto.EnvironmentDTO;

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
 * REST controller for managing {@link com.synectiks.asset.domain.Environment}.
 */
@RestController
@RequestMapping("/api")
public class EnvironmentResource {

    private final Logger log = LoggerFactory.getLogger(EnvironmentResource.class);

    private static final String ENTITY_NAME = "assetserviceEnvironment";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EnvironmentService environmentService;

    public EnvironmentResource(EnvironmentService environmentService) {
        this.environmentService = environmentService;
    }

    /**
     * {@code POST  /environments} : Create a new environment.
     *
     * @param environmentDTO the environmentDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new environmentDTO, or with status {@code 400 (Bad Request)} if the environment has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/environments")
    public ResponseEntity<EnvironmentDTO> createEnvironment(@Valid @RequestBody EnvironmentDTO environmentDTO) throws URISyntaxException {
        log.debug("REST request to save Environment : {}", environmentDTO);
        if (environmentDTO.getId() != null) {
            throw new BadRequestAlertException("A new environment cannot already have an ID", ENTITY_NAME, "idexists");
        }
        EnvironmentDTO result = environmentService.save(environmentDTO);
        return ResponseEntity.created(new URI("/api/environments/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /environments} : Updates an existing environment.
     *
     * @param environmentDTO the environmentDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated environmentDTO,
     * or with status {@code 400 (Bad Request)} if the environmentDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the environmentDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/environments")
    public ResponseEntity<EnvironmentDTO> updateEnvironment(@Valid @RequestBody EnvironmentDTO environmentDTO) throws URISyntaxException {
        log.debug("REST request to update Environment : {}", environmentDTO);
        if (environmentDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        EnvironmentDTO result = environmentService.save(environmentDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, environmentDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /environments} : get all the environments.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of environments in body.
     */
    @GetMapping("/environments")
    public List<EnvironmentDTO> getAllEnvironments() {
        log.debug("REST request to get all Environments");
        return environmentService.findAll();
    }

    /**
     * {@code GET  /environments/:id} : get the "id" environment.
     *
     * @param id the id of the environmentDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the environmentDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/environments/{id}")
    public ResponseEntity<EnvironmentDTO> getEnvironment(@PathVariable Long id) {
        log.debug("REST request to get Environment : {}", id);
        Optional<EnvironmentDTO> environmentDTO = environmentService.findOne(id);
        return ResponseUtil.wrapOrNotFound(environmentDTO);
    }

    /**
     * {@code DELETE  /environments/:id} : delete the "id" environment.
     *
     * @param id the id of the environmentDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/environments/{id}")
    public ResponseEntity<Void> deleteEnvironment(@PathVariable Long id) {
        log.debug("REST request to delete Environment : {}", id);
        environmentService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
