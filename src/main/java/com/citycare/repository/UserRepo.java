package com.citycare.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.citycare.entity.User;


public interface UserRepo extends JpaRepository<User, Integer>{
	
	public User findByEmail(String email);
}
