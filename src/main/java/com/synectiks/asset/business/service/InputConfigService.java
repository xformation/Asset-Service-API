package com.synectiks.asset.business.service;

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
import com.synectiks.asset.domain.Accounts;
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
			obj.setStatus(object.get("status"));
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
		return list;
	}
	
	public InputConfig addInputConfig(ObjectNode obj) {
		logger.debug("Adding input config: "+obj.toString());
		InputConfig inputs = new InputConfig();
		if(obj.get("accountId") != null && obj.get("tenantId") != null) {
			Accounts ac = new Accounts();
			ac.setAccountId(obj.get("accountId").asText());
			ac.setTenantId(obj.get("tenantId").asText());
			Optional<Accounts> oa = accountsRepository.findOne(Example.of(ac));
			if(oa.isPresent()) {
				inputs.setAccounts(oa.get());
			}
		}
		if(obj.get("tenantId") != null) {
			inputs.setTenantId(obj.get("tenantId").asText());
		}
		if(obj.get("inputType") != null) {
			inputs.setInputType(obj.get("inputType").asText());
		}
		
		if(obj.get("status") != null) {
			inputs.setStatus(obj.get("status").asText().toUpperCase());
		}
		
		inputs = inputConfigRepository.save(inputs);
		
		logger.info("Input config added successfully: "+inputs.toString());
		
		return inputs;
		
	}
	
	@Transactional
	public void updateInput(List<ObjectNode> list) {
		for(ObjectNode obj: list) {
			logger.debug("Updating input config: "+obj.toString());
			updateInputConfig(obj);
		}
	}
	
	public InputConfig updateInputConfig(ObjectNode obj){
		InputConfig inputs = null;
		if(obj.get("id") != null) {
			inputs = inputConfigRepository.findById(obj.get("id").asLong()).orElse(null);
			if(inputs != null) {
				if(obj.get("status") != null) {
					inputs.setStatus(obj.get("status").asText().toUpperCase());
				}
			}
			logger.debug("Input config updated successfully : "+inputs.toString());
		}
		return inputs;
	}
}
