package com.citycare.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.citycare.entity.Products;
import com.citycare.entity.User;

public interface ProductsRepo extends JpaRepository<Products, Long>{

	List<Products> findByUser(User user);

}
