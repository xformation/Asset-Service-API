package com.synectiks.asset.business.cacheservice;

import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.synectiks.asset.business.appservice.AccountsService;
import com.synectiks.asset.business.appservice.CloudAssetService;
import com.synectiks.asset.config.Constants;
import com.synectiks.asset.domain.Accounts;
import com.synectiks.asset.domain.AwsDiscoveredAssetCache;
import com.synectiks.asset.domain.AwsDiscoveredAssetKey;
import com.synectiks.asset.domain.CloudAsset;
import com.synectiks.aws.config.Converter;
import com.synectiks.aws.entities.vpc.XformVpc;
import com.synectiks.aws.processor.XformVpcProcessor;

@Service
public class AwsDiscoveredCacheService {
	
	private static final Logger logger = LoggerFactory.getLogger(AwsDiscoveredCacheService.class);
	
	@Autowired
	private AccountsService accountsService;
	
	@Autowired
	private CloudAssetService cloudAssetService;
	
//	@PostConstruct
//    private void postConstruct() throws Exception {
//		this.initEnableDashboardCache();
//    }
	
	public void initEnableDashboardCache() throws Exception {
		logger.info("Initializing discovered asset cache");
		Map<String, String> reqObj = new HashMap<>();
		reqObj.put("status", Constants.ACTIVE);
		List<Accounts> accountsList = accountsService.searchAccounts(reqObj);
		
		for(Accounts account: accountsList) {
			this.buildCache(account);
		}
		logger.info("Discovered assets cache initialized");
	}
	
	private void buildCache(Accounts account) throws Exception {
		
		for(String awsObj: Constants.AWS_DISCOVERED_ASSETS) {
			switch (awsObj) {
				case Constants.VPC:
					XformVpcProcessor processor = new XformVpcProcessor(account.getAccessKey(), account.getSecretKey(), account.getRegion());
					List<XformVpc> vpcList = processor.getXformObject();
					AwsDiscoveredAssetKey key = new AwsDiscoveredAssetKey(account.getAccountId(), Constants.CLOUD_TYPE_AWS);
					if(Constants.AWS_DISCOVERED_ASSET_CACHE.containsKey(key)) {
						AwsDiscoveredAssetCache cache = Constants.AWS_DISCOVERED_ASSET_CACHE.get(key);
						cache.setVpcList(vpcList);
					}else {
						AwsDiscoveredAssetCache cache = new AwsDiscoveredAssetCache();
						cache.setAccountId(account.getAccountId());
						cache.setVpcList(vpcList);
						Constants.AWS_DISCOVERED_ASSET_CACHE.put(key, cache);
					}
					this.saveCloudAsset(awsObj, account, Converter.toPrettyJsonString(vpcList, List.class));
					break;
				default:
					break;
			}
		}
	}
	
	private void saveCloudAsset(String awsObj, Accounts account, String sourceJson){
		logger.info("Updating cloud assets in database");
		Instant now = Instant.now();
		CloudAsset cloudAsset = null;
		Map<String, String> object = new HashMap<>();
		object.put("accountId", account.getAccountId());
		object.put("type", Constants.CLOUD_TYPE_AWS);
		object.put("name", awsObj);
		object.put("status", Constants.ACTIVE.toUpperCase());
		
		List<CloudAsset> caList = cloudAssetService.searchDiscoveredAsset(object);
		if(caList.size() > 0) {
			cloudAsset = caList.get(0);
			cloudAsset.setUpdatedBy(Constants.SYSTEM_ACCOUNT);
			cloudAsset.setUpdatedOn(now);
		}else {
			cloudAsset = new CloudAsset();
			cloudAsset.setAccountId(account.getAccountId());
			cloudAsset.setType(Constants.CLOUD_TYPE_AWS);
			cloudAsset.setName(awsObj);
			cloudAsset.setStatus(Constants.ACTIVE.toUpperCase());
			cloudAsset.setDescription(Constants.CLOUD_TYPE_AWS + " "+awsObj);
			cloudAsset.setCreatedBy(Constants.SYSTEM_ACCOUNT);
			cloudAsset.setUpdatedBy(Constants.SYSTEM_ACCOUNT);
			cloudAsset.setCreatedOn(now);
			cloudAsset.setUpdatedOn(now);
		}
		cloudAsset.setSourceJson(sourceJson.getBytes());
		cloudAsset = cloudAssetService.saveCloudAsset(cloudAsset);
		logger.info("Cloud assets updated successfully in database");
	}
}
