package com.synectiks.asset.business.appservice;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.transaction.Transactional;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.synectiks.asset.config.Constants;
import com.synectiks.asset.domain.Accounts;
import com.synectiks.asset.domain.Dashboard;
import com.synectiks.asset.domain.InputConfig;
import com.synectiks.asset.repository.AccountsRepository;
import com.synectiks.asset.repository.InputConfigRepository;

@Service
public class InputConfigService {
	
	private static final Logger logger = LoggerFactory.getLogger(InputConfigService.class);
	
	@Autowired
	private InputConfigRepository inputConfigRepository;
	
	@Autowired
	private AccountsRepository accountsRepository;
	
//	@Autowired
//	private ApplicationAssetService applicationAssetService;
	
	public InputConfig getInputConfig(Long id) {
		logger.info("Getting input config by id: "+id);
		Optional<InputConfig> oin = inputConfigRepository.findById(id);
		if(oin.isPresent()) {
			logger.info("Input config: "+oin.get().toString());
			return oin.get();
		}
		logger.warn("InputConfig not found");
		return null;
	}
	
	public List<InputConfig> searchInputConfig(Map<String, String> object) {
		logger.info("Searching Input config");
		InputConfig obj = new InputConfig();
		
		boolean isFilter = false;
		if (object.get("id") != null) {
			obj.setId(Long.parseLong(object.get("id")));
			isFilter = true;
		}
		if (!StringUtils.isBlank(object.get("accountId")) && !StringUtils.isBlank(object.get("tenantId"))) {
			Accounts ac = new Accounts();
			ac.setAccountId(object.get("accountId"));
			ac.setTenantId(object.get("tenantId"));
			Optional<Accounts> oa = accountsRepository.findOne(Example.of(ac));
			if(oa.isPresent()) {
				obj.setAccounts(oa.get());
			}
			isFilter = true;
		}
		
		if (!StringUtils.isBlank(object.get("tenantId"))) {
			obj.setTenantId(object.get("tenantId"));
			isFilter = true;
		}
		
		if (!StringUtils.isBlank(object.get("status"))) {
			obj.setStatus(object.get("status").toUpperCase());
			isFilter = true;
		}
		if (!StringUtils.isBlank(object.get("inputType"))) {
			obj.setInputType(object.get("inputType"));
			isFilter = true;
		}
		List<InputConfig> list = null;
		if (isFilter) {
			list = this.inputConfigRepository.findAll(Example.of(obj));
		} else {
			list = this.inputConfigRepository.findAll();
		}
		
		for(InputConfig inputConfig: list) {
			if(Constants.ENABLED_DASHBOARD_CACHE.containsKey(inputConfig.getAccounts().getTenantId())) {
				Map<String, Map<String, List<Dashboard>>> accountMap = Constants.ENABLED_DASHBOARD_CACHE.get(inputConfig.getAccounts().getTenantId());
				if(accountMap.containsKey(inputConfig.getAccounts().getAccountId())) {
					Map<String, List<Dashboard>> inpMap = accountMap.get(inputConfig.getAccounts().getAccountId());
					if(inpMap.containsKey(inputConfig.getInputType().toUpperCase())) {
						inputConfig.setEnabledDashboardList(inpMap.get(inputConfig.getInputType().toUpperCase()));
					}
				}
			}
			logger.debug("Enabled dashboards : "+inputConfig.getEnabledDashboard());
		}
		
		return list;
	}
	
	public InputConfig addInputConfig(ObjectNode obj) {
		logger.debug("Adding input config: "+obj.toString());
		InputConfig inputConfig = new InputConfig();
		if(obj.get("accountId") != null && obj.get("tenantId") != null) {
			Accounts ac = new Accounts();
			ac.setAccountId(obj.get("accountId").asText());
			ac.setTenantId(obj.get("tenantId").asText());
			Optional<Accounts> oa = accountsRepository.findOne(Example.of(ac));
			if(oa.isPresent()) {
				inputConfig.setAccounts(oa.get());
			}
		}
		if(obj.get("tenantId") != null) {
			inputConfig.setTenantId(obj.get("tenantId").asText());
		}
		if(obj.get("inputType") != null) {
			inputConfig.setInputType(obj.get("inputType").asText());
		}
		
		if(obj.get("status") != null) {
			inputConfig.setStatus(obj.get("status").asText().toUpperCase());
		}
		
		if(obj.get("viewJson") != null) {
			inputConfig.setViewJson(obj.get("viewJson").asText().getBytes());
		}
		
		inputConfig = inputConfigRepository.save(inputConfig);
		
		logger.info("Input config added successfully: "+inputConfig.toString());
		
		return inputConfig;
		
	}
	
	@Transactional
	public void bulkUpdateInputConfig(List<ObjectNode> list) {
		for(ObjectNode obj: list) {
			logger.debug("Updating input config: "+obj.toString());
			updateInputConfig(obj);
		}
	}
	
	public InputConfig updateInputConfig(ObjectNode obj){
		if(obj.get("id") == null) {
			logger.warn("Input config id missing. Cannot find input config for update");
			return null;
		}
		InputConfig inputConfig = inputConfigRepository.findById(obj.get("id").asLong()).orElse(null);
		if(inputConfig == null) {
			logger.warn("Input config not found. Update failed");
			return null;
		}
		
		if(obj.get("status") != null) {
			inputConfig.setStatus(obj.get("status").asText().toUpperCase());
		}
		if(obj.get("tenantId") != null) {
			inputConfig.setTenantId(obj.get("tenantId").asText());
		}
		if(obj.get("inputType") != null) {
			inputConfig.setInputType(obj.get("inputType").asText());
		}
		
		inputConfig = inputConfigRepository.save(inputConfig);
		logger.debug("Input config updated successfully : "+inputConfig.toString());
		return inputConfig;
	}
	
	public InputConfig updateInputConfig(InputConfig obj){
		if(obj.getId() == null) {
			logger.warn("Input config id missing. Cannot find input config for update");
			return null;
		}
		InputConfig inputConfig = inputConfigRepository.findById(obj.getId()).orElse(null);
		if(inputConfig == null) {
			logger.warn("Input config not found. Update failed");
			return null;
		}
		
		if(!StringUtils.isBlank(obj.getStatus())) {
			inputConfig.setStatus(obj.getStatus().toUpperCase());
		}
		if(!StringUtils.isBlank(obj.getTenantId())) {
			inputConfig.setTenantId(obj.getTenantId());
		}
		if(!StringUtils.isBlank(obj.getInputType())) {
			inputConfig.setInputType(obj.getInputType());
		}
		if(obj.getViewJson() != null && obj.getViewJson().length > 0) {
			inputConfig.setViewJson(obj.getViewJson());
		}
		inputConfig = inputConfigRepository.save(inputConfig);
		logger.debug("Input config updated successfully : "+inputConfig.toString());
		
		return inputConfig;
	}
	
}
