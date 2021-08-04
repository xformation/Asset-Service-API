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
import com.synectiks.asset.business.service.InputService;
import com.synectiks.asset.domain.Inputs;

@RestController
@RequestMapping("/api")
public class InputsController {
	
	private static final Logger logger = LoggerFactory.getLogger(InputsController.class);
	
	@Autowired
	InputService inputService;
	
	@GetMapping("/getInput/{id}")
	public ResponseEntity<Inputs> getInput(@PathVariable Long id) {
		logger.info("Request to get input by id: "+id);
		Inputs input = inputService.getInput(id);
		if(input != null) {
			return ResponseEntity.status(HttpStatus.OK).body(input);
		}
		return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(null);
	}
	
	@GetMapping("/searchInput")
	public List<Inputs> searchInput(@RequestParam Map<String, String> object) {
		logger.info("Request to get input on given filter criteria");
		return inputService.searchInputs(object);
	}
	
	@PostMapping("/addInput")
	public ResponseEntity<Inputs> addInput(@RequestBody ObjectNode obj) {
		logger.info("Request to add input");
		Inputs input = inputService.addInputs(obj);
		if(input != null) {
			return ResponseEntity.status(HttpStatus.OK).body(input);
		}
		return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(null);
	}
	
	@PostMapping("/updateInput")
	public ResponseEntity<String> updateInput(@RequestBody ObjectNode obj) {
		logger.info("Request to update an input");
		inputService.updateInput(obj);
		return ResponseEntity.status(HttpStatus.OK).body("Input updated");
	}
}
