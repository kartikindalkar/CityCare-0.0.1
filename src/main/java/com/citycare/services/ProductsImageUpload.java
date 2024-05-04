package com.citycare.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.citycare.entity.Products;
import com.citycare.repository.ProductsRepo;

@Service
public class ProductsImageUpload {

	@Autowired
	private ProductsRepo productRepository;

	public Products saveProduct(Products products) {
		return productRepository.save(products);
	}

}
