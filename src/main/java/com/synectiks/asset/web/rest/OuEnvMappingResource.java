package com.synectiks.asset.web.rest;

import com.synectiks.asset.service.OuEnvMappingService;
import com.synectiks.asset.web.rest.errors.BadRequestAlertException;
import com.synectiks.asset.service.dto.OuEnvMappingDTO;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.synectiks.asset.domain.OuEnvMapping}.
 */
@RestController
@RequestMapping("/api")
public class OuEnvMappingResource {

    private final Logger log = LoggerFactory.getLogger(OuEnvMappingResource.class);

    private static final String ENTITY_NAME = "assetserviceOuEnvMapping";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final OuEnvMappingService ouEnvMappingService;

    public OuEnvMappingResource(OuEnvMappingService ouEnvMappingService) {
        this.ouEnvMappingService = ouEnvMappingService;
    }

    /**
     * {@code POST  /ou-env-mappings} : Create a new ouEnvMapping.
     *
     * @param ouEnvMappingDTO the ouEnvMappingDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new ouEnvMappingDTO, or with status {@code 400 (Bad Request)} if the ouEnvMapping has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/ou-env-mappings")
    public ResponseEntity<OuEnvMappingDTO> createOuEnvMapping(@RequestBody OuEnvMappingDTO ouEnvMappingDTO) throws URISyntaxException {
        log.debug("REST request to save OuEnvMapping : {}", ouEnvMappingDTO);
        if (ouEnvMappingDTO.getId() != null) {
            throw new BadRequestAlertException("A new ouEnvMapping cannot already have an ID", ENTITY_NAME, "idexists");
        }
        OuEnvMappingDTO result = ouEnvMappingService.save(ouEnvMappingDTO);
        return ResponseEntity.created(new URI("/api/ou-env-mappings/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /ou-env-mappings} : Updates an existing ouEnvMapping.
     *
     * @param ouEnvMappingDTO the ouEnvMappingDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated ouEnvMappingDTO,
     * or with status {@code 400 (Bad Request)} if the ouEnvMappingDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the ouEnvMappingDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/ou-env-mappings")
    public ResponseEntity<OuEnvMappingDTO> updateOuEnvMapping(@RequestBody OuEnvMappingDTO ouEnvMappingDTO) throws URISyntaxException {
        log.debug("REST request to update OuEnvMapping : {}", ouEnvMappingDTO);
        if (ouEnvMappingDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        OuEnvMappingDTO result = ouEnvMappingService.save(ouEnvMappingDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, ouEnvMappingDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /ou-env-mappings} : get all the ouEnvMappings.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of ouEnvMappings in body.
     */
    @GetMapping("/ou-env-mappings")
    public List<OuEnvMappingDTO> getAllOuEnvMappings() {
        log.debug("REST request to get all OuEnvMappings");
        return ouEnvMappingService.findAll();
    }

    /**
     * {@code GET  /ou-env-mappings/:id} : get the "id" ouEnvMapping.
     *
     * @param id the id of the ouEnvMappingDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the ouEnvMappingDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/ou-env-mappings/{id}")
    public ResponseEntity<OuEnvMappingDTO> getOuEnvMapping(@PathVariable Long id) {
        log.debug("REST request to get OuEnvMapping : {}", id);
        Optional<OuEnvMappingDTO> ouEnvMappingDTO = ouEnvMappingService.findOne(id);
        return ResponseUtil.wrapOrNotFound(ouEnvMappingDTO);
    }

    /**
     * {@code DELETE  /ou-env-mappings/:id} : delete the "id" ouEnvMapping.
     *
     * @param id the id of the ouEnvMappingDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/ou-env-mappings/{id}")
    public ResponseEntity<Void> deleteOuEnvMapping(@PathVariable Long id) {
        log.debug("REST request to delete OuEnvMapping : {}", id);
        ouEnvMappingService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
