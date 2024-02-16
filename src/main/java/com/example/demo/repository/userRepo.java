package com.example.demo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.User;

public interface userRepo extends JpaRepository<User, Integer> {
	
	Optional<User>  findByUsername(String username);

	User save(User user);

	

}
