package com.appsdeveloperblog.estore.ProductService.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/products") // http://localhost:8080/products
public class ProductsController {

  @Autowired
  private Environment env;

	@PostMapping
	public String createProduct(@RequestBody CreateProductRestModel createProductRestModel) {
		return "HTTP POST Handled " + createProductRestModel.getTitle();
	}
	
	@GetMapping
	public String getProducts() {
		return "HTTP GET Handled " + env.getProperty("local.server.port");
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
