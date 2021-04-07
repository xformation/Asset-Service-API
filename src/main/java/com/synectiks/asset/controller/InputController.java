package com.synectiks.asset.controller;


import java.net.URI;
import java.net.URISyntaxException;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.util.Streamable;
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
import com.synectiks.asset.domain.Input;
import com.synectiks.asset.repository.EnvAccountRepository;
import com.synectiks.asset.repository.InputRepository;

import feign.Headers;
import io.github.jhipster.web.util.HeaderUtil;

@RestController
@RequestMapping("/api")
public class InputController {

	private static final String ENTITY_NAME = "input";
	private String applicationName;
	
	@Autowired
	private InputRepository inputRepository;
	
	@Autowired
	private EnvAccountRepository envAccountRepository;
	
	
	@PostMapping("/addInput")
	public ResponseEntity<List<Input>> addAccount(
			@RequestParam(required = false) String name,
			@RequestParam(required = false) String description,			
			@RequestParam(required = false) String status,
			@RequestParam(required = false) String source,
			@RequestParam(required = false) String type,
			@RequestParam(required = false) String url,
			@RequestParam(required = false) String userId,
			@RequestParam(required = false) String password,
			@RequestParam(required = false) String database,
			@RequestParam(required = false) String updatedBy,
			@RequestParam(required = false) String createdBy,			
			@RequestParam(required = false) Long envAccountId
			) throws URISyntaxException {
		
		Optional<EnvAccount> oIn = envAccountRepository.findById(envAccountId);
		if(oIn.isPresent()) {
			EnvAccount env = oIn.get();
			Input input = new Input();
			input.setName(name);
			input.setDescription(description);
			input.setStatus(status);
			input.setSource(source);
			input.setType(type);
			input.setUrl(url);
			input.setUserId(userId);
			input.setPassword(password);
			input.setDatabase(database);
			input.setCreatedBy(createdBy);
			input.setUpdatedBy(updatedBy);
			input.setEnvAccount(env);
			Instant now= Instant.now();
			input.setCreatedOn(now);
			input.setUpdatedOn(now);
			input = inputRepository.save(input);
			List<Input> list= getAllInput();
			return ResponseEntity
					.created(new URI("/api/addAccount/" + input.getId())).headers(HeaderUtil
							.createEntityCreationAlert( applicationName, false, ENTITY_NAME,
									input.getId().toString()))
					.body(list);
		}
			
		return ResponseEntity
				.created(new URI("/api/addInput/")).headers(HeaderUtil
						.createEntityCreationAlert( applicationName, false, ENTITY_NAME,
								""))
				.body(null);
		}

	@GetMapping("/getAllInput")
	private List<Input> getAllInput() {
  
		List<Input> list= inputRepository.findAll(Sort.by(Direction.DESC, "id"));
		
		return list;
   }

	@GetMapping("/getInput/{id}")
	public ResponseEntity<Input> getInput(@PathVariable Long id)
	throws URISyntaxException{
		Optional<Input> oInput = inputRepository.findById(id);
	if(oInput.isPresent()) {
		return ResponseEntity
				.created(new URI("api/updateInput" + oInput.get().getId())).headers(HeaderUtil
						.createEntityCreationAlert(applicationName, false, ENTITY_NAME, oInput.get().getId().toString()))
	             .body(oInput.get());
	}
	return ResponseEntity
			.created(new URI("api/updateInput")).headers(HeaderUtil
					.createEntityCreationAlert(applicationName, false, ENTITY_NAME, ""))
			       .body(null);
	}
	
	@DeleteMapping("/deleteInput/{id}")
	public ResponseEntity<Void> deteteInput(@PathVariable Long id){
		inputRepository.deleteById(id);
		return ResponseEntity
				.noContent().headers(HeaderUtil
						.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
	}
	
	
	       @PutMapping("/updateInput")
        	private ResponseEntity<Input> updateInput(
			@RequestParam Long id,
			@RequestParam(required = false) String name,
			@RequestParam(required = false) String description,			
			@RequestParam(required = false) String status,
			@RequestParam(required = false) String source,
			@RequestParam(required = false) String type,
			@RequestParam(required = false) String url,
			@RequestParam(required = false) String userId,
			@RequestParam(required = false) String password,
			@RequestParam(required = false) String database,
			@RequestParam(required = false) String updatedBy,
			@RequestParam(required = false) String createdBy,			
			@RequestParam(required = false) Long envAccountId,
			@RequestParam(name = "userName", required = false) String userName
			) throws URISyntaxException{
		Optional<Input> oInput = inputRepository.findById(id);
		if(!oInput.isPresent()) {
			
		
				return  ResponseEntity
						.created(new URI("/api/updateInput")).headers(HeaderUtil
					     .createEntityCreationAlert( applicationName, false,ENTITY_NAME, ""))
						.body(null);	
	     }

		Input input = oInput.get();
		input.setName(name);
		input.setDescription(description);
		input.setStatus(status);
		input.setSource(source);
		input.setType(type);
		input.setUrl(url);
		input.setUserId(userId);
		input.setPassword(password);
		input.setDatabase(database);
		input.setCreatedBy(createdBy);
		input.setUpdatedBy(updatedBy);
		Instant now= Instant.now();
		input.setUpdatedOn(now);
		input = inputRepository.save(input);
		
		return ResponseEntity
				.created(new URI("api/updateInput" + input.getId())).headers(HeaderUtil
						.createEntityCreationAlert(applicationName, false, ENTITY_NAME, input.getId().toString()))
				.body(input);
    }
	
}
