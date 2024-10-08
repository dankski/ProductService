package com.appsdeveloperblog.estore.ProductService.query.rest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.appsdeveloperblog.estore.ProductService.query.FindProductsQuery;

import java.util.List;

import org.axonframework.messaging.responsetypes.ResponseTypes;

import org.axonframework.queryhandling.QueryGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping("/products") 
public class ProductQueryController {

  @Autowired
  QueryGateway queryGateway;

  @GetMapping
  public List<ProductRestModel> getProducts() {

    FindProductsQuery findProductsQuery = new FindProductsQuery();
    List<ProductRestModel> products = queryGateway.query(findProductsQuery, ResponseTypes.multipleInstancesOf(ProductRestModel.class)).join();

    return products;
  }
  
}
