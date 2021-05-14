package com.synectiks.asset.web.rest;

import com.synectiks.asset.service.CloudAssetService;
import com.synectiks.asset.web.rest.errors.BadRequestAlertException;
import com.synectiks.asset.service.dto.CloudAssetDTO;

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
 * REST controller for managing {@link com.synectiks.asset.domain.CloudAsset}.
 */
@RestController
@RequestMapping("/api")
public class CloudAssetResource {

    private final Logger log = LoggerFactory.getLogger(CloudAssetResource.class);

    private static final String ENTITY_NAME = "assetserviceCloudAsset";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CloudAssetService cloudAssetService;

    public CloudAssetResource(CloudAssetService cloudAssetService) {
        this.cloudAssetService = cloudAssetService;
    }

    /**
     * {@code POST  /cloud-assets} : Create a new cloudAsset.
     *
     * @param cloudAssetDTO the cloudAssetDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new cloudAssetDTO, or with status {@code 400 (Bad Request)} if the cloudAsset has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/cloud-assets")
    public ResponseEntity<CloudAssetDTO> createCloudAsset(@Valid @RequestBody CloudAssetDTO cloudAssetDTO) throws URISyntaxException {
        log.debug("REST request to save CloudAsset : {}", cloudAssetDTO);
        if (cloudAssetDTO.getId() != null) {
            throw new BadRequestAlertException("A new cloudAsset cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CloudAssetDTO result = cloudAssetService.save(cloudAssetDTO);
        return ResponseEntity.created(new URI("/api/cloud-assets/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /cloud-assets} : Updates an existing cloudAsset.
     *
     * @param cloudAssetDTO the cloudAssetDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated cloudAssetDTO,
     * or with status {@code 400 (Bad Request)} if the cloudAssetDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the cloudAssetDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/cloud-assets")
    public ResponseEntity<CloudAssetDTO> updateCloudAsset(@Valid @RequestBody CloudAssetDTO cloudAssetDTO) throws URISyntaxException {
        log.debug("REST request to update CloudAsset : {}", cloudAssetDTO);
        if (cloudAssetDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CloudAssetDTO result = cloudAssetService.save(cloudAssetDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, cloudAssetDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /cloud-assets} : get all the cloudAssets.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of cloudAssets in body.
     */
    @GetMapping("/cloud-assets")
    public List<CloudAssetDTO> getAllCloudAssets() {
        log.debug("REST request to get all CloudAssets");
        return cloudAssetService.findAll();
    }

    /**
     * {@code GET  /cloud-assets/:id} : get the "id" cloudAsset.
     *
     * @param id the id of the cloudAssetDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the cloudAssetDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/cloud-assets/{id}")
    public ResponseEntity<CloudAssetDTO> getCloudAsset(@PathVariable Long id) {
        log.debug("REST request to get CloudAsset : {}", id);
        Optional<CloudAssetDTO> cloudAssetDTO = cloudAssetService.findOne(id);
        return ResponseUtil.wrapOrNotFound(cloudAssetDTO);
    }

    /**
     * {@code DELETE  /cloud-assets/:id} : delete the "id" cloudAsset.
     *
     * @param id the id of the cloudAssetDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/cloud-assets/{id}")
    public ResponseEntity<Void> deleteCloudAsset(@PathVariable Long id) {
        log.debug("REST request to delete CloudAsset : {}", id);
        cloudAssetService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
