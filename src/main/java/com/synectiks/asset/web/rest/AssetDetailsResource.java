package com.synectiks.asset.web.rest;

import com.synectiks.asset.service.AssetDetailsService;
import com.synectiks.asset.web.rest.errors.BadRequestAlertException;
import com.synectiks.asset.service.dto.AssetDetailsDTO;

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
 * REST controller for managing {@link com.synectiks.asset.domain.AssetDetails}.
 */
@RestController
@RequestMapping("/api")
public class AssetDetailsResource {

    private final Logger log = LoggerFactory.getLogger(AssetDetailsResource.class);

    private static final String ENTITY_NAME = "assetserviceAssetDetails";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AssetDetailsService assetDetailsService;

    public AssetDetailsResource(AssetDetailsService assetDetailsService) {
        this.assetDetailsService = assetDetailsService;
    }

    /**
     * {@code POST  /asset-details} : Create a new assetDetails.
     *
     * @param assetDetailsDTO the assetDetailsDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new assetDetailsDTO, or with status {@code 400 (Bad Request)} if the assetDetails has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/asset-details")
    public ResponseEntity<AssetDetailsDTO> createAssetDetails(@Valid @RequestBody AssetDetailsDTO assetDetailsDTO) throws URISyntaxException {
        log.debug("REST request to save AssetDetails : {}", assetDetailsDTO);
        if (assetDetailsDTO.getId() != null) {
            throw new BadRequestAlertException("A new assetDetails cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AssetDetailsDTO result = assetDetailsService.save(assetDetailsDTO);
        return ResponseEntity.created(new URI("/api/asset-details/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /asset-details} : Updates an existing assetDetails.
     *
     * @param assetDetailsDTO the assetDetailsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated assetDetailsDTO,
     * or with status {@code 400 (Bad Request)} if the assetDetailsDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the assetDetailsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/asset-details")
    public ResponseEntity<AssetDetailsDTO> updateAssetDetails(@Valid @RequestBody AssetDetailsDTO assetDetailsDTO) throws URISyntaxException {
        log.debug("REST request to update AssetDetails : {}", assetDetailsDTO);
        if (assetDetailsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        AssetDetailsDTO result = assetDetailsService.save(assetDetailsDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, assetDetailsDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /asset-details} : get all the assetDetails.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of assetDetails in body.
     */
    @GetMapping("/asset-details")
    public List<AssetDetailsDTO> getAllAssetDetails() {
        log.debug("REST request to get all AssetDetails");
        return assetDetailsService.findAll();
    }

    /**
     * {@code GET  /asset-details/:id} : get the "id" assetDetails.
     *
     * @param id the id of the assetDetailsDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the assetDetailsDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/asset-details/{id}")
    public ResponseEntity<AssetDetailsDTO> getAssetDetails(@PathVariable Long id) {
        log.debug("REST request to get AssetDetails : {}", id);
        Optional<AssetDetailsDTO> assetDetailsDTO = assetDetailsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(assetDetailsDTO);
    }

    /**
     * {@code DELETE  /asset-details/:id} : delete the "id" assetDetails.
     *
     * @param id the id of the assetDetailsDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/asset-details/{id}")
    public ResponseEntity<Void> deleteAssetDetails(@PathVariable Long id) {
        log.debug("REST request to delete AssetDetails : {}", id);
        assetDetailsService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
