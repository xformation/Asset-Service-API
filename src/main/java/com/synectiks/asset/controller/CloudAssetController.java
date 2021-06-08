package com.synectiks.asset.controller;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
import com.synectiks.asset.business.service.CloudAssetService;
import com.synectiks.asset.domain.Asset;
import com.synectiks.asset.repository.AccountsRepository;
import com.synectiks.asset.repository.CloudAssetRepository;

@RestController
@RequestMapping("/api")
public class CloudAssetController {
	
	private static final Logger logger = LoggerFactory.getLogger(CloudAssetController.class);
	private static final String ENTITY_NAME = "CloudAsset";
	
	@Value("${jhipster.clientApp.name}")
	private String applicationName;
	
	@Autowired
	private AccountsRepository accountsRepository;
	
	@Autowired
	private CloudAssetRepository cloudAssetRepository;
	
	@Autowired
	CloudAssetService cloudAssetService;
	
	@GetMapping("/getCloudAsset/{id}")
	public ResponseEntity<Asset> getCloudAsset(@PathVariable Long id) {
		logger.info("Request to get cloud asset by id: "+id);
		Asset asset = cloudAssetService.getCloudAsset(id);
		if(asset != null) {
			return ResponseEntity.status(HttpStatus.OK).body(asset);
		}
		return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(null);
	}
	
	@GetMapping("/searchCloudAsset")
	public List<Asset> searchCloudAsset(@RequestParam Map<String, String> object) {
		logger.info("Request to get cloud assets on given filter criteria");
		return cloudAssetService.searchCloudAsset(object);
	}
	
	
	@PostMapping("/addCloudAsset")
	public ResponseEntity<Asset> addCloudAsset(@RequestBody ObjectNode obj) {
		logger.info("Request to add cloud assets");
		Asset asset = cloudAssetService.addCloudAsset(obj);
		if(asset != null) {
			return ResponseEntity.status(HttpStatus.OK).body(asset);
		}
		return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(null);
	}
	
}
