package com.synectiks.asset.business.service;

import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.synectiks.asset.aws.AwsUtils;
import com.synectiks.asset.config.Constants;
import com.synectiks.asset.domain.Accounts;
import com.synectiks.asset.domain.Organization;
import com.synectiks.asset.domain.OrganizationalUnit;
import com.synectiks.asset.repository.AccountsRepository;
import com.synectiks.asset.repository.OrganizationalUnitRepository;

@Service
public class AccountsService {
	
	private static final Logger logger = LoggerFactory.getLogger(AccountsService.class);
		
	@Autowired
	OrganizationService organizationService;
	
	@Autowired
	private AccountsRepository accountsRepository;
	
	@Autowired
	CloudAssetService cloudAssetService;
	
	@Autowired
	private OrganizationalUnitRepository organizationalUnitRepository;
	
	public Accounts getAccount(Long id) {
		logger.info("Getting account by id. Id: "+id);
		Optional<Accounts> oa = accountsRepository.findById(id);
		if(oa.isPresent()) {
			logger.debug("Account :"+oa.get().toString());
			Map<String, String> assetSarchParams = new HashMap<String, String>();
			assetSarchParams.put("accountId", oa.get().getAccountId());
			Accounts ac = oa.get();
			if(!StringUtils.isBlank(ac.getTenantId())) {
				Organization org = organizationService.getOrganization(Long.parseLong(ac.getTenantId()));
				ac.setOrganizationName(org.getName());
			}
			return ac;
		}
		logger.warn("Account not found");
		return null;
	}
	
	public Accounts getAccountByAccountAndTenantId(String accountId, String tenantId) {
		logger.info("Getting account by account id:"+accountId+" and tenanat id: "+tenantId);
		Accounts obj = new Accounts();
		obj.setAccountId(accountId);
		obj.setTenantId(tenantId);
		
		Optional<Accounts> oa = accountsRepository.findOne(Example.of(obj));
		if(oa.isPresent()) {
			logger.debug("Account :"+oa.get().toString());
			Map<String, String> assetSarchParams = new HashMap<String, String>();
			assetSarchParams.put("accountId", accountId);
			Accounts ac = oa.get();
			Organization org = organizationService.getOrganization(Long.parseLong(ac.getTenantId()));
			ac.setOrganizationName(org.getName());
			return ac;
			
		}
		logger.warn("Account not found");
		return null;
	}
	
	public List<Accounts> searchAccounts(Map<String, String> reqObj) {
		logger.info("Search accounts on given filter criteria");
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
			Organization org = organizationService.getOrganization(Long.parseLong(ac.getTenantId()));
			ac.setOrganizationName(org.getName());
			assetSarchParams.put("accountId", ac.getAccountId());
		}
		return list;
	}
	
	
	public Accounts addAccount(ObjectNode obj) throws Exception {
		logger.info("Adding account");
		Accounts accounts = new Accounts();
		accounts.setName(obj.get("name").asText());
//		accounts.setDescription(obj.get("description").asText());
		
//		accountId is AWS account id and will be provided from UI to the application. 
//		if it is not provided from UI then try to get it from WAS with default region us-east-1. 
		if(obj.get("accountId") != null) {
			accounts.setAccountId(obj.get("accountId").asText());
		}else {
			try {
				String accountId = AwsUtils.getAwsAccountId(obj.get("accessKey").asText(), obj.get("secretKey").asText(), Constants.DEFAULT_AWS_REGION);
				accounts.setAccountId(accountId);
			}catch(Exception e) {
				logger.warn("AWS connection failed. "+e.getMessage());
			}
		}
		
//		Tenant id is the organization id of an application user (same for owner and its team). 
//		get it from security service 
		if(obj.get("orgId") != null) {
			Organization org = organizationService.getOrganization(obj.get("orgId").asLong());
			if(org != null) {
				accounts.setTenantId(String.valueOf(org.getId()));
			}
		}
		
		accounts.setAccessKey(obj.get("accessKey").asText());
		accounts.setSecretKey(obj.get("secretKey").asText());
		accounts.setCloudType("AWS");
//		accounts.setRegion(obj.get("region").asText());
//		accounts.setBucket(obj.get("bucket").asText());
//		accounts.setEmail(obj.get("email").asText());
//		accounts.setPassword(obj.get("password").asText());
		
//		accounts.setSourceJsonRef(obj.get("sourceJsonRef").asText());
//		accounts.setSourceJsonContentType(obj.get("sourceJsonContentType").asText());
		if(obj.get("ouId") != null) {
			Optional<OrganizationalUnit> oou = organizationalUnitRepository.findById(obj.get("ouId").asLong());
			if(oou.isPresent()) {
				accounts.setOrganizationalUnit(oou.get());
			}
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
		logger.info("Account added successfully");
		return accounts;
	}
	
	public Accounts updateAccount(ObjectNode obj) throws Exception  {
		logger.info("Updating account"); 
		String id =(obj.get("id")).asText();
		Optional<Accounts> oa = accountsRepository.findById(Long.parseLong(obj.get("id").asText()));
		if(!oa.isPresent()) {
			logger.error("Account not found. Given account id : "+obj.get("id").asText());
			return null;
		}
		Accounts accounts = oa.get();
		accounts.setName(obj.get("name").asText());
//		accounts.setDescription(obj.get("description").asText());
//		accounts.setTenantId(obj.get("tenantId").asText());
		accounts.setAccessKey(obj.get("accessKey").asText());
		accounts.setSecretKey(obj.get("secretKey").asText());
//		accounts.setRegion(obj.get("region").asText());
//		String bucket =(obj.get("bucket")).asText();
//		accounts.setBucket(obj.get("bucket").asText());
//		accounts.setEmail(obj.get("email").asText());
//		accounts.setPassword(obj.get("password").asText());
//		accounts.setCloudType(obj.get("cloudType").asText());
//		String sourceJsonRef =(obj.get("sourceJsonRef")).asText();
//		accounts.setSourceJsonRef(sourceJsonRef);
//		String sourceJsonContentType =(obj.get("sourceJsonContentType")).asText();
//		accounts.setSourceJsonContentType(sourceJsonContentType);
		if (obj.get("user") != null) {
			accounts.setUpdatedBy(obj.get("user").asText());
		} else {
			accounts.setUpdatedBy(Constants.SYSTEM_ACCOUNT);
		}
		Instant now = Instant.now();
		accounts.setUpdatedOn(now);
		accounts = accountsRepository.save(accounts);
		logger.info("Account updated successfully");
		return accounts;
	}
			
	public String getUuid() {
		UUID uuid = UUID.randomUUID();
		String uuidAsString = uuid.toString();
		return uuidAsString;
	}
	
}
