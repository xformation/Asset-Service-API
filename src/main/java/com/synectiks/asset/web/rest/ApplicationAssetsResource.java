package com.synectiks.asset.web.rest;

import com.synectiks.asset.service.ApplicationAssetsService;
import com.synectiks.asset.web.rest.errors.BadRequestAlertException;
import com.synectiks.asset.service.dto.ApplicationAssetsDTO;

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
 * REST controller for managing {@link com.synectiks.asset.domain.ApplicationAssets}.
 */
@RestController
@RequestMapping("/api")
public class ApplicationAssetsResource {

    private final Logger log = LoggerFactory.getLogger(ApplicationAssetsResource.class);

    private static final String ENTITY_NAME = "assetserviceApplicationAssets";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ApplicationAssetsService applicationAssetsService;

    public ApplicationAssetsResource(ApplicationAssetsService applicationAssetsService) {
        this.applicationAssetsService = applicationAssetsService;
    }

    /**
     * {@code POST  /application-assets} : Create a new applicationAssets.
     *
     * @param applicationAssetsDTO the applicationAssetsDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new applicationAssetsDTO, or with status {@code 400 (Bad Request)} if the applicationAssets has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/application-assets")
    public ResponseEntity<ApplicationAssetsDTO> createApplicationAssets(@RequestBody ApplicationAssetsDTO applicationAssetsDTO) throws URISyntaxException {
        log.debug("REST request to save ApplicationAssets : {}", applicationAssetsDTO);
        if (applicationAssetsDTO.getId() != null) {
            throw new BadRequestAlertException("A new applicationAssets cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ApplicationAssetsDTO result = applicationAssetsService.save(applicationAssetsDTO);
        return ResponseEntity.created(new URI("/api/application-assets/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /application-assets} : Updates an existing applicationAssets.
     *
     * @param applicationAssetsDTO the applicationAssetsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated applicationAssetsDTO,
     * or with status {@code 400 (Bad Request)} if the applicationAssetsDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the applicationAssetsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/application-assets")
    public ResponseEntity<ApplicationAssetsDTO> updateApplicationAssets(@RequestBody ApplicationAssetsDTO applicationAssetsDTO) throws URISyntaxException {
        log.debug("REST request to update ApplicationAssets : {}", applicationAssetsDTO);
        if (applicationAssetsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ApplicationAssetsDTO result = applicationAssetsService.save(applicationAssetsDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, applicationAssetsDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /application-assets} : get all the applicationAssets.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of applicationAssets in body.
     */
    @GetMapping("/application-assets")
    public List<ApplicationAssetsDTO> getAllApplicationAssets() {
        log.debug("REST request to get all ApplicationAssets");
        return applicationAssetsService.findAll();
    }

    /**
     * {@code GET  /application-assets/:id} : get the "id" applicationAssets.
     *
     * @param id the id of the applicationAssetsDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the applicationAssetsDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/application-assets/{id}")
    public ResponseEntity<ApplicationAssetsDTO> getApplicationAssets(@PathVariable Long id) {
        log.debug("REST request to get ApplicationAssets : {}", id);
        Optional<ApplicationAssetsDTO> applicationAssetsDTO = applicationAssetsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(applicationAssetsDTO);
    }

    /**
     * {@code DELETE  /application-assets/:id} : delete the "id" applicationAssets.
     *
     * @param id the id of the applicationAssetsDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/application-assets/{id}")
    public ResponseEntity<Void> deleteApplicationAssets(@PathVariable Long id) {
        log.debug("REST request to delete ApplicationAssets : {}", id);
        applicationAssetsService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
