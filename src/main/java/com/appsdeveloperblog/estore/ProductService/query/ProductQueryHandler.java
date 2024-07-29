package com.appsdeveloperblog.estore.ProductService.query;

import java.util.ArrayList;
import java.util.List;

import org.axonframework.queryhandling.QueryHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import com.appsdeveloperblog.estore.ProductService.core.data.ProductEntity;
import com.appsdeveloperblog.estore.ProductService.core.data.ProductsRepository;
import com.appsdeveloperblog.estore.ProductService.query.rest.ProductRestModel;

@Component
public class ProductQueryHandler {

  private final ProductsRepository productsRepository;

  public ProductQueryHandler(ProductsRepository productsRepository)  {
    this.productsRepository = productsRepository;
  }

  @QueryHandler
  public List<ProductRestModel> findProducts(FindProductsQuery query) {

    ArrayList<ProductRestModel> productsRest = new ArrayList<>();

    List<ProductEntity> storedProducts = productsRepository.findAll();

    storedProducts.forEach(productEntity -> {
      ProductRestModel productRestModel = new ProductRestModel();
      BeanUtils.copyProperties(productEntity, productRestModel);
      productsRest.add(productRestModel);
    });

    return productsRest;
  }

}
