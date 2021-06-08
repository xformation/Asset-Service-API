package com.synectiks.asset.business.service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.synectiks.asset.config.Constants;
import com.synectiks.asset.domain.ApplicationAssets;
import com.synectiks.asset.domain.Asset;
import com.synectiks.asset.repository.ApplicationAssetsRepository;

@Service
public class ApplicationAssetService {
	
	private static final Logger logger = LoggerFactory.getLogger(ApplicationAssetService.class);
	
	@Autowired
	private ApplicationAssetsRepository applicationAssetsRepository;
	
	public Asset getApplicationAsset(Long id) {
		logger.info("Getting application asset by id: "+id);
		Optional<ApplicationAssets> oaa = applicationAssetsRepository.findById(id);
		if(oaa.isPresent()) {
			logger.info("Application asset: "+oaa.get().toString());
			Asset asset = new Asset();
			BeanUtils.copyProperties(oaa.get(), asset);
			asset.setTitle(oaa.get().getElementType());
			asset.setType(oaa.get().getCloudType());
			asset.setStatus(Constants.ACTIVE.equalsIgnoreCase(oaa.get().getStatus()) ? true: false );
			return asset;
		}
		logger.warn("Application asset not found");
		return null;
	}
	
	public List<Asset> searchApplicationAsset(Map<String, String> object) {
		logger.info("Searching application asset");
		ApplicationAssets obj = new ApplicationAssets();
		
		boolean isFilter = false;
		if (object.get("id") != null) {
			obj.setId(Long.parseLong(object.get("id")));
			isFilter = true;
		}
		if (object.get("tenantId") != null) {
			obj.setTenantId(object.get("tenantId"));
			isFilter = true;
		}
		if (object.get("cloudType") != null) {
			obj.setCloudType(object.get("cloudType"));
			isFilter = true;
		}
		if (object.get("elementType") != null) {
			obj.setElementType(object.get("elementType"));
			isFilter = true;
		}
		if (object.get("dashboardUuid") != null) {
			obj.setDashboardUuid(object.get("dashboardUuid"));
			isFilter = true;
		}
		if (object.get("status") != null) {
			obj.setStatus(object.get("status"));
			isFilter = true;
		}
		if (object.get("inputType") != null) {
			obj.setInputType(object.get("inputType"));
			isFilter = true;
		}
		
		List<ApplicationAssets> list = null;
		if (isFilter) {
			list = this.applicationAssetsRepository.findAll(Example.of(obj), Sort.by(Direction.ASC, "elementType"));
		} else {
			list = this.applicationAssetsRepository.findAll(Sort.by(Direction.ASC, "elementType"));
		}
		
		List<Asset> listAsset = new ArrayList<>();
		for(ApplicationAssets aa: list) {
			Asset asset = new Asset();
			BeanUtils.copyProperties(aa, asset);
			asset.setTitle(aa.getElementType());
			asset.setType(aa.getCloudType());
			asset.setStatus(Constants.ACTIVE.equalsIgnoreCase(aa.getStatus()) ? true: false );
			listAsset.add(asset);
		}

		return listAsset;
	}
	
	
	public Asset addApplicationAsset(ObjectNode obj) {
		logger.info("Adding application asset: "+obj.toString());
		try {
			ApplicationAssets appAsset = new ApplicationAssets();
			appAsset.setTenantId(obj.get("tenantId").asText());
			appAsset.setDashboardUuid(obj.get("dashboardUuid").asText());
			appAsset.setCloudType(obj.get("cloudType").asText());
			appAsset.setElementType(obj.get("elementTyp").asText());
			appAsset.setInputType(obj.get("inputTyp").asText());
			appAsset.setStatus(obj.get("status").asText());
			
		 	if (obj.get("user") != null) {
				appAsset.setCreatedBy(obj.get("user").asText());
				appAsset.setUpdatedBy(obj.get("user").asText());
			} else {
				appAsset.setCreatedBy(Constants.SYSTEM_ACCOUNT);
				appAsset.setUpdatedBy(Constants.SYSTEM_ACCOUNT);
			}
			Instant now = Instant.now();
			appAsset.setCreatedOn(now);
			appAsset.setUpdatedOn(now);
			
			appAsset = applicationAssetsRepository.save(appAsset);
			
			logger.info("Application asset added successfully: "+appAsset.toString());
			Asset asset = new Asset();
			BeanUtils.copyProperties(appAsset, asset);
			asset.setTitle(appAsset.getElementType());
			asset.setType(appAsset.getCloudType());
			asset.setStatus(Constants.ACTIVE.equalsIgnoreCase(appAsset.getStatus()) ? true: false );
			return asset;
		}catch(Exception e) {
			logger.error("Application asset could not be added. Exception: ",e);
			return null;
		}
	}
	
	public void updatePurchaseInventory(List<ObjectNode> list) {
		for(ObjectNode obj: list) {
			logger.info("Adding new asset inventory: "+obj.toString());
			addApplicationAsset(obj);
		}
	}
}
