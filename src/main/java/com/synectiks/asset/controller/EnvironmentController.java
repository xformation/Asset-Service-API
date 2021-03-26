package com.synectiks.asset.controller;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.Instant;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.synectiks.asset.config.Constants;
import com.synectiks.asset.domain.Environment;
import com.synectiks.asset.repository.EnvironmentRepository;

import io.github.jhipster.web.util.HeaderUtil;

@RestController
@RequestMapping("/api")
@CrossOrigin( origins = "*")
public class EnvironmentController {
	private static final String ENTITY_NAME = "environment";

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
			@RequestParam(required = false) String description
			) throws URISyntaxException {

		Environment environment = new Environment();
		environment.setName(name);
		environment.setDescription(description);
		environment.setAuthUrl(authUrl);
		environment.setTokenUrl(tokenUrl);
		environment.setApiUrl(apiUrl);
		Instant now = Instant.now();
		environment.setCreatedOn(now);
		environment.setUpdatedOn(now);
		environment = environmentRepository.save(environment);
		List<Environment> list = getAllEnvironment();
		return ResponseEntity
				.created(new URI("/api/addEnvironment/" + environment.getId())).headers(HeaderUtil
						.createEntityCreationAlert(applicationName, false, ENTITY_NAME, environment.getId().toString()))
				.body(list);

	}

	@GetMapping("/getAllEnvironment")
	private List<Environment> getAllEnvironment() {
		List<Environment> list = environmentRepository.findAll(Sort.by(Direction.DESC, "id"));
		return list;
	}
	
	@PutMapping("/updateEnvironment")
	public ResponseEntity<Environment> updateEnvironment(
			@RequestParam(required = false) String name,
			@RequestParam(required = false) String scopes, 
			@RequestParam(required = false) String authUrl,
			@RequestParam(required = false) String tokenUrl, 
			@RequestParam(required = false) String apiUrl,
			@RequestParam(required = false) String description,
			@RequestParam(name = "userName", required = false) String userName
			)
					throws URISyntaxException {

		Environment environment = new Environment();
		environment.setName(name);
		environment.setDescription(description);
		environment.setAuthUrl(authUrl);
		environment.setTokenUrl(tokenUrl);
		environment.setApiUrl(apiUrl);
		Instant now = Instant.now();
		environment.setUpdatedOn(now);

		environment = environmentRepository.save(environment);

		return ResponseEntity
				.created(new URI("/api/updateEnvironment/" + environment.getId())).headers(HeaderUtil
						.createEntityCreationAlert(applicationName, false, ENTITY_NAME, environment.getId().toString()))
				.body(environment);
	}

	
	
}
