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

@RestController
@RequestMapping("/api")
public class InputConfigController {
	
	private static final Logger logger = LoggerFactory.getLogger(InputConfigController.class);
	
	@Autowired
	InputConfigService inputConfigService;
	
	@GetMapping("/getInputConfig/{id}")
	public ResponseEntity<InputConfig> getInputConfig(@PathVariable Long id) {
		logger.info("Request to get input config by id: "+id);
		InputConfig input = inputConfigService.getInputConfig(id);
		if(input != null) {
			return ResponseEntity.status(HttpStatus.OK).body(input);
		}
		return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(null);
	}
	
	@GetMapping("/searchInputConfig")
	public List<InputConfig> searchInputConfig(@RequestParam Map<String, String> object) {
		logger.info("Request to get input config on given filter criteria");
		return inputConfigService.searchInputConfig(object);
	}
	
	@PostMapping("/addInputConfig")
	public ResponseEntity<InputConfig> addInputConfig(@RequestBody ObjectNode obj) {
		logger.info("Request to add input config");
		InputConfig input = inputConfigService.addInputConfig(obj);
		if(input != null) {
			return ResponseEntity.status(HttpStatus.OK).body(input);
		}
		return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(null);
	}
	
	@PostMapping("/updateInputConfig")
	public ResponseEntity<String> updateInputConfig(@RequestBody ObjectNode obj) {
		logger.info("Request to update an input config");
		inputConfigService.updateInputConfig(obj);
		return ResponseEntity.status(HttpStatus.OK).body("Input config updated");
	}
}
