package com.synectiks.asset.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.synectiks.asset.domain.Environment;
import com.synectiks.asset.domain.Organization;
import com.synectiks.asset.domain.OrganizationalUnit;
import com.synectiks.asset.repository.EnvironmentRepository;
import com.synectiks.asset.repository.OrganizationRepository;
import com.synectiks.asset.repository.OrganizationalUnitRepository;

@RestController
@RequestMapping("/api")
public class OrganizationController {

	private static final String ENTITY_NAME = "organization";
	
	@Value("${jhipster.clientApp.name}")
	private String applicationName;
	
	
	@Autowired
	private EnvironmentRepository environmentRepository;
	
	@Autowired
	private OrganizationRepository organizationRepository;
	
	@Autowired
	private OrganizationalUnitRepository organizationalUnitRepository;
	
	
	@GetMapping("/getAllOrganization")
	private List<Organization> getAllOrganization() {

		List<Organization> list = organizationRepository.findAll(Sort.by(Direction.DESC, "id"));

		return list;
	}
	@GetMapping("/getAllOrganizationalUnit")
	private List<OrganizationalUnit> getAllOrganizationalUnit() {

		List<OrganizationalUnit> list = organizationalUnitRepository.findAll(Sort.by(Direction.DESC, "id"));

		return list;
	}
}
