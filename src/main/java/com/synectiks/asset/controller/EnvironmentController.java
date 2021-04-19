package com.synectiks.asset.controller;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.synectiks.asset.domain.Environment;
import com.synectiks.asset.repository.EnvironmentRepository;

import io.github.jhipster.web.util.HeaderUtil;

@RestController
@RequestMapping("/api")
//@CrossOrigin( origins = "*")
public class EnvironmentController {
	private static final String ENTITY_NAME = "environment";
	private static final Logger logger = LoggerFactory.getLogger(EnvironmentController.class);
	
	@Value("${jhipster.clientApp.name}")
	private String applicationName;

	@Autowired
	private EnvironmentRepository environmentRepository;

	@PostMapping("/addEnvironment")
	public ResponseEntity<List<Environment>> addEnvironment(
			@RequestParam(required = false) String name,
			@RequestParam(required = false) String scopes, 
			@RequestParam(required = false) String authUrl,
			@RequestParam(required = false) String tokenUrl, 
			@RequestParam(required = false) String apiUrl,
			@RequestParam(required = false) String description,
			@RequestParam(required = false) String type,
			
			@RequestParam(required = false) String email,
			@RequestParam(required = false) String password,
			@RequestParam(required = false) String clientId,
			@RequestParam(required = false) String clientSecret,
			@RequestParam(required = false) String userType,
			@RequestParam(required = false) String status
			
			) throws URISyntaxException {
		logger.info("Adding environment");
		Environment environment = new Environment();
		environment.setName(name);
		environment.setDescription(description);
		environment.setAuthUrl(authUrl);
		environment.setTokenUrl(tokenUrl);
		environment.setScopes(scopes);
		environment.setApiUrl(apiUrl);
		environment.setType(type);
		
		environment.setEmail(email);
		environment.setPassword(password);
		environment.setClientId(clientId);
		environment.setClientSecret(clientSecret);
		environment.setUserType(userType);
		environment.setStatus(status);
		
		
		Instant now = Instant.now();
		environment.setCreatedOn(now);
		environment.setUpdatedOn(now);
		environment = environmentRepository.save(environment);
		List<Environment> list = getAllEnvironment();
		logger.info("Adding environment completed");
		return ResponseEntity
				.created(new URI("/api/addEnvironment/" + environment.getId())).headers(HeaderUtil
						.createEntityCreationAlert(applicationName, false, ENTITY_NAME, environment.getId().toString()))
				.body(list);

	}

	@GetMapping("/getAllEnvironment")
	private List<Environment> getAllEnvironment() {
		logger.info("Getting all environments");
		List<Environment> list = environmentRepository.findAll(Sort.by(Direction.DESC, "id"));
		logger.info("Getting all environments completed");
		return list;
	}
	
	@PutMapping("/updateEnvironment")
	public ResponseEntity<Environment> updateEnvironment(
			@RequestParam Long id,
			@RequestParam(required = false) String name,
			@RequestParam(required = false) String scopes, 
			@RequestParam(required = false) String authUrl,
			@RequestParam(required = false) String tokenUrl, 
			@RequestParam(required = false) String apiUrl,
			@RequestParam(required = false) String description,
			@RequestParam(name = "userName", required = false) String userName
			) throws URISyntaxException {
		logger.info("Updating environment");
		
		Optional<Environment> oEnv = environmentRepository.findById(id);
		if(!oEnv.isPresent()) {
			logger.warn("Environment object not found for give id: "+id);
			return ResponseEntity
					.created(new URI("/api/updateEnvironment/")).headers(HeaderUtil
							.createEntityCreationAlert(applicationName, false, ENTITY_NAME, ""))
					.body(null);
		}
		Environment environment = oEnv.get();
		environment.setName(name);
		environment.setDescription(description);
		environment.setScopes(scopes);
		environment.setAuthUrl(authUrl);
		environment.setTokenUrl(tokenUrl);
		environment.setApiUrl(apiUrl);
		Instant now = Instant.now();
		environment.setUpdatedOn(now);

		environment = environmentRepository.save(environment);
		logger.info("Updating environment completed");
		return ResponseEntity
				.created(new URI("/api/updateEnvironment/" + environment.getId())).headers(HeaderUtil
						.createEntityCreationAlert(applicationName, false, ENTITY_NAME, environment.getId().toString()))
				.body(environment);
	}

	@DeleteMapping("/deleteEnvironment/{id}")
    public ResponseEntity<Void> deleteEnvironment(@PathVariable Long id) {
		logger.info("Deleting environment");
		environmentRepository.deleteById(id);
		logger.info("Deleting environment completed");
		return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
	}
	
	@GetMapping("/getEnvironment/{id}")
    public ResponseEntity<Environment> getEnvironment(@PathVariable Long id) throws URISyntaxException {
		logger.info("Getting environment");
		Optional<Environment> oEnv =environmentRepository.findById(id);
		if(oEnv.isPresent()) {
			logger.info("Environment object found");
			return ResponseEntity
					.created(new URI("/api/updateEnvironment/" + oEnv.get().getId())).headers(HeaderUtil
							.createEntityCreationAlert(applicationName, false, ENTITY_NAME, oEnv.get().getId().toString()))
					.body(oEnv.get());
		}
		logger.info("Getting environment completed");
		return ResponseEntity
				.created(new URI("/api/updateEnvironment/")).headers(HeaderUtil
						.createEntityCreationAlert(applicationName, false, ENTITY_NAME, ""))
				.body(null);
	}
}
