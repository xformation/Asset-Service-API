package com.synectiks.asset.web.rest;

import com.synectiks.asset.service.InputsService;
import com.synectiks.asset.web.rest.errors.BadRequestAlertException;
import com.synectiks.asset.service.dto.InputsDTO;

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
 * REST controller for managing {@link com.synectiks.asset.domain.Inputs}.
 */
@RestController
@RequestMapping("/api")
public class InputsResource {

    private final Logger log = LoggerFactory.getLogger(InputsResource.class);

    private static final String ENTITY_NAME = "assetserviceInputs";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final InputsService inputsService;

    public InputsResource(InputsService inputsService) {
        this.inputsService = inputsService;
    }

    /**
     * {@code POST  /inputs} : Create a new inputs.
     *
     * @param inputsDTO the inputsDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new inputsDTO, or with status {@code 400 (Bad Request)} if the inputs has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/inputs")
    public ResponseEntity<InputsDTO> createInputs(@Valid @RequestBody InputsDTO inputsDTO) throws URISyntaxException {
        log.debug("REST request to save Inputs : {}", inputsDTO);
        if (inputsDTO.getId() != null) {
            throw new BadRequestAlertException("A new inputs cannot already have an ID", ENTITY_NAME, "idexists");
        }
        InputsDTO result = inputsService.save(inputsDTO);
        return ResponseEntity.created(new URI("/api/inputs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /inputs} : Updates an existing inputs.
     *
     * @param inputsDTO the inputsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated inputsDTO,
     * or with status {@code 400 (Bad Request)} if the inputsDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the inputsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/inputs")
    public ResponseEntity<InputsDTO> updateInputs(@Valid @RequestBody InputsDTO inputsDTO) throws URISyntaxException {
        log.debug("REST request to update Inputs : {}", inputsDTO);
        if (inputsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        InputsDTO result = inputsService.save(inputsDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, inputsDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /inputs} : get all the inputs.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of inputs in body.
     */
    @GetMapping("/inputs")
    public List<InputsDTO> getAllInputs() {
        log.debug("REST request to get all Inputs");
        return inputsService.findAll();
    }

    /**
     * {@code GET  /inputs/:id} : get the "id" inputs.
     *
     * @param id the id of the inputsDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the inputsDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/inputs/{id}")
    public ResponseEntity<InputsDTO> getInputs(@PathVariable Long id) {
        log.debug("REST request to get Inputs : {}", id);
        Optional<InputsDTO> inputsDTO = inputsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(inputsDTO);
    }

    /**
     * {@code DELETE  /inputs/:id} : delete the "id" inputs.
     *
     * @param id the id of the inputsDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/inputs/{id}")
    public ResponseEntity<Void> deleteInputs(@PathVariable Long id) {
        log.debug("REST request to delete Inputs : {}", id);
        inputsService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
