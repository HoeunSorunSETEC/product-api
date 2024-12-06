package com.setec.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.setec.entities.Product;
import com.setec.repo.ProductRepo;

import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api/product")
public class MyController {
	@Autowired
	private ProductRepo productRepo;

	@GetMapping
	public Object getAllProducts() {

		return productRepo.findAll();
	}

	@GetMapping("/{id}")
	public Object getById(@PathVariable Integer id, HttpServletResponse http) {
		var pro = productRepo.findById(id);
		if (pro.isPresent()) {
			http.setStatus(HttpServletResponse.SC_OK);
			return pro.get();
		} else {
			http.setStatus(HttpServletResponse.SC_NOT_FOUND);
			return "Product id= " + id + " notfound";
		}
	}

	@GetMapping("/name/{name}")
	public Object getByName(@PathVariable String name, HttpServletResponse http) {
		var pro = productRepo.findAllByName(name);
		if (pro.size() > 0) {
			http.setStatus(HttpServletResponse.SC_OK);
			return pro;
		} else {
			http.setStatus(HttpServletResponse.SC_NOT_FOUND);
			return "Product id= " + name + " notfound";
		}
	}

	@DeleteMapping("/{id}")
	public Object deleteById(@PathVariable Integer id, HttpServletResponse http) {

		var pro = productRepo.findById(id);
		if (pro.isPresent()) {
			http.setStatus(HttpServletResponse.SC_ACCEPTED);
			var p = pro.get();
			productRepo.delete(p);
			return p;
			
		}else {
			http.setStatus(HttpServletResponse.SC_NOT_FOUND);
			return "Product id = " + id + " not found";
		}
	}
	
	@PostMapping
	public Object addProduct(@RequestBody Product product,HttpServletResponse http) {
		product.setId(null);
		productRepo.save(product);
		http.setStatus(HttpServletResponse.SC_CREATED);
		return product;
	}
	
	@PutMapping
	public Object editProduct(@RequestBody Product product,HttpServletResponse http) {
		if(product.getId()!=null) {
		var pro = productRepo.findById(product.getId());
		if(pro.isPresent()) {
			http.setStatus(HttpServletResponse.SC_ACCEPTED);
			productRepo.save(product);
			return product;
		}
	}
		
		product.setId(null);
		productRepo.save(product);
		http.setStatus(HttpServletResponse.SC_CREATED);
		return product;
}
}
