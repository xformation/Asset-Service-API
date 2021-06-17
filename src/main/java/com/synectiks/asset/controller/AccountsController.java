package com.synectiks.asset.controller;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.Instant;
import java.util.HashMap;
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.amazonaws.services.s3.AmazonS3;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.synectiks.asset.aws.AwsUtils;
import com.synectiks.asset.business.service.CloudAssetService;
import com.synectiks.asset.config.Constants;
import com.synectiks.asset.domain.Accounts;
import com.synectiks.asset.domain.Asset;
import com.synectiks.asset.domain.Organization;
import com.synectiks.asset.domain.OrganizationalUnit;
import com.synectiks.asset.repository.AccountsRepository;
import com.synectiks.asset.repository.OrganizationRepository;
import com.synectiks.asset.repository.OrganizationalUnitRepository;

import io.github.jhipster.web.util.HeaderUtil;

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
	
	@Autowired
	CloudAssetService cloudAssetService;
	
	@Autowired
	private OrganizationRepository organizationRepository;
	
	@Autowired
	private OrganizationalUnitRepository organizationalUnitRepository;
	
	@GetMapping("/getAccount/{id}")
	public ResponseEntity<Accounts> getAccount(@PathVariable Long id) {
		logger.info("Request to get account by id. Id: "+id);
		Optional<Accounts> oa = accountsRepository.findById(id);
		if(oa.isPresent()) {
			logger.info("Account :"+oa.get().toString());
			Map<String, String> assetSarchParams = new HashMap<String, String>();
			assetSarchParams.put("accountId", oa.get().getAccountId());
			List<Asset> assetList = cloudAssetService.searchCloudAsset(assetSarchParams);
			Accounts ac = oa.get();
			ac.setAssetList(assetList);
			return ResponseEntity.status(HttpStatus.OK).body(ac);
		}
		logger.warn("Account not found");
		return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(null);
	}
	
	@GetMapping("/getAccountByAccountAndTenantId")
	public ResponseEntity<Accounts> getAccountByAccountAndTenantId(@RequestParam String accountId, @RequestParam String tenantId) {
		logger.info("Request to get account by account id:"+accountId+" and tenanat id: "+tenantId);
		Accounts obj = new Accounts();
		obj.setAccountId(accountId);
		obj.setTenantId(tenantId);
		
		Optional<Accounts> oa = accountsRepository.findOne(Example.of(obj));
		if(oa.isPresent()) {
			logger.info("Account :"+oa.get().toString());
			Map<String, String> assetSarchParams = new HashMap<String, String>();
			assetSarchParams.put("accountId", accountId);
			List<Asset> assetList = cloudAssetService.searchCloudAsset(assetSarchParams);
			Accounts ac = oa.get();
			ac.setAssetList(assetList);
			return ResponseEntity.status(HttpStatus.OK).body(ac);
			
		}
		logger.warn("Account not found");
		return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(null);
	}
	
	/**
	 * {@code GET  /searchAccounts} : get collectors based on filter criteria.
	 *
	 * @return the {@link List<Accounts>} with status {@code 200 (OK)} and the list
	 *         of Accounts in body.
	 */
	@GetMapping("/searchAccounts")
	public List<Accounts> searchAccounts(@RequestParam Map<String, String> reqObj) {
		logger.info("Request to get accounts on given filter criteria");
		Accounts accounts = new Accounts();
		
		boolean isFilter = false;
		if (reqObj.get("id") != null) {
			accounts.setId(Long.parseLong(reqObj.get("id")));
			isFilter = true;
		}
		if (reqObj.get("name") != null) {
			accounts.setName(reqObj.get("name"));
			isFilter = true;
		}
		if (reqObj.get("description") != null) {
			accounts.setDescription(reqObj.get("description"));
			isFilter = true;
		}
		if (reqObj.get("tenantId") != null) {
			accounts.setTenantId(reqObj.get("tenantId"));
			isFilter = true;
		}
		if (reqObj.get("accountId") != null) {
			accounts.setAccountId(reqObj.get("accountId"));
			isFilter = true;
		}
		if (reqObj.get("accessKey") != null) {
			accounts.setAccessKey(reqObj.get("accessKey"));
			isFilter = true;
		}
		if (reqObj.get("secretKey") != null) {
			accounts.setSecretKey(reqObj.get("secretKey"));
			isFilter = true;
		}
		if (reqObj.get("region") != null) {
			accounts.setRegion(reqObj.get("region"));
			isFilter = true;
		}
		if (reqObj.get("bucket") != null) {
			accounts.setBucket(reqObj.get("bucket"));
			isFilter = true;
		}
		if (reqObj.get("email") != null) {
			accounts.setEmail(reqObj.get("email"));
			isFilter = true;
		}
		if (reqObj.get("password") != null) {
			accounts.setPassword(reqObj.get("password"));
			isFilter = true;
		}
		if (reqObj.get("cloudType") != null) {
			accounts.setCloudType(reqObj.get("cloudType"));
			isFilter = true;
		}
		if (reqObj.get("sourceJsonRef") != null) {
			accounts.setSourceJsonRef(reqObj.get("sourceJsonRef"));
			isFilter = true;
		}
		if (reqObj.get("sourceJsonContentType") != null) {
			accounts.setSourceJsonContentType(reqObj.get("sourceJsonContentType"));
			isFilter = true;
		}

		List<Accounts> list = null;
		if (isFilter) {
			list = this.accountsRepository.findAll(Example.of(accounts), Sort.by(Direction.DESC, "id"));
		} else {
			list = this.accountsRepository.findAll(Sort.by(Direction.DESC, "id"));
		}
		
		Map<String, String> assetSarchParams = new HashMap<String, String>();
		for(Accounts ac: list) {
			assetSarchParams.put("accountId", ac.getAccountId());
			List<Asset> assetList = cloudAssetService.searchCloudAsset(assetSarchParams);
			ac.setAssetList(assetList);
		}
		
		return list;
	}
	
	
	@PostMapping("/addAccount")
	public  ResponseEntity<Accounts> addAccount(@RequestBody ObjectNode obj) throws JSONException {
		
		Accounts accounts = new Accounts();
		accounts.setAccountId(getUuid());
		accounts.setName(obj.get("name").asText());
//		accounts.setDescription(obj.get("description").asText());
		String account = AwsUtils.getAwsAccountId(obj.get("accessKey").asText(), obj.get("secretKey").asText(), "us-east-1");
		accounts.setTenantId(account);
		
		accounts.setAccessKey(obj.get("accessKey").asText());
		accounts.setSecretKey(obj.get("secretKey").asText());
//		accounts.setRegion(obj.get("region").asText());
//		accounts.setBucket(obj.get("bucket").asText());
//		accounts.setEmail(obj.get("email").asText());
//		accounts.setPassword(obj.get("password").asText());
		accounts.setCloudType("AWS");
//		accounts.setSourceJsonRef(obj.get("sourceJsonRef").asText());
//		accounts.setSourceJsonContentType(obj.get("sourceJsonContentType").asText());
		
		Optional<Organization> oo = organizationRepository.findById(obj.get("orgId").asLong());
		Optional<OrganizationalUnit> oou = organizationalUnitRepository.findById(obj.get("ouId").asLong());
		if(oo.isPresent()) {
			accounts.setOrganization(oo.get());
		}
		if(oou.isPresent()) {
			accounts.setOrganizationalUnit(oou.get());
		}
		
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
		return ResponseEntity.status(HttpStatus.OK).body(accounts);
	}
	
	

	@PutMapping("/updateAccount")
	public ResponseEntity<Accounts> updateAccount(@RequestBody ObjectNode obj) throws URISyntaxException  {
		 String id =(obj.get("id")).asText();
		 Optional<Accounts> oa = accountsRepository.findById(Long.parseLong(obj.get("id").asText()));
		 if(!oa.isPresent()) {
			return ResponseEntity.created(new URI("/api/updateAccount/")).headers(HeaderUtil
					.createEntityCreationAlert(applicationName, false, ENTITY_NAME, ""))
					.body(null);
		 }
		 Accounts accounts = oa.get();
		 accounts.setName(obj.get("name").asText());
//		 accounts.setDescription(obj.get("description").asText());
//		 accounts.setTenantId(obj.get("tenantId").asText());
		 accounts.setAccessKey(obj.get("accessKey").asText());
		 accounts.setSecretKey(obj.get("secretKey").asText());
//		 accounts.setRegion(obj.get("region").asText());
//		 String bucket =(obj.get("bucket")).asText();
//		 accounts.setBucket(obj.get("bucket").asText());
//		 accounts.setEmail(obj.get("email").asText());
//		 accounts.setPassword(obj.get("password").asText());
//		 accounts.setCloudType(obj.get("cloudType").asText());
//		 String sourceJsonRef =(obj.get("sourceJsonRef")).asText();
//		 accounts.setSourceJsonRef(sourceJsonRef);
//		 String sourceJsonContentType =(obj.get("sourceJsonContentType")).asText();
//		 accounts.setSourceJsonContentType(sourceJsonContentType);
		 if (obj.get("user") != null) {
			accounts.setUpdatedBy(obj.get("user").asText());
		 } else {
			accounts.setUpdatedBy(Constants.SYSTEM_ACCOUNT);
		 }
		 Instant now = Instant.now();
		 accounts.setUpdatedOn(now);
		 accounts = accountsRepository.save(accounts);
		 logger.info("Updating account completed");
		 return ResponseEntity.status(HttpStatus.OK).body(accounts);
	}
			

	public String getUuid() {
		UUID uuid = UUID.randomUUID();
		String uuidAsString = uuid.toString();
		return uuidAsString;
	}
}
