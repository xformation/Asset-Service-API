package com.synectiks.asset.controller;

import java.net.URISyntaxException;
import java.time.Instant;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.synectiks.asset.business.appservice.OrganizationService;
import com.synectiks.asset.config.Constants;
import com.synectiks.asset.domain.Organization;
import com.synectiks.asset.domain.OrganizationalUnit;
import com.synectiks.asset.repository.OrganizationalUnitRepository;

@RestController
@RequestMapping("/api")
public class OrganizationalUnitController {

	private static final Logger logger = LoggerFactory.getLogger(OrganizationalUnitController.class);
	
	@Autowired
	OrganizationService organizationService;
	
	@Autowired
	private OrganizationalUnitRepository organizationalUnitRepository;
	
	@PostMapping("/addOrganizationUnit/{orgId}/{ouName}")
	public ResponseEntity<OrganizationalUnit> addOrganizationUnit(@PathVariable Long orgId,  @PathVariable String ouName ) throws URISyntaxException {
		Organization org = organizationService.getOrganization(orgId);
		if(org == null) {
			logger.error("Cannot add organization unit. Parent organization not found");
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(null);
		}
		OrganizationalUnit ou = new OrganizationalUnit();
		ou.setOrganizationId(org.getId());
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
	
	@GetMapping("/getAllOrganizationalUnits")
	private List<OrganizationalUnit> getAllOrganizationalUnits() {
		List<OrganizationalUnit> list = organizationalUnitRepository.findAll(Sort.by(Direction.ASC, "name"));
		return list;
	}
	
	@GetMapping("/getAllOrgUnitsByOrgId/{orgId}")
	private List<OrganizationalUnit> getAllOrgUnitsByOrgId(@PathVariable Long orgId) {
		OrganizationalUnit ou = new OrganizationalUnit();
		ou.setOrganizationId(orgId);
		List<OrganizationalUnit> ouList = organizationalUnitRepository.findAll(Example.of(ou), Sort.by(Direction.ASC, "name"));
		return ouList;
	}
	
	@GetMapping("/getAllOrgUnitsByUserName/{userName}")
	private Organization getAllOrgUnitsByUserName(@PathVariable String userName) {
		Organization org = organizationService.getOrganization(userName);
		List<OrganizationalUnit> list = getAllOrgUnitsByOrgId(org.getId());
		org.setOrganizationalUnitList(list);
		return org;
	}
	
}
