package com.synectiks.asset.controller;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.synectiks.asset.business.service.InputConfigService;
import com.synectiks.asset.domain.InputConfig;
import com.synectiks.asset.domain.Status;

@RestController
@RequestMapping("/api")
public class InputConfigController {
	
	private static final Logger logger = LoggerFactory.getLogger(InputConfigController.class);
	
	@Autowired
	InputConfigService inputConfigService;
	
	@GetMapping("/getInputConfig/{id}")
	public ResponseEntity<Status> getInputConfig(@PathVariable Long id) {
		logger.info("Request to get input config by id: "+id);
		try {
			InputConfig input = inputConfigService.getInputConfig(id);
			if(input != null) {
				Status st = new Status();
				st.setCode(HttpStatus.OK.value());
				st.setType("SUCCESS");
				st.setMessage("Input config found");
				st.setObject(input);
				logger.info("Input config found: "+input.toString());
				return ResponseEntity.status(HttpStatus.OK).body(st);
			}
			Status st = new Status();
			st.setCode(HttpStatus.EXPECTATION_FAILED.value());
			st.setType("ERROR");
			st.setMessage("Input config not found");
			logger.warn("Input config not found");
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(st);
		}catch(Exception e) {
			logger.error("Exception in getting an input config");
			Status st = new Status();
			st.setCode(HttpStatus.EXPECTATION_FAILED.value());
			st.setType("ERROR");
			st.setMessage("Exception in getting an input config");
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(st);
		}
		
	}
	
	@GetMapping("/searchInputConfig")
	public ResponseEntity<Status> searchInputConfig(@RequestParam Map<String, String> object) {
		logger.info("Request to get list of input configs on given filter criteria");
		Status st = new Status();
		try {
			List<InputConfig> list = inputConfigService.searchInputConfig(object);
			st.setCode(HttpStatus.OK.value());
			st.setType("SUCCESS");
			st.setMessage("Input configs found");
			st.setObject(list);
			logger.info("Input configs found");
			return ResponseEntity.status(HttpStatus.OK).body(st);
		}catch(Exception e) {
			logger.error("Exception in getting input config");
			st.setCode(HttpStatus.EXPECTATION_FAILED.value());
			st.setType("ERROR");
			st.setMessage("Exception in getting input config");
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(st);
		}
	}
	
	@PostMapping("/addInputConfig")
	public ResponseEntity<Status> addInputConfig(@RequestBody ObjectNode obj) {
		logger.info("Request to add input config");
		Status st = new Status();
		try {
			InputConfig input = inputConfigService.addInputConfig(obj);
			st.setCode(HttpStatus.OK.value());
			st.setType("SUCCESS");
			st.setMessage("Input config added successfully");
			st.setObject(input);
			logger.info("Input config added successfully");
			return ResponseEntity.status(HttpStatus.OK).body(st);
		}catch(Exception e) {
			logger.error("Adding input config failed");
			st.setCode(HttpStatus.EXPECTATION_FAILED.value());
			st.setType("ERROR");
			st.setMessage("Adding input config failed");
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(st);
		}
	}
	
	@PostMapping("/updateInputConfig")
	public ResponseEntity<Status> updateInputConfig(@RequestBody ObjectNode obj) {
		logger.info("Request to update an input config");
		Status st = new Status();
		try {
			InputConfig input = inputConfigService.updateInputConfig(obj);
			if(input != null) {
				st.setCode(HttpStatus.OK.value());
				st.setType("SUCCESS");
				st.setMessage("Input config updated successfully");
				st.setObject(input);
				logger.info("Input config updated successfully");
				return ResponseEntity.status(HttpStatus.OK).body(st);
			}
			st.setCode(HttpStatus.EXPECTATION_FAILED.value());
			st.setType("ERROR");
			st.setMessage("Input config not found for update");
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(st);
		}catch(Exception e) {
			logger.error("Updating input config failed");
			st.setCode(HttpStatus.EXPECTATION_FAILED.value());
			st.setType("ERROR");
			st.setMessage("Updating input config failed");
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(st);
		}
		
	}
}
