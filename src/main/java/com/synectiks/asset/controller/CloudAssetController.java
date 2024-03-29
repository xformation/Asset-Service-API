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
import com.synectiks.asset.business.appservice.CloudAssetService;
import com.synectiks.asset.domain.Asset;
import com.synectiks.asset.domain.Status;

@RestController
@RequestMapping("/api")
public class CloudAssetController {
	
	private static final Logger logger = LoggerFactory.getLogger(CloudAssetController.class);
	
	@Autowired
	private CloudAssetService cloudAssetService;
	
	@GetMapping("/getDiscoveredAsset/{accountId}")
	public ResponseEntity<List<Asset>> getCloudAssetByAccountId(@PathVariable Long accountId) {
		logger.info("Request to get cloud asset by account id: "+accountId);
		List<Asset> asset = cloudAssetService.getCloudAssets(accountId);
		if(asset != null) {
			return ResponseEntity.status(HttpStatus.OK).body(asset);
		}
		return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(null);
	}
	
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
	public ResponseEntity<Status> addCloudAsset(@RequestBody ObjectNode obj) {
		logger.info("Request to add cloud assets");
		Status st = new Status();
		try {
			Asset asset = cloudAssetService.addCloudAsset(obj);
			if(asset == null) {
				st.setCode(HttpStatus.EXPECTATION_FAILED.value());
				st.setType("ERROR");
				st.setMessage("Adding cloud asset failed. Mandatory fields (Account id, cloud type (AWS, GCP etc.) or element type (VPC, EC2 etc.) ) missing");
				return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(st);
			}
			st.setCode(HttpStatus.OK.value());
			st.setType("SUCCESS");
			st.setMessage("Cloud asset added successfully");
			st.setObject(asset);
			logger.info("Cloud asset added successfully");
			return ResponseEntity.status(HttpStatus.OK).body(st);
		}catch(Exception e) {
			logger.error("Adding cloud asset failed", e);
			st.setCode(HttpStatus.EXPECTATION_FAILED.value());
			st.setType("ERROR");
			st.setMessage("Adding cloud asset failed");
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(st);
		}
		
	}
	
}
