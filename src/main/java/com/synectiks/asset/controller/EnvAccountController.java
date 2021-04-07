package com.synectiks.asset.controller;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.synectiks.asset.domain.EnvAccount;
import com.synectiks.asset.domain.Environment;
import com.synectiks.asset.repository.EnvAccountRepository;
import com.synectiks.asset.repository.EnvironmentRepository;

import io.github.jhipster.web.util.HeaderUtil;



@RestController
@RequestMapping("/api")
public class EnvAccountController {
    
	private static final String ENTITY_NAME = "env_account";
	private String applicationName;
	
	@Autowired
	private EnvAccountRepository envAccountRepository;
	
	@Autowired
	private EnvironmentRepository environmentRepository;
	
	
	@PostMapping("/addAccount")
	public ResponseEntity<List<EnvAccount>> addAccount(
		
			@RequestParam(required = false) String name,
			@RequestParam(required = false) String description,
			@RequestParam(required = false) String status,
			@RequestParam(required = false) String client_id,
			@RequestParam(required = false) String clientSecret,
			@RequestParam(required = false) String userType,
			@RequestParam(required = false) String email,
			@RequestParam(required = false) String password,
			@RequestParam(required = false) String jsonDataContentType,
			@RequestParam(required = false) String updatedBy,
			@RequestParam(required = false) String createdBy,
			@RequestParam(required = false) Long environmentId
			) throws URISyntaxException {
		
		Optional<Environment> oEnv = environmentRepository.findById(environmentId);
		if(oEnv.isPresent()) {
			Environment env = oEnv.get();
			EnvAccount envAccount= new EnvAccount();
			envAccount.setName(name);
			envAccount.setDescription(description);
			envAccount.setStatus(status);
			envAccount.setClientId(client_id);
			envAccount.setClientSecret(clientSecret);
			envAccount.setUserType(userType);
			envAccount.setEmail(email);
			envAccount.setPassword(password);
			envAccount.setJsonDataContentType(jsonDataContentType);
			envAccount.setUpdatedBy(updatedBy);
			envAccount.setCreatedBy(createdBy);
	    	envAccount.setEnvironment(env);
			Instant now= Instant.now();
			envAccount.setCreatedOn(now);
			envAccount.setUpdatedOn(now);
			envAccount = envAccountRepository.save(envAccount);
			List<EnvAccount> list= getAllEnvAccount();
			return ResponseEntity
					.created(new URI("/api/addAccount/" + envAccount.getId())).headers(HeaderUtil
							.createEntityCreationAlert( applicationName, false, ENTITY_NAME,
									envAccount.getId().toString()))
					.body(list);
		}
		
		return ResponseEntity
				.created(new URI("/api/addAccount/")).headers(HeaderUtil
						.createEntityCreationAlert( applicationName, false, ENTITY_NAME,
								""))
				.body(null);
		
	}
	@GetMapping("/getAllEnvAccount")
	private List<EnvAccount> getAllEnvAccount() {
  
		List<EnvAccount> list= envAccountRepository.findAll(Sort.by(Direction.DESC, "id"));
		
		return list;
	}
	
	@GetMapping("/getEnvAccount{id}")
	public ResponseEntity<EnvAccount> getEnvAccount(@PathVariable Long id)
	throws URISyntaxException
	{
		Optional <EnvAccount> oEnvAccount = envAccountRepository.findById(id);
		if(oEnvAccount.isPresent()) {
			
			return ResponseEntity
					.created(new URI("/api/updateEnvAccount/" + oEnvAccount.get().getId())).headers(HeaderUtil 
							.createEntityCreationAlert(applicationName, false, ENTITY_NAME, oEnvAccount.get().getId().toString()))
					.body(oEnvAccount.get());		
		}
		
		return ResponseEntity
				.created(new URI("/api/updateEnvAccount/")).headers(HeaderUtil
						.createEntityCreationAlert(applicationName, false, ENTITY_NAME,  ""))
				.body(null);
	}
	
	

	@PutMapping("/updateEnvAccount")
	private ResponseEntity<EnvAccount> updateEnvAccount(
			@RequestParam Long id,
			@RequestParam(required = false) String name,
			@RequestParam(required = false) String description,
			@RequestParam(required = false) String status,
			@RequestParam(required = false) String client_id,
			@RequestParam(required = false) String clientSecret,
			@RequestParam(required = false) String userType,
			@RequestParam(required = false) String email,
			@RequestParam(required = false) String password,
			@RequestParam(required = false) String jsonDataContentType,
			@RequestParam(required = false) String updatedBy,
			@RequestParam(required = false) String createdBy,
			@RequestParam(name = "username" , required=false )String userName
			) throws URISyntaxException {
		Optional<EnvAccount> oEnvAccount = envAccountRepository.findById(id);
		if(!oEnvAccount.isPresent()) {
		 
		return ResponseEntity
				.created(new URI("/api/updateEnvAccount" )).headers(HeaderUtil
				.createEntityCreationAlert( applicationName, false, ENTITY_NAME, "" ))
				.body(null);
		}
		
		EnvAccount envAccount = oEnvAccount.get();
		envAccount.setName(name);
		envAccount.setDescription(description);
		envAccount.setStatus(status);
		envAccount.setClientId(client_id);
		envAccount.setClientSecret(clientSecret);
		envAccount.setUserType(userType);
		envAccount.setEmail(email);
		envAccount.setPassword(password);
		envAccount.setJsonDataContentType(jsonDataContentType);
		envAccount.setUpdatedBy(updatedBy);
//		envAccount.setCreatedBy(createdBy);
		Instant now= Instant.now();
		envAccount.setUpdatedOn(now);
		
		envAccount = envAccountRepository.save(envAccount);
		return ResponseEntity
				.created(new URI("/api/updateEnvAccount" + envAccount.getId())).headers(HeaderUtil
					.createEntityCreationAlert(applicationName, false, ENTITY_NAME, 
							envAccount.getId().toString()))
				.body(envAccount);
         	}


	@DeleteMapping("/deleteEnvAccount/{id}")
     public  ResponseEntity<Void>  deleteEnvAccount(@PathVariable Long id){
    	 envAccountRepository.deleteById(id);
    	 return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    	 
     }

}




	



	
	

