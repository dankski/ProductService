package com.appsdeveloperblog.estore.ProductService.rest;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/products") // http://localhost:8080/products
public class ProductsController {
	
	@PostMapping
	public String createProduct() {
		return "HTTP POST Handled";
	}
	
	@GetMapping
	public String getProducts() {
		return "HTTP GET Handled";
	}
	
	@PutMapping
	public String updateProduct() {
		return "HTTP PUT Hdndled";
	}
	
	@DeleteMapping
	public String deleteProduct() {
		return "HTTP DELETE Handled";
	}
}
