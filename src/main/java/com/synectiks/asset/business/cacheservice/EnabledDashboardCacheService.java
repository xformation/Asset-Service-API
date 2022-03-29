package com.synectiks.asset.business.cacheservice;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.amazonaws.services.s3.AmazonS3;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.synectiks.asset.business.appservice.AccountsService;
import com.synectiks.asset.business.appservice.InputConfigService;
import com.synectiks.asset.config.Constants;
import com.synectiks.asset.domain.Accounts;
import com.synectiks.asset.domain.Dashboard;
import com.synectiks.asset.domain.DashboardMeta;
import com.synectiks.asset.domain.InputConfig;
import com.synectiks.asset.util.Utils;

@Service
public class EnabledDashboardCacheService {
	
	private static final Logger logger = LoggerFactory.getLogger(EnabledDashboardCacheService.class);
	
	@Autowired
	private AccountsService accountsService;
	
	@Autowired
	private InputConfigService inputConfigService;
	
	@PostConstruct
    private void postConstruct() throws IOException {
		this.initEnableDashboardCache();
    }
	
	public void initEnableDashboardCache() throws IOException {
		logger.info("Initializing dashboard cache");
		List<InputConfig> inputConfigList = inputConfigService.searchInputConfig(new HashMap<String, String>());
		
		for(InputConfig inputConfig: inputConfigList) {
			ObjectNode requestObj = new ObjectMapper().createObjectNode();
			requestObj.put("accountId", inputConfig.getAccounts().getAccountId());
			requestObj.put("tenantId", inputConfig.getTenantId());
			requestObj.put("inputType", inputConfig.getInputType());
			requestObj.put("viewJson", new String(inputConfig.getViewJson()));
			this.preBuild(requestObj);
		}
		logger.info("Dashboard cache initialized");
	}
	
	public void preBuild(ObjectNode requestObj) throws IOException {
		logger.info("Creating dashboard cache");
		String accountId = requestObj.get("accountId").asText();
		String tenantId = requestObj.get("tenantId").asText();
		String inputType = requestObj.get("inputType").asText();
		String viewJson = requestObj.get("viewJson").asText();
		
		ObjectMapper mapper = new ObjectMapper();
		Accounts ac = accountsService.getAccountByAccountAndTenantId(accountId, tenantId);
		AmazonS3 s3Client = Utils.getAmazonS3Client(ac.getAccessKey(), ac.getSecretKey(), ac.getRegion());
		
		ArrayNode jsonArray = (ArrayNode)mapper.readTree(viewJson);
		List<Dashboard> dashList = new ArrayList<>();
		if(jsonArray.isArray()) {
	        for(JsonNode jsonNode : jsonArray) {
	        	Dashboard ds = downloadDashboardFromAwsS3(mapper, jsonNode, ac, s3Client);
	        	dashList.add(ds);
	        }
	    }
		 
		buildCache(accountId, tenantId, inputType, dashList);

		logger.info("Dashboard cache created");
	}

	
	
	private Dashboard downloadDashboardFromAwsS3(ObjectMapper mapper, JsonNode jsonNode, Accounts account, AmazonS3 s3Client) throws IOException {
		Map<String, String> object = new HashMap<>();
		object.put("cloudType",jsonNode.get("CloudName").asText());
		object.put("elementType",jsonNode.get("ElementType").asText());
		object.put("tenantId",jsonNode.get("TenantId").asText());
		object.put("accountId",jsonNode.get("AccountId").asText());
		object.put("inputType",jsonNode.get("InputType").asText());
		object.put("fileName",jsonNode.get("FileName").asText());
		object.put("dataSource",jsonNode.get("InputSourceId").asText());
		
		Dashboard ds = Utils.getDashboardFromAwsS3(object, mapper, account, s3Client);
		ObjectNode dataNode = (ObjectNode)mapper.readTree(ds.getData());
		
		if(jsonNode.get("Uid") != null) {
			ds.setUid(jsonNode.get("Uid").asText());
			dataNode.put("uid", jsonNode.get("Uid").asText());
		}
		if(jsonNode.get("Uuid") != null) {
			ds.setUuid(jsonNode.get("Uuid").asText());
		}
		if(jsonNode.get("Slug") != null) {
			ds.setSlug(jsonNode.get("Slug").asText());
			ds.setTitle(jsonNode.get("Slug").asText());
			dataNode.put("slug", jsonNode.get("Slug").asText());
			dataNode.put("title", jsonNode.get("Slug").asText());
		}
		if(jsonNode.get("Version") != null) {
			ds.setVersion(jsonNode.get("Version").asInt());
			dataNode.put("version", jsonNode.get("Version").asText());
		}
		if(jsonNode.get("Id") != null) {
			ds.setId(jsonNode.get("Id").asLong());
			dataNode.put("id", jsonNode.get("Id").asLong());
		}
		if(jsonNode.get("IsCloud") != null) {
			ds.setCloud(jsonNode.get("IsCloud").asBoolean());
		}
		String newData = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(dataNode);
		
		DashboardMeta meta = ds.getDashboardMeta();
		meta.setSlug(ds.getSlug());
		meta.setVersion(ds.getVersion());
		ds.setDashboardMeta(meta);
		
		ds.setData(newData);
		return ds;
	}
	
	private static void buildCache(String accountId, String tenantId, String inputType,
			List<Dashboard> dashList) {
		if(!Constants.ENABLED_DASHBOARD_CACHE.containsKey(tenantId)) {
			Map<String, List<Dashboard>> inputMap = new HashMap<>();
			inputMap.put(inputType.toUpperCase(), dashList);
			
			Map<String, Map<String, List<Dashboard>> > accountMap = new HashMap<>();
			accountMap.put(accountId, inputMap);
			
			Constants.ENABLED_DASHBOARD_CACHE.put(tenantId, accountMap);
		}else {
			Map<String, Map<String, List<Dashboard>> > accountMap = Constants.ENABLED_DASHBOARD_CACHE.get(tenantId);
			if(!accountMap.containsKey(accountId)) {
				Map<String, List<Dashboard>> inputMap = new HashMap<>();
				inputMap.put(inputType.toUpperCase(), dashList);
				accountMap.put(accountId, inputMap);
			}else {
				Map<String, List<Dashboard>> inpMap = accountMap.get(accountId);
				if(!inpMap.containsKey(inputType.toUpperCase())) {
					inpMap = new HashMap<>();
					inpMap.put(inputType.toUpperCase(), dashList);
				}else {
					inpMap.put(inputType.toUpperCase(), dashList);
				}
				accountMap.put(accountId, inpMap);
			}
		}
	}
	
}
