package com.synectiks.asset.business.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.transaction.Transactional;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.S3Object;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.synectiks.asset.aws.AwsUtils;
import com.synectiks.asset.config.Constants;
import com.synectiks.asset.domain.Accounts;
import com.synectiks.asset.domain.ApplicationAssets;
import com.synectiks.asset.domain.Asset;
import com.synectiks.asset.domain.Dashboard;
import com.synectiks.asset.domain.DashboardMeta;
import com.synectiks.asset.domain.InputConfig;
import com.synectiks.asset.repository.ApplicationAssetsRepository;

@Service
public class ApplicationAssetService {
	
	private static final Logger logger = LoggerFactory.getLogger(ApplicationAssetService.class);
	
	@Autowired
	private ApplicationAssetsRepository applicationAssetsRepository;
	
	@Autowired
	AccountsService accountsService;

	@Autowired
	InputConfigService inputConfigService;
	
	public Asset getApplicationAsset(Long id) {
		logger.info("Getting application asset by id: "+id);
		Optional<ApplicationAssets> oaa = applicationAssetsRepository.findById(id);
		if(oaa.isPresent()) {
			logger.info("Application asset: "+oaa.get().toString());
			Asset asset = new Asset();
			BeanUtils.copyProperties(oaa.get(), asset);
			asset.setTitle(oaa.get().getElementType());
			asset.setType(oaa.get().getCloudType());
			asset.setStatus(Constants.STATUS_ENABLED.equalsIgnoreCase(oaa.get().getStatus()) ? true: false );
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
			obj.setStatus(object.get("status").toUpperCase());
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
			asset.setStatus(Constants.STATUS_ENABLED.equalsIgnoreCase(aa.getStatus()) ? true: false );
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
//		obj.setStatus(Constants.STATUS_READY_TO_ENABLE);
		
		List<ApplicationAssets> list = this.applicationAssetsRepository.findAll(Example.of(obj));
		
		Map<String, List<Asset>> assetMap = new HashMap<String, List<Asset>>();
		for(ApplicationAssets aa: list) {
			Asset asset = new Asset();
			BeanUtils.copyProperties(aa, asset);
			asset.setTitle(aa.getElementType());
			asset.setType(aa.getCloudType());
			asset.setStatus(Constants.STATUS_ENABLED.equalsIgnoreCase(aa.getStatus()) ? true: false );
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
		if(obj.get("elementSubType") != null) {
			appAsset.setElementSubType(obj.get("elementSubType").asText());
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
			appAsset.setStatus(Constants.STATUS_READY_TO_ENABLE);
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
		asset.setStatus(Constants.STATUS_READY_TO_ENABLE.equalsIgnoreCase(appAsset.getStatus()) ? true: false );
		return asset;
		
	}
	
	@Transactional
	public void bulkAddApplicationAsset(List<ObjectNode> list) {
		for(ObjectNode obj: list) {
			logger.debug("Adding new application asset to inventory: "+obj.toString());
			addApplicationAsset(obj);
		}
	}
	
	@Transactional
	public void bulkUpdateApplicationAsset(ObjectNode requestObj, boolean enableInput) throws IOException {
		logger.info("Updating application assets");
		JsonNode dashboardList = requestObj.get("dashboardList");
		if(dashboardList.isArray()) {
			Iterator<JsonNode> itr = dashboardList.iterator();
			while(itr.hasNext()) {
				updateApplicationAsset(itr.next());
			}
		}
		logger.info("Application assets updated successfully");		
		
		if(enableInput) {
			String accountId = requestObj.get("accountId").asText();
			String tenantId = requestObj.get("tenantId").asText();
			String inputType = requestObj.get("inputType").asText();
			String status = requestObj.get("status").asText();
			Map<String, String> objMap = new HashMap<String, String>();
			objMap.put("accountId", accountId);
			objMap.put("tenantId", tenantId);
			objMap.put("inputType", inputType);
			objMap.put("status", status);
			
			List<InputConfig> inpConfigList = inputConfigService.searchInputConfig(objMap);
			
			ObjectMapper mapper = new ObjectMapper();
			
			if(inpConfigList.size() == 0) {
				
				logger.info("enabling input " + inputType);
				
				ArrayNode arrayNode = getJsonArray(mapper, dashboardList.iterator());
				String json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(arrayNode);
				requestObj.put("viewJson", json);
				logger.debug("Dashboard json array as string : "+json);
				logger.info("Updating input config with enabled dashboards");
					
				inputConfigService.addInputConfig(requestObj);
				logger.info("Input " + inputType+" enabled successfully");
				
			}else {
				logger.info("Input " + inputType+ " already enabled. Updating view json");
				InputConfig inputConfig = inpConfigList.get(0);
				ArrayNode existingJsonArray = (ArrayNode)mapper.readTree(inputConfig.getViewJson());
				
				if(dashboardList.isArray()) {
					ArrayNode newJsonarray = getJsonArray(mapper, dashboardList.iterator());
					
					if(existingJsonArray.isArray()) {
				         for(JsonNode jsonNode : existingJsonArray) {
				        	 logger.debug("Existing dashboard json : "+jsonNode);
				        	 newJsonarray.add(jsonNode);
				         }
				    }
					
					String newJson = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(newJsonarray);
					
					logger.debug("Updated dashboard json Array : "+newJson);
					inputConfig.setViewJson(newJson.getBytes());
					inputConfig = inputConfigService.updateInputConfig(inputConfig);
					
				}
				logger.info("View json updated successfully");	
			}
		}
	}

	private ArrayNode getJsonArray(ObjectMapper mapper, Iterator<JsonNode> itr) {
		ArrayNode arrayNode = mapper.createArrayNode();
		while(itr.hasNext()) {
			JsonNode jsonObj = itr.next();
			JsonNode appAsset = jsonObj.get("appAsset");
			JsonNode grafanaAsset = jsonObj.get("grafanaAsset");
			
			ObjectNode dashboard = (ObjectNode)appAsset.get("Dashboard");
			dashboard.put("Uid", grafanaAsset.get("uid").asText());
			dashboard.put("Slug", grafanaAsset.get("slug").asText());
			dashboard.put("Id", grafanaAsset.get("id").asLong());
			dashboard.put("Url", grafanaAsset.get("url").asLong());
			dashboard.put("Version", grafanaAsset.get("version").asInt());
			
			arrayNode.add(dashboard);
			
			logger.debug("Dashboard : "+dashboard.toString());
		}
		return arrayNode;
	}
	
	public Asset updateApplicationAsset(JsonNode obj){
		Asset asset = null;
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
				appAsset = applicationAssetsRepository.save(appAsset);
				
				asset = new Asset();
				BeanUtils.copyProperties(appAsset, asset);
				asset.setTitle(appAsset.getElementType());
				asset.setType(appAsset.getCloudType());
//				asset.setStatus(Constants.STATUS_READY_TO_ENABLE.equalsIgnoreCase(appAsset.getStatus()) ? true: false );
			}
			
			logger.debug("Application asset updated successfully : "+appAsset.toString());
		}
		return asset;
	}
	
	public Dashboard getDashboardFromAwsS3(Map<String, String> object) throws IOException {
		logger.info("Downloading dashboard json from AWS");
		
		if (StringUtils.isBlank(object.get("cloudType")) || StringUtils.isBlank(object.get("elementType")) ||
				StringUtils.isBlank(object.get("tenantId")) || StringUtils.isBlank(object.get("accountId")) ||
				StringUtils.isBlank(object.get("inputType")) || StringUtils.isBlank(object.get("fileName")) ||
				StringUtils.isBlank(object.get("dataSource"))) {
			logger.error("Mandatory fields missing");
			return null;
		}
		
		String cloudType = object.get("cloudType");
		String elementType = object.get("elementType");
		String tenantId = object.get("tenantId");
		String accountId = object.get("accountId");
		String inputType = object.get("inputType");
		String fileName = object.get("fileName");
		String dataSource = object.get("dataSource");
		
		Accounts account = accountsService.getAccountByAccountAndTenantId(accountId, tenantId);
		
		AmazonS3 s3Client = AwsUtils.getAmazonS3Client(account.getAccessKey(), account.getSecretKey(), account.getRegion());
		S3Object file = s3Client.getObject(account.getBucket(), fileName);
		
		Dashboard dashboard = new Dashboard();
		
		dashboard.setCloudName(cloudType);
		dashboard.setElementType(elementType);
		dashboard.setTenantId(tenantId);
		dashboard.setAccountId(accountId);
		dashboard.setInputType(inputType);
		dashboard.setFileName(fileName);
		dashboard.setInputSourceId(dataSource);
		dashboard.setTitle(cloudType+"_"+elementType+"_"+dataSource);
		dashboard.setSlug(cloudType+"_"+elementType+"_"+dataSource);
		String data = displayTextInputStream(file.getObjectContent());
		
		ObjectMapper mapper = new ObjectMapper();
//		ArrayNode arrayNode = mapper.createArrayNode();
		
		ObjectNode dataNode = (ObjectNode)mapper.readTree(data);
		for(JsonNode panel : dataNode.get("panels")) {
			ObjectNode oPanel = (ObjectNode)panel;
			oPanel.put("datasource", dataSource);
//			arrayNode.add(oPanel);
        }
//		dataNode.put("panels", arrayNode);
		
		String uid = RandomStringUtils.random(8, true, true);
		dashboard.setUid(uid);
		dashboard.setData(data);
		
		DashboardMeta meta = new DashboardMeta();
		meta.setSlug(dashboard.getSlug());
		
		dashboard.setDashboardMeta(meta);
		return dashboard;
	}
	
	private String displayTextInputStream(InputStream input) throws IOException {
        // Read the text input stream one line at a time and display each line.
		BufferedReader reader = new BufferedReader(new InputStreamReader(input));
        StringBuffer sb = new StringBuffer();
		String line = null;
        while ((line = reader.readLine()) != null) {
            sb.append(line).append(" ");
        	logger.info(sb.toString());
        }
        return sb.toString();
	}
}
