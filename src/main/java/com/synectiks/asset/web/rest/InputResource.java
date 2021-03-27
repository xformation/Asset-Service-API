package com.synectiks.asset.web.rest;

import com.synectiks.asset.service.InputService;
import com.synectiks.asset.web.rest.errors.BadRequestAlertException;
import com.synectiks.asset.service.dto.InputDTO;

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
 * REST controller for managing {@link com.synectiks.asset.domain.Input}.
 */
@RestController
@RequestMapping("/api")
public class InputResource {

    private final Logger log = LoggerFactory.getLogger(InputResource.class);

    private static final String ENTITY_NAME = "assetserviceInput";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final InputService inputService;

    public InputResource(InputService inputService) {
        this.inputService = inputService;
    }

    /**
     * {@code POST  /inputs} : Create a new input.
     *
     * @param inputDTO the inputDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new inputDTO, or with status {@code 400 (Bad Request)} if the input has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/inputs")
    public ResponseEntity<InputDTO> createInput(@Valid @RequestBody InputDTO inputDTO) throws URISyntaxException {
        log.debug("REST request to save Input : {}", inputDTO);
        if (inputDTO.getId() != null) {
            throw new BadRequestAlertException("A new input cannot already have an ID", ENTITY_NAME, "idexists");
        }
        InputDTO result = inputService.save(inputDTO);
        return ResponseEntity.created(new URI("/api/inputs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /inputs} : Updates an existing input.
     *
     * @param inputDTO the inputDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated inputDTO,
     * or with status {@code 400 (Bad Request)} if the inputDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the inputDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/inputs")
    public ResponseEntity<InputDTO> updateInput(@Valid @RequestBody InputDTO inputDTO) throws URISyntaxException {
        log.debug("REST request to update Input : {}", inputDTO);
        if (inputDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        InputDTO result = inputService.save(inputDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, inputDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /inputs} : get all the inputs.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of inputs in body.
     */
    @GetMapping("/inputs")
    public List<InputDTO> getAllInputs() {
        log.debug("REST request to get all Inputs");
        return inputService.findAll();
    }

    /**
     * {@code GET  /inputs/:id} : get the "id" input.
     *
     * @param id the id of the inputDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the inputDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/inputs/{id}")
    public ResponseEntity<InputDTO> getInput(@PathVariable Long id) {
        log.debug("REST request to get Input : {}", id);
        Optional<InputDTO> inputDTO = inputService.findOne(id);
        return ResponseUtil.wrapOrNotFound(inputDTO);
    }

    /**
     * {@code DELETE  /inputs/:id} : delete the "id" input.
     *
     * @param id the id of the inputDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/inputs/{id}")
    public ResponseEntity<Void> deleteInput(@PathVariable Long id) {
        log.debug("REST request to delete Input : {}", id);
        inputService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
