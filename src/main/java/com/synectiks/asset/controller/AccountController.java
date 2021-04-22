package com.synectiks.asset.controller;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.synectiks.asset.domain.Account;
import com.synectiks.asset.domain.Environment;
import com.synectiks.asset.domain.Organization;
import com.synectiks.asset.domain.OrganizationalUnit;
import com.synectiks.asset.repository.EnvironmentRepository;
import com.synectiks.asset.repository.OrganizationRepository;
import com.synectiks.asset.repository.OrganizationalUnitRepository;

import io.github.jhipster.web.util.HeaderUtil;

@RestController
@RequestMapping("/api")
//@CrossOrigin( origins = "*")
public class AccountController {
	private static final String ENTITY_NAME = "account";
	private static final Logger logger = LoggerFactory.getLogger(AccountController.class);
	
	@Value("${jhipster.clientApp.name}")
	private String applicationName;

	@Autowired
	private EnvironmentRepository environmentRepository;
	
	@Autowired
	private OrganizationRepository organizationRepository;
	
	@Autowired
	private OrganizationalUnitRepository organizationalUnitRepository;

	@GetMapping("/getAllAccounts")
	private List<Account> getAllEnvironment() {
		logger.info("Getting all cloud account details");
		List<OrganizationalUnit> ouList;
		List<Organization> oList;
		
		ouList = organizationalUnitRepository.findAll(Sort.by(Direction.DESC, "id"));
		if(ouList.size() > 0) {
			logger.info("List from Org unit");
			return buildAccoutFromOgrUnit(ouList);
		}
		
		oList = organizationRepository.findAll(Sort.by(Direction.DESC, "id"));
		if(oList.size() > 0) {
			logger.info("List from Org");
			return buildAccoutFromOrg(oList);
		}
		logger.info("List from Env");
		List<Environment> list = environmentRepository.findAll(Sort.by(Direction.DESC, "id"));
		
		return buildAccoutFromEnv(list);
	}
	
	private List<Account> buildAccoutFromOgrUnit(List<OrganizationalUnit> ouList) {
		List<Account> list = new ArrayList<>();
		for(OrganizationalUnit ou: ouList) {
			Account acc = new Account();
			acc.setEnvironment(ou.getOrganization().getEnvironment());
			acc.setOrganization(ou.getOrganization());
			acc.setOrganizationalUnit(ou);
			list.add(acc);
		}
		return list;
	}
	
	private List<Account> buildAccoutFromOrg(List<Organization> oList) {
		List<Account> list = new ArrayList<>();
		for(Organization o: oList) {
			Account acc = new Account();
			acc.setEnvironment(o.getEnvironment());
			acc.setOrganization(o);
			list.add(acc);
		}
		return list;
	}
	
	private List<Account> buildAccoutFromEnv(List<Environment> envList) {
		List<Account> list = new ArrayList<>();
		for(Environment e: envList) {
			Account acc = new Account();
			acc.setEnvironment(e);
			list.add(acc);
		}
		return list;
	}

	@GetMapping("/getAccount")
    public ResponseEntity<Account> getEnvironment(@RequestParam(name = "envId") Long envId,
    		@RequestParam(name = "orgId", required=false) Long orgId) 
    		throws URISyntaxException {
		logger.info("Getting single cloud account details");
		Optional<Environment> oEnv =environmentRepository.findById(envId);
		
		if(orgId != null && orgId > 0) {
			Organization org = new Organization();
			org.setId(orgId);
			org.setEnvironment(oEnv.get());
			
			Optional<Organization> oOrg = organizationRepository.findOne(Example.of(org));
			if(oOrg.isPresent()) {
				OrganizationalUnit ou = new OrganizationalUnit();
				ou.setOrganization(oOrg.get());
				List<OrganizationalUnit> ouList = organizationalUnitRepository.findAll(Example.of(ou), Sort.by(Direction.DESC, "id"));
				
				Account acc = new Account();
				acc.setOrganization(oOrg.get());
				acc.setEnvironment(oOrg.get().getEnvironment());
				if(ouList.size() > 0) {
					acc.setOrganizationalUnitList(ouList);
				}
				return ResponseEntity
						.created(new URI("/api/updateEnvironment/" + oOrg.get().getEnvironment().getId())).headers(HeaderUtil
								.createEntityCreationAlert(applicationName, false, ENTITY_NAME, oOrg.get().getEnvironment().getId().toString()))
						.body(acc);
			}
		}
		
		Account acc = new Account();
		acc.setEnvironment(oEnv.get());
		
		return ResponseEntity
				.created(new URI("/api/updateEnvironment/" + oEnv.get().getId())).headers(HeaderUtil
						.createEntityCreationAlert(applicationName, false, ENTITY_NAME, oEnv.get().getId().toString()))
				.body(acc);
		
		
	}
}
