package com.synectiks.asset.business.service;

import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.synectiks.asset.config.Constants;
import com.synectiks.asset.domain.Inputs;
import com.synectiks.asset.repository.InputsRepository;

@Service
public class InputService {
	
	private static final Logger logger = LoggerFactory.getLogger(InputService.class);
	
	@Autowired
	private InputsRepository inputsRepository;
	
	public Inputs getInput(Long id) {
		logger.info("Getting input by id: "+id);
		Optional<Inputs> oin = inputsRepository.findById(id);
		if(oin.isPresent()) {
			logger.info("Input: "+oin.get().toString());
			return oin.get();
		}
		logger.warn("Input not found");
		return null;
	}
	
	public List<Inputs> searchInputs(Map<String, String> object) {
		logger.info("Searching Input");
		Inputs obj = new Inputs();
		
		boolean isFilter = false;
		if (object.get("id") != null) {
			obj.setId(Long.parseLong(object.get("id")));
			isFilter = true;
		}
		if (object.get("accountId") != null) {
			obj.setAccountId(object.get("accountId"));
			isFilter = true;
		}
		if (object.get("tenantId") != null) {
			obj.setTenantId(object.get("tenantId"));
			isFilter = true;
		}
		if (object.get("inputSource") != null) {
			obj.setInputSource(object.get("inputSource"));
			isFilter = true;
		}
		if (object.get("name") != null) {
			obj.setName(object.get("name"));
			isFilter = true;
		}
		if (object.get("description") != null) {
			obj.setDescription(object.get("description"));
			isFilter = true;
		}
		if (object.get("status") != null) {
			obj.setStatus(object.get("status"));
			isFilter = true;
		}
		if (object.get("refUrl") != null) {
			obj.setRefUrl(object.get("refUrl"));
			isFilter = true;
		}
		
		List<Inputs> list = null;
		if (isFilter) {
			list = this.inputsRepository.findAll(Example.of(obj), Sort.by(Direction.ASC, "name"));
		} else {
			list = this.inputsRepository.findAll(Sort.by(Direction.ASC, "name"));
		}
		return list;
	}
	
	public Inputs addInputs(ObjectNode obj) {
		logger.debug("Adding input: "+obj.toString());
		try {
			Inputs inputs = new Inputs();
			if(obj.get("accountId") != null) {
				inputs.setAccountId(obj.get("accountId").asText());
			}
			if(obj.get("tenantId") != null) {
				inputs.setTenantId(obj.get("tenantId").asText());
			}
			
			if(obj.get("inputSource") != null) {
				inputs.setInputSource(obj.get("inputSource").asText());
			}
			if(obj.get("name") != null) {
				inputs.setName(obj.get("name").asText());
			}
			if(obj.get("description") != null) {
				inputs.setDescription(obj.get("description").asText());
			}
			if(obj.get("status") != null) {
				inputs.setStatus(obj.get("status").asText().toUpperCase());
			}
			if(obj.get("refUrl") != null) {
				inputs.setRefUrl(obj.get("refUrl").asText());
			}
			
		 	if (obj.get("user") != null) {
				inputs.setCreatedBy(obj.get("user").asText());
				inputs.setUpdatedBy(obj.get("user").asText());
			} else {
				inputs.setCreatedBy(Constants.SYSTEM_ACCOUNT);
				inputs.setUpdatedBy(Constants.SYSTEM_ACCOUNT);
			}
			Instant now = Instant.now();
			inputs.setCreatedOn(now);
			inputs.setUpdatedOn(now);
			
			inputs = inputsRepository.save(inputs);
			
			logger.info("Input added successfully: "+inputs.toString());
			
			return inputs;
		}catch(Exception e) {
			logger.error("Input could not be added. Exception: ",e);
			return null;
		}
	}
	
	@Transactional
	public void updateInput(List<ObjectNode> list) {
		for(ObjectNode obj: list) {
			logger.debug("Updating input: "+obj.toString());
			updateInput(obj);
		}
	}
	
	public void updateInput(ObjectNode obj){
		try {
			if(obj.get("id") != null) {
				Inputs inputs = inputsRepository.findById(obj.get("id").asLong()).orElse(null);
				if(inputs != null) {
					if(obj.get("status") != null) {
						inputs.setStatus(obj.get("status").asText().toUpperCase());
					}
					if (obj.get("user") != null) {
						inputs.setUpdatedBy(obj.get("user").asText());
					} else {
						inputs.setUpdatedBy(Constants.SYSTEM_ACCOUNT);
					}
					Instant now = Instant.now();
					inputs.setUpdatedOn(now);
					inputsRepository.save(inputs);
				}
				logger.debug("Input updated successfully : "+inputs.toString());
			}
		}catch(Exception e) {
			logger.warn("Due to exception input cannot be updated", e);
		}
	}
}
