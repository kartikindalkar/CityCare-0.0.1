package com.citycare.repository;


import com.citycare.entity.User;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageUploadRepository extends JpaRepository <User, Integer> {
	

}
