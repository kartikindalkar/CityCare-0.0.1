package com.citycare.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.citycare.entity.User;


public interface UserRepo extends JpaRepository<User, Integer>{
	
	public User findByEmail(String email);
	
	public User findById(Long id);

	
}
