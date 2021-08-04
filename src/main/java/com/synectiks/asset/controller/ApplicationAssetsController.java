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
import com.synectiks.asset.business.service.ApplicationAssetService;
import com.synectiks.asset.domain.Asset;

@RestController
@RequestMapping("/api")
public class ApplicationAssetsController {
	
	private static final Logger logger = LoggerFactory.getLogger(ApplicationAssetsController.class);
	
	@Autowired
	ApplicationAssetService applicationAssetService;
	
	@GetMapping("/getApplicationAsset/{id}")
	public ResponseEntity<Asset> getApplicationAsset(@PathVariable Long id) {
		logger.info("Request to get application asset by id: "+id);
		Asset asset = applicationAssetService.getApplicationAsset(id);
		if(asset != null) {
			return ResponseEntity.status(HttpStatus.OK).body(asset);
		}
		return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(null);
	}
	
	@GetMapping("/searchApplicationAsset")
	public List<Asset> searchApplicationAsset(@RequestParam Map<String, String> object) {
		logger.info("Request to get application assets on given filter criteria");
		return applicationAssetService.searchApplicationAsset(object);
	}
	
	@GetMapping("/getApplicationAssetsGropuByInputType")
	public ResponseEntity<Map<String, List<Asset>>> getApplicationAssetToEnable(@RequestParam Map<String, String> object) {
		logger.info("Request to get all application assets group by input type");
		if (object.get("tenantId") == null) {
			logger.warn("User's organization id is missing");
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(null);
		}
		Map<String, List<Asset>> map = applicationAssetService.getApplicationAssetsGropuByInputType(object);
		return ResponseEntity.status(HttpStatus.OK).body(map);
	}
	
	@PostMapping("/addApplicationAsset")
	public ResponseEntity<Asset> addApplicationAsset(@RequestBody ObjectNode obj) {
		logger.info("Request to add application assets");
		Asset asset = applicationAssetService.addApplicationAsset(obj);
		if(asset != null) {
			return ResponseEntity.status(HttpStatus.OK).body(asset);
		}
		return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(null);
	}
	
	@PostMapping("/bulkAddApplicationAssets")
	public ResponseEntity<String> bulkAddApplicationAsset(@RequestBody List<ObjectNode> list) {
		logger.info("Request to add list of purchased assets");
		applicationAssetService.bulkAddApplicationAsset(list);
		return ResponseEntity.status(HttpStatus.OK).body("Assets added");
	}
	
	@PostMapping("/updateApplicationAssets")
	public ResponseEntity<String> updateApplicationAssets(@RequestBody List<ObjectNode> list) {
		logger.info("Request to update list of application assets");
		applicationAssetService.updateApplicationAsset(list);
		return ResponseEntity.status(HttpStatus.OK).body("Assets updated");
	}
	
	@PostMapping("/updateApplicationAsset")
	public ResponseEntity<String> updateApplicationAsset(@RequestBody ObjectNode obj) {
		logger.info("Request to update an application assets");
		applicationAssetService.updateApplicationAsset(obj);
		return ResponseEntity.status(HttpStatus.OK).body("Asset updated");
	}
}
