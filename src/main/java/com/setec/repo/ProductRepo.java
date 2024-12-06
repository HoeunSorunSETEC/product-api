package com.setec.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.setec.entities.Product;

public interface ProductRepo extends JpaRepository<Product, Integer> {
 
	public List<Product> findAllByName(String name);
}
