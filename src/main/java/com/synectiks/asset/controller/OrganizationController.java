package com.synectiks.asset.controller;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.synectiks.asset.domain.Organization;
import com.synectiks.asset.domain.OrganizationalUnit;
import com.synectiks.asset.repository.OrganizationRepository;
import com.synectiks.asset.repository.OrganizationalUnitRepository;

@RestController
@RequestMapping("/api")
public class OrganizationController {

	private static final Logger logger = LoggerFactory.getLogger(OrganizationController.class);
	
	private static final String ENTITY_NAME = "organization";
	
	@Value("${jhipster.clientApp.name}")
	private String applicationName;
	
	@Autowired
	private OrganizationRepository organizationRepository;
	
	@Autowired
	private OrganizationalUnitRepository organizationalUnitRepository;
	
	
	@GetMapping("/getAllOrganizations")
	private List<Organization> getAllOrganization() {
		List<Organization> list = organizationRepository.findAll(Sort.by(Direction.DESC, "id"));
		for(Organization org: list) {
			OrganizationalUnit ou = new OrganizationalUnit();
			ou.setOrganization(org);
			List<OrganizationalUnit> ouList = organizationalUnitRepository.findAll(Sort.by(Direction.DESC, "name"));
			org.setOrganizationalUnitList(ouList);
		}
		return list;
	}
	
	@GetMapping("/getOrganization/{id}")
    public ResponseEntity<Organization> getOrganization(@PathVariable Long id) {
		logger.debug("REST request to get Organization : {}", id);
		Optional<Organization> oo = organizationRepository.findById(id);
		if(oo.isPresent()) {
			Organization org = oo.get();
			OrganizationalUnit ou = new OrganizationalUnit();
			ou.setOrganization(org);
			List<OrganizationalUnit> ouList = organizationalUnitRepository.findAll(Sort.by(Direction.DESC, "name"));
			org.setOrganizationalUnitList(ouList);
			return ResponseEntity.status(HttpStatus.OK).body(org);
		}
		return ResponseEntity.status(HttpStatus.OK).body(null);
    }
	
	
}
