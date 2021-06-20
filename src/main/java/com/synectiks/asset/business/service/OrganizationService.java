package com.synectiks.asset.business.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.synectiks.asset.config.ApplicationProperties;
import com.synectiks.asset.domain.Organization;

@Service
public class OrganizationService {
	
	private static final Logger logger = LoggerFactory.getLogger(OrganizationService.class);
	
	@Autowired
	ApplicationProperties appProps;
	
	@Autowired
    RestTemplate restTemplate;
	
	public Organization getOrganization(String userName) {
        String secSrvUrl = appProps.getSecurityServiceUrl();
        String url = secSrvUrl+"/security/organization/getOrganization?userName="+userName;
        Organization org = this.restTemplate.getForObject(url, Organization.class);
        return org;
    }
	
	public Organization getOrganization(Long id) {
        String secSrvUrl = appProps.getSecurityServiceUrl();
        String url = secSrvUrl+"/security/organization/getOrganization/"+id;
        Organization org = this.restTemplate.getForObject(url, Organization.class);
        return org;
    }
}
