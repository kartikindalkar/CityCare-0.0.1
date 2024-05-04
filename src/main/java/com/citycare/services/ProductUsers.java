package com.citycare.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.citycare.entity.Products;
import com.citycare.repository.ProductsRepo;

@Service
public class ProductUsers {

	@Autowired
	private  ProductsRepo prodRepo;
	
	
	public Products getProductById(Long prodId) {
		
		 return prodRepo.findById(prodId).orElse(null);
		}

	
	
}
