package com.synectiks.asset.controller;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.synectiks.asset.config.Constants;
import com.synectiks.asset.domain.Accounts;
import com.synectiks.asset.repository.AccountsRepository;

import io.github.jhipster.web.util.HeaderUtil;
import io.micrometer.core.instrument.util.StringUtils;

@RestController
@RequestMapping("/api")
public class AccountsController {
	
	private static final Logger logger = LoggerFactory.getLogger(AccountsController.class);
	private static final String ENTITY_NAME = "accounts";
	private static final List<List<Accounts>> List = null;

	@Value("${jhipster.clientApp.name}")
	private String applicationName;
	
	@Autowired
	private AccountsRepository accountsRepository;
	
	@GetMapping("/getAccount/{id}")
	public ResponseEntity<Object> getAccount(Long id) {
		logger.info("Request to get account by id. Id: "+id);
		Optional<Accounts> oa = accountsRepository.findById(id);
		if(oa.isPresent()) {
			logger.info("Account :"+oa.get().toString());
			return ResponseEntity.status(HttpStatus.OK).body(oa.get());
		}
		logger.warn("Account not found");
		return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(null);
	}
	
	@GetMapping("/getAccountByAccountAndTenantId")
	public ResponseEntity<Object> getAccountByAccountAndTenantId(@RequestParam String accountId, @RequestParam String tenantId) {
		logger.info("Request to get account by account id:"+accountId+" and tenanat id: "+tenantId);
		Accounts obj = new Accounts();
		obj.setAccountId(accountId);
		obj.setTenantId(tenantId);
		
		Optional<Accounts> oa = accountsRepository.findOne(Example.of(obj));
		if(oa.isPresent()) {
			logger.info("Account :"+oa.get().toString());
			return ResponseEntity.status(HttpStatus.OK).body(oa.get());
			
		}
		logger.warn("Account not found");
		return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(null);
	}
	
	/**
	 * {@code GET  /searchAccount} : get collectors based on filter criteria.
	 *
	 * @return the {@link List<Accounts>} with status {@code 200 (OK)} and the list
	 *         of Accounts in body.
	 */
	@GetMapping("/searchAccounts")
	public List<Accounts> searchAccounts(@RequestParam Map<String, String> Accounts) {
		logger.info("Request to get accounts on given filter criteria");
		Accounts obj = new Accounts();
		
		boolean isFilter = false;
		if (Accounts.get("id") != null) {
			obj.setId(Long.parseLong(Accounts.get("id")));
			isFilter = true;
		}
		if (Accounts.get("name") != null) {
			obj.setName(Accounts.get("name"));
			isFilter = true;
		}
		if (Accounts.get("description") != null) {
			obj.setDescription(Accounts.get("description"));
			isFilter = true;
		}
		if (Accounts.get("tenantId") != null) {
			obj.setTenantId(Accounts.get("tenantId"));
			isFilter = true;
		}
		if (Accounts.get("accountId") != null) {
			obj.setAccountId(Accounts.get("accountId"));
			isFilter = true;
		}
		if (Accounts.get("accessKey") != null) {
			obj.setAccessKey(Accounts.get("accessKey"));
			isFilter = true;
		}
		if (Accounts.get("secretKey") != null) {
			obj.setSecretKey(Accounts.get("secretKey"));
			isFilter = true;
		}
		if (Accounts.get("region") != null) {
			obj.setRegion(Accounts.get("region"));
			isFilter = true;
		}
		if (Accounts.get("bucket") != null) {
			obj.setBucket(Accounts.get("bucket"));
			isFilter = true;
		}
		if (Accounts.get("email") != null) {
			obj.setEmail(Accounts.get("email"));
			isFilter = true;
		}
		if (Accounts.get("password") != null) {
			obj.setPassword(Accounts.get("password"));
			isFilter = true;
		}
		if (Accounts.get("cloudType") != null) {
			obj.setCloudType(Accounts.get("cloudType"));
			isFilter = true;
		}
		if (Accounts.get("sourceJsonRef") != null) {
			obj.setSourceJsonRef(Accounts.get("sourceJsonRef"));
			isFilter = true;
		}
		if (Accounts.get("sourceJsonContentType") != null) {
			obj.setSourceJsonContentType(Accounts.get("sourceJsonContentType"));
			isFilter = true;
		}

		List<Accounts> list = null;
		if (isFilter) {
			list = this.accountsRepository.findAll(Example.of(obj), Sort.by(Direction.DESC, "id"));
		} else {
			list = this.accountsRepository.findAll(Sort.by(Direction.DESC, "id"));
		}

		return list;
	}
	
	
	@PostMapping("/addAccount")
	public  List<List<Accounts>> addAccount(@RequestBody ObjectNode obj) throws JSONException {
		
		Accounts accounts = new Accounts();
		accounts.setAccountId(getUuid());
		accounts.setName(obj.get("name").asText());
		accounts.setDescription(obj.get("description").asText());
		accounts.setTenantId(obj.get("tenantId").asText());
		accounts.setAccessKey(obj.get("accessKey").asText());
		accounts.setSecretKey(obj.get("secretKey").asText());
		accounts.setRegion(obj.get("region").asText());
		accounts.setBucket(obj.get("bucket").asText());
		accounts.setEmail(obj.get("email").asText());
		accounts.setPassword(obj.get("password").asText());
		accounts.setCloudType(obj.get("cloudType").asText());
		accounts.setSourceJsonRef(obj.get("sourceJsonRef").asText());
		accounts.setSourceJsonContentType(obj.get("sourceJsonContentType").asText());
		
	 	if (obj.get("user") != null) {
			accounts.setCreatedBy(obj.get("user").asText());
			accounts.setUpdatedBy(obj.get("user").asText());
		} else {
			accounts.setCreatedBy(Constants.SYSTEM_ACCOUNT);
			accounts.setUpdatedBy(Constants.SYSTEM_ACCOUNT);
		}
		Instant now = Instant.now();
		accounts.setCreatedOn(now);
		accounts.setUpdatedOn(now);
		accounts = accountsRepository.save(accounts);
		return List;
	}
	
	

	@PutMapping("/updateAccount")
	public ResponseEntity<Accounts> updateAccount(@RequestBody ObjectNode obj) throws URISyntaxException  {
		 String id =(obj.get("id")).asText();
		 java.util.Optional<Accounts> abc = accountsRepository.findById(Long.parseLong(obj.get("id").asText()));
		 if(!abc.isPresent()) {
			return ResponseEntity.created(new URI("/api/updateAccount/")).headers(HeaderUtil
					.createEntityCreationAlert(applicationName, false, ENTITY_NAME, ""))
					.body(null);
		 }
		 Accounts accounts = abc.get();
		 accounts.setName(obj.get("name").asText());
		 accounts.setDescription(obj.get("description").asText());
		 accounts.setTenantId(obj.get("tenantId").asText());
		 accounts.setAccessKey(obj.get("accessKey").asText());
		 accounts.setSecretKey(obj.get("secretKey").asText());
		 accounts.setRegion(obj.get("region").asText());
		 String bucket =(obj.get("bucket")).asText();
		 accounts.setBucket(obj.get("bucket").asText());
		 accounts.setEmail(obj.get("email").asText());
		 accounts.setPassword(obj.get("password").asText());
		 accounts.setCloudType(obj.get("cloudType").asText());
		 String sourceJsonRef =(obj.get("sourceJsonRef")).asText();
		 accounts.setSourceJsonRef(sourceJsonRef);
		 String sourceJsonContentType =(obj.get("sourceJsonContentType")).asText();
		 accounts.setSourceJsonContentType(sourceJsonContentType);
		 if (obj.get("user") != null) {
			accounts.setUpdatedBy(obj.get("user").asText());
		 } else {
			accounts.setUpdatedBy(Constants.SYSTEM_ACCOUNT);
		 }
		 Instant now = Instant.now();
		 accounts.setUpdatedOn(now);
		 accounts = accountsRepository.save(accounts);
		 logger.info("Updating account completed");
		 return ResponseEntity
				.created(new URI("/api/updateAccount/" + accounts.getId())).headers(HeaderUtil
						.createEntityCreationAlert(applicationName, false, ENTITY_NAME, accounts.getId().toString()))
				.body(accounts);
	}
			

	public String getUuid() {
		UUID uuid = UUID.randomUUID();
		String uuidAsString = uuid.toString();
		return uuidAsString;
	}
}
