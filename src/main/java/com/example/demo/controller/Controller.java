package com.example.demo.controller;

import org.springframework.boot.devtools.restart.RestartScope;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.service.annotation.GetExchange;

import com.example.demo.entity.User;
import com.example.demo.service.AuthenticationResponse;
import com.example.demo.service.AuthenticationService;

@RestController
public class Controller {
	
	public final AuthenticationService authService ;

	public Controller(AuthenticationService authService) {
		super();
		this.authService = authService;
	}
	
	@PostMapping("/register")
	public ResponseEntity<AuthenticationResponse> register(@RequestBody User request){
		return ResponseEntity.ok(authService.register(request));
	}
	
	@PostMapping("/login")
	public ResponseEntity<AuthenticationResponse> login(@RequestBody User request){
		return ResponseEntity.ok(authService.authenticate(request));
	}
	
	@GetExchange("/demo")
	public ResponseEntity<String> demo(){
		return ResponseEntity.ok().body("i am secured");
	}

}
