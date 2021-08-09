package com.synectiks.asset.web.rest;

import com.synectiks.asset.service.InputConfigService;
import com.synectiks.asset.web.rest.errors.BadRequestAlertException;
import com.synectiks.asset.service.dto.InputConfigDTO;

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
 * REST controller for managing {@link com.synectiks.asset.domain.InputConfig}.
 */
@RestController
@RequestMapping("/api")
public class InputConfigResource {

    private final Logger log = LoggerFactory.getLogger(InputConfigResource.class);

    private static final String ENTITY_NAME = "assetserviceInputConfig";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final InputConfigService inputConfigService;

    public InputConfigResource(InputConfigService inputConfigService) {
        this.inputConfigService = inputConfigService;
    }

    /**
     * {@code POST  /input-configs} : Create a new inputConfig.
     *
     * @param inputConfigDTO the inputConfigDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new inputConfigDTO, or with status {@code 400 (Bad Request)} if the inputConfig has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/input-configs")
    public ResponseEntity<InputConfigDTO> createInputConfig(@RequestBody InputConfigDTO inputConfigDTO) throws URISyntaxException {
        log.debug("REST request to save InputConfig : {}", inputConfigDTO);
        if (inputConfigDTO.getId() != null) {
            throw new BadRequestAlertException("A new inputConfig cannot already have an ID", ENTITY_NAME, "idexists");
        }
        InputConfigDTO result = inputConfigService.save(inputConfigDTO);
        return ResponseEntity.created(new URI("/api/input-configs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /input-configs} : Updates an existing inputConfig.
     *
     * @param inputConfigDTO the inputConfigDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated inputConfigDTO,
     * or with status {@code 400 (Bad Request)} if the inputConfigDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the inputConfigDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/input-configs")
    public ResponseEntity<InputConfigDTO> updateInputConfig(@RequestBody InputConfigDTO inputConfigDTO) throws URISyntaxException {
        log.debug("REST request to update InputConfig : {}", inputConfigDTO);
        if (inputConfigDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        InputConfigDTO result = inputConfigService.save(inputConfigDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, inputConfigDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /input-configs} : get all the inputConfigs.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of inputConfigs in body.
     */
    @GetMapping("/input-configs")
    public List<InputConfigDTO> getAllInputConfigs() {
        log.debug("REST request to get all InputConfigs");
        return inputConfigService.findAll();
    }

    /**
     * {@code GET  /input-configs/:id} : get the "id" inputConfig.
     *
     * @param id the id of the inputConfigDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the inputConfigDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/input-configs/{id}")
    public ResponseEntity<InputConfigDTO> getInputConfig(@PathVariable Long id) {
        log.debug("REST request to get InputConfig : {}", id);
        Optional<InputConfigDTO> inputConfigDTO = inputConfigService.findOne(id);
        return ResponseUtil.wrapOrNotFound(inputConfigDTO);
    }

    /**
     * {@code DELETE  /input-configs/:id} : delete the "id" inputConfig.
     *
     * @param id the id of the inputConfigDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/input-configs/{id}")
    public ResponseEntity<Void> deleteInputConfig(@PathVariable Long id) {
        log.debug("REST request to delete InputConfig : {}", id);
        inputConfigService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
