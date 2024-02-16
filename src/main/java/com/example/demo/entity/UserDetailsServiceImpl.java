package com.example.demo.entity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.example.demo.repository.userRepo;

@Component
public class UserDetailsServiceImpl implements UserDetailsService{
	@Autowired
	private userRepo repo;

	public UserDetailsServiceImpl(userRepo repo) {
		super();
		this.repo = repo;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		
		return repo.findByUsername(username)
				.orElseThrow(()->new UsernameNotFoundException("username not found"));
	}

}
