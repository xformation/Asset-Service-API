package com.synectiks.asset.business.appservice;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
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
import com.synectiks.asset.domain.Accounts;
import com.synectiks.asset.domain.Asset;
import com.synectiks.asset.domain.CloudAsset;
import com.synectiks.asset.domain.Organization;
import com.synectiks.asset.repository.CloudAssetRepository;

@Service
public class CloudAssetService {
	
	private static final Logger logger = LoggerFactory.getLogger(CloudAssetService.class);
	
	@Autowired
	private CloudAssetRepository cloudAssetRepository;
	
	@Autowired
	private OrganizationService organizationService;
	
	@Autowired
	private AccountsService accountsService;
	
	public List<Asset> getCloudAssets(Long accountId) {
		logger.info("Getting cloud asset by account id: "+accountId);
		Accounts acc = accountsService.getAccount(accountId);
		List<Asset> assetList = getCloudAssets(acc);
		return assetList;
	}
	
	public List<Asset> getCloudAssets(Accounts accounts) {
		logger.info("Getting cloud asset for account : "+accounts.toString());
		CloudAsset cloudAsset = new CloudAsset();
		cloudAsset.setAccountId(accounts.getAccountId());
		List<CloudAsset> list = cloudAssetRepository.findAll(Example.of(cloudAsset));
		List<Asset> assetList = new ArrayList<>();
		if(list.size() > 0) {
			for(CloudAsset ca: list) {
				logger.debug("Cloud asset:"+ca.toString());
				Asset asset = new Asset();
				BeanUtils.copyProperties(ca, asset);
				asset.setTitle(ca.getName());
				asset.setStatus(Constants.ACTIVE.equalsIgnoreCase(ca.getStatus()) ? true: false );
				
				if(!StringUtils.isBlank(accounts.getTenantId())) {
					Organization org = organizationService.getOrganization(Long.parseLong(accounts.getTenantId()));
					asset.setOrganizationName(org.getName());
					asset.setTenantId(accounts.getTenantId());
				}
				asset.setOrganizationalUnit(accounts.getOrganizationalUnit().getName());
				assetList.add(asset);
			}
		}
		return assetList;
	}
	
	public Asset getCloudAsset(Long id) {
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
			obj.setStatus(object.get("status").toUpperCase());
			isFilter = true;
		}
		
		List<CloudAsset> list = null;
		if (isFilter) {
			list = this.cloudAssetRepository.findAll(Example.of(obj), Sort.by(Direction.ASC, "name"));
		} else {
			list = this.cloudAssetRepository.findAll(Sort.by(Direction.ASC, "name"));
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
			if(obj.get("accountId") != null) {
				cloudAsset.setAccountId(obj.get("accountId").asText());
			}else {
				logger.warn("Cloud asset could not be added. Account id missing");
				return null;
			}
			if(obj.get("type") != null) {
				cloudAsset.setType(obj.get("type").asText());
			}else {
				logger.warn("Cloud asset could not be added. Cloud type (e.g. AWS, GCP etc.) missing");
				return null;
			}
			if(obj.get("name") != null) {
				cloudAsset.setName(obj.get("name").asText());
			}else {
				logger.warn("Cloud asset could not be added. Cloud element type (e.g. VPC, EC2 etc.) missing");
				return null;
			}
			if(obj.get("description") != null) {
				cloudAsset.setDescription(obj.get("description").asText());
			}
			if(obj.get("status") != null) {
				cloudAsset.setStatus(obj.get("status").asText().toUpperCase());
			}
			if(obj.get("sourceJsonRef") != null) {
				cloudAsset.setSourceJsonRef(obj.get("sourceJsonRef").asText());
			}
			
			if(obj.get("sourceJson") != null) {
				cloudAsset.setSourceJson(obj.get("sourceJson").asText().getBytes());
			}
			
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
	
	public CloudAsset saveCloudAsset(CloudAsset obj) {
		logger.info("Saving cloud asset in database");
		obj = cloudAssetRepository.save(obj);
		logger.info("Cloud asset saved successfully in database");
		return obj;
	}
	
	public List<CloudAsset> searchDiscoveredAsset(Map<String, String> object) {
		logger.info("Searching discovered asset");
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
			obj.setStatus(object.get("status").toUpperCase());
			isFilter = true;
		}
		
		List<CloudAsset> list = null;
		if (isFilter) {
			list = this.cloudAssetRepository.findAll(Example.of(obj), Sort.by(Direction.ASC, "name"));
		} else {
			list = this.cloudAssetRepository.findAll(Sort.by(Direction.ASC, "name"));
		}
		
		return list;
	}
}
