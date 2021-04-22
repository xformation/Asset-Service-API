package com.synectiks.asset.controller;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.Instant;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.synectiks.asset.domain.Organization;
import com.synectiks.asset.domain.OrganizationalUnit;
import com.synectiks.asset.repository.OrganizationRepository;
import com.synectiks.asset.repository.OrganizationalUnitRepository;

import io.github.jhipster.web.util.HeaderUtil;

@RestController
@RequestMapping("/api")
public class OrganizationalUnitController {

private static final String ENTITY_NAME = "OrganizationalUnit";
	
	@Value("${jhipster.clientApp.name}")
	private String applicationName;
	
	@Autowired
	private OrganizationRepository organizationRepository;
	
	@Autowired
	private OrganizationalUnitRepository organizationalUnitRepository;
	
	@PostMapping("/addOrganizationUnit{id}")
	public ResponseEntity<OrganizationalUnit> addOrganizationUnit(
			Long id, 
			@RequestParam(required = false)String name
			) throws URISyntaxException {
	
		Optional<Organization> oRg = organizationRepository.findById(id);
		if(!oRg.isPresent()) {
			return ResponseEntity
					.created(new URI("/api/addOrganizationUnit")).headers(HeaderUtil
							.createEntityCreationAlert(applicationName, false, ENTITY_NAME, null))
					.body(null);
		}
		
		OrganizationalUnit ou = new OrganizationalUnit();
		ou.setOrganization(oRg.get());
		ou.setName(name);
		Instant now = Instant.now();
		ou.setCreatedOn(now);
		ou.setUpdatedOn(now);
		ou = organizationalUnitRepository.save(ou);
		return ResponseEntity
				.created(new URI("/api/addOrganizationUnit/" + ou.getId())).headers(HeaderUtil
						.createEntityCreationAlert(applicationName, false, ENTITY_NAME, ou.getId().toString()))
				.body(ou);
	
	
	
  }
}
