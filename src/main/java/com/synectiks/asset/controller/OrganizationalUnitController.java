package com.synectiks.asset.controller;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.Instant;
import java.util.ArrayList;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.synectiks.asset.config.Constants;
import com.synectiks.asset.domain.Organization;
import com.synectiks.asset.domain.OrganizationalUnit;
import com.synectiks.asset.repository.OrganizationRepository;
import com.synectiks.asset.repository.OrganizationalUnitRepository;

import io.github.jhipster.web.util.HeaderUtil;

@RestController
@RequestMapping("/api")
public class OrganizationalUnitController {

	private static final Logger logger = LoggerFactory.getLogger(OrganizationalUnitController.class);
	
	private static final String ENTITY_NAME = "OrganizationalUnit";
	
	@Value("${jhipster.clientApp.name}")
	private String applicationName;
	
	@Autowired
	private OrganizationRepository organizationRepository;
	
	@Autowired
	private OrganizationalUnitRepository organizationalUnitRepository;
	
	@PostMapping("/addOrganizationUnit/{orgId}/{ouName}")
	public ResponseEntity<OrganizationalUnit> addOrganizationUnit(@PathVariable Long orgId,  @PathVariable String ouName ) throws URISyntaxException {
	
		Optional<Organization> oRg = organizationRepository.findById(orgId);
		if(!oRg.isPresent()) {
			logger.error("Cannot add organization unit. Parent organization not found");
			ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(null);
		}
		
		OrganizationalUnit ou = new OrganizationalUnit();
		ou.setOrganization(oRg.get());
		ou.setName(ouName);
		ou.setDescription(ouName+" department");
		ou.setStatus("ACTIVE");
		Instant now = Instant.now();
		ou.setCreatedOn(now);
		ou.setUpdatedOn(now);
		ou.setCreatedBy(Constants.SYSTEM_ACCOUNT);
		
		ou = organizationalUnitRepository.save(ou);
		logger.info("Organization unit added successfully. New organization unit : "+ou.toString());
		return ResponseEntity.status(HttpStatus.OK).body(ou);
	}
	
	@GetMapping("/getAllOrgUnits")
	private List<OrganizationalUnit> getAllOrganizationalUnits() {
		List<OrganizationalUnit> list = organizationalUnitRepository.findAll(Sort.by(Direction.DESC, "name"));
		return list;
	}
	
	@GetMapping("/getAllOrgUnitsByOrgId/{orgId}")
	private List<OrganizationalUnit> getAllOrganizationalUnits(@PathVariable Long orgId) {
		Optional<Organization> oo = organizationRepository.findById(orgId);
		List<OrganizationalUnit> ouList = new ArrayList<>();
		if(oo.isPresent()) {
			Organization org = oo.get();
			OrganizationalUnit ou = new OrganizationalUnit();
			ou.setOrganization(org);
			ouList = organizationalUnitRepository.findAll(Sort.by(Direction.DESC, "name"));
		}
		
		return ouList;
	}
	
}
