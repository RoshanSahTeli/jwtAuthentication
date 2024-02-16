package com.example.demo.service;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.entity.User;
import com.example.demo.generate_validate.JwtService;
import com.example.demo.repository.userRepo;

import jakarta.websocket.server.ServerEndpoint;

@Service
public class AuthenticationService {
	
	private final userRepo repo;
	
	private final PasswordEncoder passwordEncoder ;
	
	private final JwtService jwtService ;
	
	private final AuthenticationManager authenticationManager;
	
	
	
	public AuthenticationService(userRepo repo, PasswordEncoder passwordEncoder, JwtService jwtService,
			AuthenticationManager authenticationManager) {
		super();
		this.repo = repo;
		this.passwordEncoder = passwordEncoder;
		this.jwtService = jwtService;
		this.authenticationManager = authenticationManager;
	}



	public AuthenticationResponse register(User request) {
		User user=new User();
		user.setFirstname(request.getFirstname());
		user.setLastname(request.getFirstname());
		user.setUsername(request.getUsername());
		user.setPassword(passwordEncoder.encode(request.getPassword()));
		user.setRole(request.getRole());
		
		user=repo.save(user);
		String token=jwtService.generateToken(user);
		return new AuthenticationResponse(token);
	}
	
	public AuthenticationResponse authenticate(User request) {
		authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
	User user=repo.findByUsername(request.getUsername()).orElseThrow();
		
		String token=jwtService.generateToken(user );
		return new AuthenticationResponse(token);
		
	}
	

}
