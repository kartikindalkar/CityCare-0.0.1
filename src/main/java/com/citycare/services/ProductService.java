package com.citycare.services;

import org.springframework.data.domain.Pageable;

import org.springframework.data.domain.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.citycare.entity.Products;
import com.citycare.repository.ProductsRepo;

@Service
public class ProductService {
	
	@Autowired
	private ProductsRepo productRepo;
	
	private Products products;
	
	public Page<Products> getProductsByPaginate(int currentPage,int size){
		Pageable p = PageRequest.of(currentPage, size);
		 return  productRepo.findAll(p);
	}

}
