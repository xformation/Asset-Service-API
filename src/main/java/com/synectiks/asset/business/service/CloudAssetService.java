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
import org.springframework.web.bind.annotation.RequestParam;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.synectiks.asset.config.Constants;
import com.synectiks.asset.domain.Asset;
import com.synectiks.asset.domain.CloudAsset;
import com.synectiks.asset.repository.AccountsRepository;
import com.synectiks.asset.repository.CloudAssetRepository;

@Service
public class CloudAssetService {
	
	private static final Logger logger = LoggerFactory.getLogger(CloudAssetService.class);
	
	@Autowired
	private AccountsRepository accountsRepository;
	
	@Autowired
	private CloudAssetRepository cloudAssetRepository;
	
	public Asset getCloudAsset(@RequestParam Long id) {
		logger.info("Getting cloud asset by id: "+id);
		Optional<CloudAsset> oca = cloudAssetRepository.findById(id);
		if(oca.isPresent()) {
			logger.info("Cloud asset:"+oca.get().toString());
			Asset asset = new Asset();
			BeanUtils.copyProperties(oca.get(), asset);
			asset.setTitle(oca.get().getName());
			asset.setStatus(Constants.ACTIVE.equalsIgnoreCase(oca.get().getStatus()) ? true: false );
			return asset;
		}
		logger.warn("Cloud asset not found");
		return null;
	}
	
	public List<Asset> searchCloudAsset(Map<String, String> object) {
		logger.info("Searching cloud asset");
		CloudAsset obj = new CloudAsset();
		
		boolean isFilter = false;
		if (object.get("id") != null) {
			obj.setId(Long.parseLong(object.get("id")));
			isFilter = true;
		}
		if (object.get("accountId") != null) {
			obj.setAccountId(object.get("accountId"));
			isFilter = true;
		}
		if (object.get("type") != null) {
			obj.setType(object.get("type"));
			isFilter = true;
		}
		if (object.get("name") != null) {
			obj.setName(object.get("name"));
			isFilter = true;
		}
		if (object.get("description") != null) {
			obj.setDescription(object.get("description"));
			isFilter = true;
		}
		if (object.get("status") != null) {
			obj.setStatus(object.get("status"));
			isFilter = true;
		}
		
		List<CloudAsset> list = null;
		if (isFilter) {
			list = this.cloudAssetRepository.findAll(Example.of(obj), Sort.by(Direction.DESC, "name"));
		} else {
			list = this.cloudAssetRepository.findAll(Sort.by(Direction.DESC, "name"));
		}
		
		List<Asset> listAsset = new ArrayList<>();
		for(CloudAsset ca: list) {
			Asset asset = new Asset();
			BeanUtils.copyProperties(ca, asset);
			asset.setTitle(ca.getName());
			asset.setStatus(Constants.ACTIVE.equalsIgnoreCase(ca.getStatus()) ? true: false );
			listAsset.add(asset);
		}

		return listAsset;
	}
	
	
	public Asset addCloudAsset(ObjectNode obj) {
		try {
			CloudAsset cloudAsset = new CloudAsset();
			cloudAsset.setAccountId(obj.get("accountId").asText());
			cloudAsset.setType(obj.get("type").asText());
			cloudAsset.setName(obj.get("name").asText());
			cloudAsset.setDescription(obj.get("description").asText());
			cloudAsset.setStatus(obj.get("status").asText());
			cloudAsset.setSourceJsonRef(obj.get("sourceJsonRef").asText());
			
		 	if (obj.get("user") != null) {
				cloudAsset.setCreatedBy(obj.get("user").asText());
				cloudAsset.setUpdatedBy(obj.get("user").asText());
			} else {
				cloudAsset.setCreatedBy(Constants.SYSTEM_ACCOUNT);
				cloudAsset.setUpdatedBy(Constants.SYSTEM_ACCOUNT);
			}
			Instant now = Instant.now();
			cloudAsset.setCreatedOn(now);
			cloudAsset.setUpdatedOn(now);
			cloudAsset = cloudAssetRepository.save(cloudAsset);
			logger.info("Cloud asset record added successfully: Cloud asset: "+cloudAsset.toString());
			Asset asset = new Asset();
			BeanUtils.copyProperties(cloudAsset, asset);
			asset.setTitle(cloudAsset.getName());
			asset.setStatus(Constants.ACTIVE.equalsIgnoreCase(cloudAsset.getStatus()) ? true: false );
			return asset;
		}catch(Exception e) {
			logger.error("Cloud asset could not be added. Exception: ",e);
			return null;
		}
		
	}
	
	
}
