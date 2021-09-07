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
import com.synectiks.asset.domain.Dashboard;
import com.synectiks.asset.domain.Status;

@RestController
@RequestMapping("/api")
public class ApplicationAssetsController {
	
	private static final Logger logger = LoggerFactory.getLogger(ApplicationAssetsController.class);
	
	@Autowired
	ApplicationAssetService applicationAssetService;
	
	@GetMapping("/getApplicationAsset/{id}")
	public ResponseEntity<Status> getApplicationAsset(@PathVariable Long id) {
		logger.info("Request to get application asset by id: "+id);
		try {
			Asset asset = applicationAssetService.getApplicationAsset(id);
			if(asset != null) {
				Status st = new Status();
				st.setCode(HttpStatus.OK.value());
				st.setType("SUCCESS");
				st.setMessage("Asset found");
				st.setObject(asset);
				logger.info("Asset found: "+asset.toString());
				return ResponseEntity.status(HttpStatus.OK).body(st);
			}
			Status st = new Status();
			st.setCode(HttpStatus.EXPECTATION_FAILED.value());
			st.setType("ERROR");
			st.setMessage("Asset not found");
			logger.warn("Asset not found");
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(st);
		}catch(Exception e) {
			logger.error("Exception in getting an asset. Exception : ",e );
			Status st = new Status();
			st.setCode(HttpStatus.EXPECTATION_FAILED.value());
			st.setType("ERROR");
			st.setMessage("Exception in getting an asset");
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(st);
		}
	}
	
	@GetMapping("/searchApplicationAsset")
	public ResponseEntity<Status> searchApplicationAsset(@RequestParam Map<String, String> object) {
		logger.info("Request to get application assets on given filter criteria");
		Status st = new Status();
		try {
			List<Asset> list = applicationAssetService.searchApplicationAsset(object);
			st.setCode(HttpStatus.OK.value());
			st.setType("SUCCESS");
			st.setMessage("Assets found");
			st.setObject(list);
			logger.info("Assets found");
			return ResponseEntity.status(HttpStatus.OK).body(st);
		}catch(Exception e) {
			logger.error("Exception in getting assets. Exception : ",e );
			st.setCode(HttpStatus.EXPECTATION_FAILED.value());
			st.setType("ERROR");
			st.setMessage("Exception in getting assets");
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(st);
		}
	}
	
	@GetMapping("/getApplicationAssetsGropuByInputType")
	public ResponseEntity<Status> getApplicationAssetToEnable(@RequestParam Map<String, String> object) {
		logger.info("Request to get all application assets group by input type");
		try {
			if (object.get("tenantId") == null) {
				logger.warn("User's organization id is missing");
				Status st = new Status();
				st.setCode(HttpStatus.EXPECTATION_FAILED.value());
				st.setType("ERROR");
				st.setMessage("User's organization id is missing");
				logger.warn("User's organization id is missing. Cannot get the assets");
				return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(st);
			}
			Map<String, List<Asset>> map = applicationAssetService.getApplicationAssetsGropuByInputType(object);
			Status st = new Status();
			st.setCode(HttpStatus.OK.value());
			st.setType("SUCCESS");
			st.setMessage("Assets found");
			st.setObject(map);
			logger.info("Assets found");
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(st);
		}catch(Exception e) {
			logger.error("Exception in getting assets. Exception : ",e );
			Status st = new Status();
			st.setCode(HttpStatus.EXPECTATION_FAILED.value());
			st.setType("ERROR");
			st.setMessage("Exception in getting assets");
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(st);
		}
	}
	
	@PostMapping("/addApplicationAsset")
	public ResponseEntity<Status> addApplicationAsset(@RequestBody ObjectNode obj) {
		logger.info("Request to add application assets");
		Status st = new Status();
		try {
			Asset asset = applicationAssetService.addApplicationAsset(obj);
			st.setCode(HttpStatus.OK.value());
			st.setType("SUCCESS");
			st.setMessage("Assets added successfully");
			st.setObject(asset);
			logger.info("Assets added successfully");
			return ResponseEntity.status(HttpStatus.OK).body(st);
		}catch(Exception e) {
			logger.error("Adding asset failed. Exception : ",e );
			st.setCode(HttpStatus.EXPECTATION_FAILED.value());
			st.setType("ERROR");
			st.setMessage("Adding asset failed");
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(st);
		}
		
	}
	
	@PostMapping("/bulkAddApplicationAssets")
	public ResponseEntity<Status> bulkAddApplicationAsset(@RequestBody List<ObjectNode> list) {
		logger.info("Request to add list of purchased assets");
		Status st = new Status();
		try {
			applicationAssetService.bulkAddApplicationAsset(list);
			st.setCode(HttpStatus.OK.value());
			st.setType("SUCCESS");
			st.setMessage("All the assets added successfully");
			st.setObject("All the assets added successfully");
			logger.info("All the assets added successfully");
			return ResponseEntity.status(HttpStatus.OK).body(st);
		}catch(Exception e) {
			logger.error("Bulk additions of assets failed. Exception : ",e );
			st.setCode(HttpStatus.EXPECTATION_FAILED.value());
			st.setType("ERROR");
			st.setMessage("Bulk additions of assets failed");
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(st);
		}
	}
	
	@PostMapping("/bulkUpdateApplicationAssets")
	public ResponseEntity<Status> bulkUpdateApplicationAssets(@RequestBody ObjectNode obj) {
		logger.info("Request to update list of application assets");
		Status st = new Status();
		try {
			boolean enableInput = obj.get("enableInput").asBoolean();
			applicationAssetService.bulkUpdateApplicationAsset(obj, enableInput);
			st.setCode(HttpStatus.OK.value());
			st.setType("SUCCESS");
			st.setMessage("All the assets updated successfully");
			st.setObject("All the assets updated successfully");
			logger.info("All the assets updated successfully");
			return ResponseEntity.status(HttpStatus.OK).body(st);
		}catch(Exception e) {
			logger.error("Bulk update of assets failed. Exception : ",e );
			st.setCode(HttpStatus.EXPECTATION_FAILED.value());
			st.setType("ERROR");
			st.setMessage("Bulk update of assets failed");
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(st);
		}
	}
	
	@PostMapping("/updateApplicationAsset")
	public ResponseEntity<Status> updateSingleApplicationAsset(@RequestBody ObjectNode obj) {
		logger.info("Request to update an application assets");
		Status st = new Status();
		try {
			Asset asset = applicationAssetService.updateApplicationAsset(obj);
			if(asset == null) {
				logger.warn("Asset not found. Could not be updated");
				st.setCode(HttpStatus.EXPECTATION_FAILED.value());
				st.setType("ERROR");
				st.setMessage("Asset not found");
				return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(st);
			}
			st.setCode(HttpStatus.OK.value());
			st.setType("SUCCESS");
			st.setMessage("Asset updated successfully");
			st.setObject(asset);
			logger.info("Asset updated successfully");
			return ResponseEntity.status(HttpStatus.OK).body(st);
		}catch(Exception e) {
			logger.error("Updating asset failed. Exception: ", e);
			st.setCode(HttpStatus.EXPECTATION_FAILED.value());
			st.setType("ERROR");
			st.setMessage("Updating asset failed");
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(st);
		}
	}
	
	@GetMapping("/previewDashboard")
	public ResponseEntity<Status> PreviewDashboard(@RequestParam Map<String, String> object) {
		logger.info("Request to get dashboard json");
		Status st = new Status();
		try {
			Dashboard dashboard = applicationAssetService.getDashboardFromAwsS3(object);
			st.setCode(HttpStatus.OK.value());
			st.setType("SUCCESS");
			st.setMessage("Dashboard found");
			st.setObject(dashboard);
			logger.info("Dashboard json found");
			return ResponseEntity.status(HttpStatus.OK).body(st);
		}catch(Exception e) {
			logger.error("Exception in getting dashboard json: ", e);
			st.setCode(HttpStatus.EXPECTATION_FAILED.value());
			st.setType("ERROR");
			st.setMessage("Exception in getting dashboard json");
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(st);
		}
	}
}
