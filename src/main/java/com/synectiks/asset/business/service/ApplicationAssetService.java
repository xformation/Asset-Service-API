package com.synectiks.asset.business.service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.transaction.Transactional;

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
	
	
	public Map<String, List<Asset>> getApplicationAssetsGropuByInputType(Map<String, String> object) {
		logger.debug("Getting all application assets group by input type");
		ApplicationAssets obj = new ApplicationAssets();
		obj.setTenantId(object.get("tenantId"));
		obj.setCloudType(object.get("cloud"));
		obj.setElementType(object.get("type"));
		obj.setInputType(object.get("inputType"));
		obj.setStatus(Constants.DEACTIVE);
		
		List<ApplicationAssets> list = this.applicationAssetsRepository.findAll(Example.of(obj));
		
		Map<String, List<Asset>> assetMap = new HashMap<String, List<Asset>>();
		for(ApplicationAssets aa: list) {
			Asset asset = new Asset();
			BeanUtils.copyProperties(aa, asset);
			asset.setTitle(aa.getElementType());
			asset.setType(aa.getCloudType());
			asset.setStatus(Constants.ACTIVE.equalsIgnoreCase(aa.getStatus()) ? true: false );
			asset.setAccountId(object.get("accountId"));
			if(!assetMap.containsKey(asset.getInputType())) {
				List<Asset> listAsset = new ArrayList<>();
				listAsset.add(asset);
				logger.debug("New input type list being added. Input type : "+asset.getInputType());
				assetMap.put(asset.getInputType(), listAsset);
			}else {
				assetMap.get(asset.getInputType()).add(asset);
				logger.debug("Asset added to the list : "+asset.toString());
			}
		}
		return assetMap;
	}
	
	
	public Asset addApplicationAsset(ObjectNode obj) {
		logger.debug("Adding application asset: "+obj.toString());
		try {
			ApplicationAssets appAsset = new ApplicationAssets();
			if(obj.get("tenantId") != null) {
				appAsset.setTenantId(obj.get("tenantId").asText());
			}
			if(obj.get("dashboardUuid") != null) {
				appAsset.setDashboardUuid(obj.get("dashboardUuid").asText());
			}
			if(obj.get("fileName") != null) {
				appAsset.setFileName(obj.get("fileName").asText());
			}
			if(obj.get("cloudType") != null) {
				appAsset.setCloudType(obj.get("cloudType").asText());
			}
			if(obj.get("elementType") != null) {
				appAsset.setElementType(obj.get("elementType").asText());
			}
			if(obj.get("inputType") != null) {
				appAsset.setInputType(obj.get("inputType").asText());
			}
			if(obj.get("dashboardNature") != null) {
				appAsset.setDashboardNature(obj.get("dashboardNature").asText());
			}
			if(obj.get("status") != null) {
				appAsset.setStatus(obj.get("status").asText().toUpperCase());
			}else {
				appAsset.setStatus(Constants.ACTIVE);
			}
			
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
	
	public void bulkAddApplicationAsset(List<ObjectNode> list) {
		for(ObjectNode obj: list) {
			logger.debug("Adding new application asset to inventory: "+obj.toString());
			addApplicationAsset(obj);
		}
	}
	
	@Transactional
	public void updateApplicationAsset(List<ObjectNode> list) {
		for(ObjectNode obj: list) {
			logger.debug("Updating application asset: "+obj.toString());
			updateApplicationAsset(obj);
		}
	}
	
	public void updateApplicationAsset(ObjectNode obj){
		try {
			if(obj.get("id") != null) {
				ApplicationAssets appAsset = applicationAssetsRepository.findById(obj.get("id").asLong()).orElse(null);
				if(appAsset != null) {
					if(obj.get("status") != null) {
						appAsset.setStatus(obj.get("status").asText().toUpperCase());
					}
					if (obj.get("user") != null) {
						appAsset.setUpdatedBy(obj.get("user").asText());
					} else {
						appAsset.setUpdatedBy(Constants.SYSTEM_ACCOUNT);
					}
					Instant now = Instant.now();
					appAsset.setUpdatedOn(now);
					applicationAssetsRepository.save(appAsset);
				}
				logger.debug("Application asset updated successfully : "+appAsset.toString());
			}
		}catch(Exception e) {
			logger.warn("Due to exception application asset cannot be updated", e);
		}
	}
}
