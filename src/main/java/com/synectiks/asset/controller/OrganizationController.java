package com.synectiks.asset.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.web.bind.annotation.GetMapping;
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
			
		}
		return list;
	}
	
	@GetMapping("/getAllOrganizationalUnits")
	private List<OrganizationalUnit> getAllOrganizationalUnits() {
		List<OrganizationalUnit> list = organizationalUnitRepository.findAll(Sort.by(Direction.DESC, "id"));
		return list;
	}
	
	@GetMapping("/getAllOrganizationalUnits/{orgId}")
	private List<OrganizationalUnit> getAllOrganizationalUnits(Long orgId) {
		List<OrganizationalUnit> list = organizationalUnitRepository.findAll(Sort.by(Direction.DESC, "id"));
		return list;
	}
}
