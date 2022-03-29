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
import com.synectiks.asset.business.appservice.AccountsService;
import com.synectiks.asset.business.appservice.CloudAssetService;
import com.synectiks.asset.domain.Accounts;

@RestController
@RequestMapping("/api")
public class AccountsController {
	
	private static final Logger logger = LoggerFactory.getLogger(AccountsController.class);
		
	@Autowired
	CloudAssetService cloudAssetService;
	
	@Autowired
	AccountsService accountsService;
	
	@GetMapping("/getAccount/{id}")
	public ResponseEntity<Accounts> getAccount(@PathVariable Long id) {
		logger.info("Request to get account by id. Id: "+id);
		Accounts accounts = accountsService.getAccount(id);
		if(accounts != null) {
			return ResponseEntity.status(HttpStatus.OK).body(accounts);
		}
		return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(null);
	}
	
	@GetMapping("/getAccountByAccountAndTenantId")
	public ResponseEntity<Accounts> getAccountByAccountAndTenantId(@RequestParam String accountId, @RequestParam String tenantId) {
		logger.info("Request to get account by account id:"+accountId+" and tenanat id: "+tenantId);
		Accounts accounts = accountsService.getAccountByAccountAndTenantId(accountId, tenantId);
		if(accounts != null) {
			return ResponseEntity.status(HttpStatus.OK).body(accounts);
		}
		return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(null);
	}
	
	@GetMapping("/searchAccounts")
	public List<Accounts> searchAccounts(@RequestParam Map<String, String> reqObj) {
		logger.info("Request to get accounts on given filter criteria");
		List<Accounts> list = accountsService.searchAccounts(reqObj);
		return list;
	}
	
	@PostMapping("/addAccount")
	public ResponseEntity<Accounts> addAccount(@RequestBody ObjectNode obj) throws Exception {
		logger.info("Request to add an account");
		Accounts accounts = null;
		try {
			accounts = accountsService.addAccount(obj);
			if(accounts != null) {
				logger.info("Account added");
				return ResponseEntity.status(HttpStatus.OK).body(accounts);
			}
		}catch(Exception e) {
			logger.error("Add account failed. Exception : ", e);
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(null);
		}
		logger.error("Add account failed");
		return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(null);
	}

	@PostMapping("/updateAccount")
	public ResponseEntity<Accounts> updateAccount(@RequestBody ObjectNode obj) throws Exception  {
		logger.info("Request to update an account");
		Accounts accounts = null;
		try {
			accounts = accountsService.updateAccount(obj);
			if(accounts != null) {
				logger.info("Account updated");
				return ResponseEntity.status(HttpStatus.OK).body(accounts);
			}
		}catch(Exception e) {
			logger.error("Update account failed. Exception : ", e);
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(null);
		}
		logger.error("Update account failed");
		return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(null);
	}
	
}
