package com.synectiks.asset.web.rest;

import com.synectiks.asset.service.OuEnvAcMappingService;
import com.synectiks.asset.web.rest.errors.BadRequestAlertException;
import com.synectiks.asset.service.dto.OuEnvAcMappingDTO;

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
 * REST controller for managing {@link com.synectiks.asset.domain.OuEnvAcMapping}.
 */
@RestController
@RequestMapping("/api")
public class OuEnvAcMappingResource {

    private final Logger log = LoggerFactory.getLogger(OuEnvAcMappingResource.class);

    private static final String ENTITY_NAME = "assetserviceOuEnvAcMapping";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final OuEnvAcMappingService ouEnvAcMappingService;

    public OuEnvAcMappingResource(OuEnvAcMappingService ouEnvAcMappingService) {
        this.ouEnvAcMappingService = ouEnvAcMappingService;
    }

    /**
     * {@code POST  /ou-env-ac-mappings} : Create a new ouEnvAcMapping.
     *
     * @param ouEnvAcMappingDTO the ouEnvAcMappingDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new ouEnvAcMappingDTO, or with status {@code 400 (Bad Request)} if the ouEnvAcMapping has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/ou-env-ac-mappings")
    public ResponseEntity<OuEnvAcMappingDTO> createOuEnvAcMapping(@RequestBody OuEnvAcMappingDTO ouEnvAcMappingDTO) throws URISyntaxException {
        log.debug("REST request to save OuEnvAcMapping : {}", ouEnvAcMappingDTO);
        if (ouEnvAcMappingDTO.getId() != null) {
            throw new BadRequestAlertException("A new ouEnvAcMapping cannot already have an ID", ENTITY_NAME, "idexists");
        }
        OuEnvAcMappingDTO result = ouEnvAcMappingService.save(ouEnvAcMappingDTO);
        return ResponseEntity.created(new URI("/api/ou-env-ac-mappings/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /ou-env-ac-mappings} : Updates an existing ouEnvAcMapping.
     *
     * @param ouEnvAcMappingDTO the ouEnvAcMappingDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated ouEnvAcMappingDTO,
     * or with status {@code 400 (Bad Request)} if the ouEnvAcMappingDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the ouEnvAcMappingDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/ou-env-ac-mappings")
    public ResponseEntity<OuEnvAcMappingDTO> updateOuEnvAcMapping(@RequestBody OuEnvAcMappingDTO ouEnvAcMappingDTO) throws URISyntaxException {
        log.debug("REST request to update OuEnvAcMapping : {}", ouEnvAcMappingDTO);
        if (ouEnvAcMappingDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        OuEnvAcMappingDTO result = ouEnvAcMappingService.save(ouEnvAcMappingDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, ouEnvAcMappingDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /ou-env-ac-mappings} : get all the ouEnvAcMappings.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of ouEnvAcMappings in body.
     */
    @GetMapping("/ou-env-ac-mappings")
    public List<OuEnvAcMappingDTO> getAllOuEnvAcMappings() {
        log.debug("REST request to get all OuEnvAcMappings");
        return ouEnvAcMappingService.findAll();
    }

    /**
     * {@code GET  /ou-env-ac-mappings/:id} : get the "id" ouEnvAcMapping.
     *
     * @param id the id of the ouEnvAcMappingDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the ouEnvAcMappingDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/ou-env-ac-mappings/{id}")
    public ResponseEntity<OuEnvAcMappingDTO> getOuEnvAcMapping(@PathVariable Long id) {
        log.debug("REST request to get OuEnvAcMapping : {}", id);
        Optional<OuEnvAcMappingDTO> ouEnvAcMappingDTO = ouEnvAcMappingService.findOne(id);
        return ResponseUtil.wrapOrNotFound(ouEnvAcMappingDTO);
    }

    /**
     * {@code DELETE  /ou-env-ac-mappings/:id} : delete the "id" ouEnvAcMapping.
     *
     * @param id the id of the ouEnvAcMappingDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/ou-env-ac-mappings/{id}")
    public ResponseEntity<Void> deleteOuEnvAcMapping(@PathVariable Long id) {
        log.debug("REST request to delete OuEnvAcMapping : {}", id);
        ouEnvAcMappingService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
