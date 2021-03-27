package com.synectiks.asset.web.rest;

import com.synectiks.asset.service.OrganizationalUnitService;
import com.synectiks.asset.web.rest.errors.BadRequestAlertException;
import com.synectiks.asset.service.dto.OrganizationalUnitDTO;

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
 * REST controller for managing {@link com.synectiks.asset.domain.OrganizationalUnit}.
 */
@RestController
@RequestMapping("/api")
public class OrganizationalUnitResource {

    private final Logger log = LoggerFactory.getLogger(OrganizationalUnitResource.class);

    private static final String ENTITY_NAME = "assetserviceOrganizationalUnit";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final OrganizationalUnitService organizationalUnitService;

    public OrganizationalUnitResource(OrganizationalUnitService organizationalUnitService) {
        this.organizationalUnitService = organizationalUnitService;
    }

    /**
     * {@code POST  /organizational-units} : Create a new organizationalUnit.
     *
     * @param organizationalUnitDTO the organizationalUnitDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new organizationalUnitDTO, or with status {@code 400 (Bad Request)} if the organizationalUnit has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/organizational-units")
    public ResponseEntity<OrganizationalUnitDTO> createOrganizationalUnit(@Valid @RequestBody OrganizationalUnitDTO organizationalUnitDTO) throws URISyntaxException {
        log.debug("REST request to save OrganizationalUnit : {}", organizationalUnitDTO);
        if (organizationalUnitDTO.getId() != null) {
            throw new BadRequestAlertException("A new organizationalUnit cannot already have an ID", ENTITY_NAME, "idexists");
        }
        OrganizationalUnitDTO result = organizationalUnitService.save(organizationalUnitDTO);
        return ResponseEntity.created(new URI("/api/organizational-units/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /organizational-units} : Updates an existing organizationalUnit.
     *
     * @param organizationalUnitDTO the organizationalUnitDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated organizationalUnitDTO,
     * or with status {@code 400 (Bad Request)} if the organizationalUnitDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the organizationalUnitDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/organizational-units")
    public ResponseEntity<OrganizationalUnitDTO> updateOrganizationalUnit(@Valid @RequestBody OrganizationalUnitDTO organizationalUnitDTO) throws URISyntaxException {
        log.debug("REST request to update OrganizationalUnit : {}", organizationalUnitDTO);
        if (organizationalUnitDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        OrganizationalUnitDTO result = organizationalUnitService.save(organizationalUnitDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, organizationalUnitDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /organizational-units} : get all the organizationalUnits.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of organizationalUnits in body.
     */
    @GetMapping("/organizational-units")
    public List<OrganizationalUnitDTO> getAllOrganizationalUnits() {
        log.debug("REST request to get all OrganizationalUnits");
        return organizationalUnitService.findAll();
    }

    /**
     * {@code GET  /organizational-units/:id} : get the "id" organizationalUnit.
     *
     * @param id the id of the organizationalUnitDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the organizationalUnitDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/organizational-units/{id}")
    public ResponseEntity<OrganizationalUnitDTO> getOrganizationalUnit(@PathVariable Long id) {
        log.debug("REST request to get OrganizationalUnit : {}", id);
        Optional<OrganizationalUnitDTO> organizationalUnitDTO = organizationalUnitService.findOne(id);
        return ResponseUtil.wrapOrNotFound(organizationalUnitDTO);
    }

    /**
     * {@code DELETE  /organizational-units/:id} : delete the "id" organizationalUnit.
     *
     * @param id the id of the organizationalUnitDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/organizational-units/{id}")
    public ResponseEntity<Void> deleteOrganizationalUnit(@PathVariable Long id) {
        log.debug("REST request to delete OrganizationalUnit : {}", id);
        organizationalUnitService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
