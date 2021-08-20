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
import com.synectiks.asset.domain.Status;

@RestController
@RequestMapping("/api")
public class InputsController {
	
	private static final Logger logger = LoggerFactory.getLogger(InputsController.class);
	
	@Autowired
	InputService inputService;
	
	@GetMapping("/getInput/{id}")
	public ResponseEntity<Status> getInput(@PathVariable Long id) {
		logger.info("Request to get input by id: "+id);
		try {
			Inputs input = inputService.getInput(id);
			if(input != null) {
				Status st = new Status();
				st.setCode(HttpStatus.OK.value());
				st.setType("SUCCESS");
				st.setMessage("Input found");
				st.setObject(input);
				logger.info("Input found: "+input.toString());
				return ResponseEntity.status(HttpStatus.OK).body(st);
			}
			Status st = new Status();
			st.setCode(HttpStatus.EXPECTATION_FAILED.value());
			st.setType("ERROR");
			st.setMessage("Input not found");
			logger.warn("Input not found");
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(st);
		}catch(Exception e) {
			logger.error("Exception in getting an input");
			Status st = new Status();
			st.setCode(HttpStatus.EXPECTATION_FAILED.value());
			st.setType("ERROR");
			st.setMessage("Exception in getting an input");
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(st);
		}
	}
	
	@GetMapping("/searchInput")
	public ResponseEntity<Status> searchInput(@RequestParam Map<String, String> object) {
		logger.info("Request to get input on given filter criteria");
		Status st = new Status();
		try {
			List<Inputs> list = inputService.searchInputs(object);
			st.setCode(HttpStatus.OK.value());
			st.setType("SUCCESS");
			st.setMessage("Inputs found");
			st.setObject(list);
			logger.info("Inputs found");
			return ResponseEntity.status(HttpStatus.OK).body(st);
		}catch(Exception e) {
			logger.error("Exception in getting inputs");
			st.setCode(HttpStatus.EXPECTATION_FAILED.value());
			st.setType("ERROR");
			st.setMessage("Exception in getting inputs");
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(st);
		}
	}
	
	@PostMapping("/addInput")
	public ResponseEntity<Status> addInput(@RequestBody ObjectNode obj) {
		logger.info("Request to add input");
		Status st = new Status();
		try {
			Inputs input = inputService.addInputs(obj);
			st.setCode(HttpStatus.OK.value());
			st.setType("SUCCESS");
			st.setMessage("Input added successfully");
			st.setObject(input);
			logger.info("Input added successfully");
			return ResponseEntity.status(HttpStatus.OK).body(st);
		}catch(Exception e) {
			logger.error("Adding input failed");
			st.setCode(HttpStatus.EXPECTATION_FAILED.value());
			st.setType("ERROR");
			st.setMessage("Adding input failed");
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(st);
		}
	}
	
	@PostMapping("/updateInput")
	public ResponseEntity<Status> updateInput(@RequestBody ObjectNode obj) {
		logger.info("Request to update an input");
		Status st = new Status();
		try {
			Inputs input = inputService.updateInput(obj);
			if(input != null) {
				st.setCode(HttpStatus.OK.value());
				st.setType("SUCCESS");
				st.setMessage("Input updated successfully");
				st.setObject(input);
				logger.info("Input updated successfully");
				return ResponseEntity.status(HttpStatus.OK).body(st);
			}
			st.setCode(HttpStatus.EXPECTATION_FAILED.value());
			st.setType("ERROR");
			st.setMessage("Input not found for update");
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(st);
		}catch(Exception e) {
			logger.error("Updating input failed");
			st.setCode(HttpStatus.EXPECTATION_FAILED.value());
			st.setType("ERROR");
			st.setMessage("Updating input failed");
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(st);
		}
	}
}
