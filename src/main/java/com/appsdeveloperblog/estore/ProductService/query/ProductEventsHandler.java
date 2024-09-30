package com.appsdeveloperblog.estore.ProductService.query;

import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.messaging.interceptors.ExceptionHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import com.appsdeveloperblog.estore.ProductService.core.data.ProductEntity;
import com.appsdeveloperblog.estore.ProductService.core.data.ProductsRepository;
import com.appsdeveloperblog.estore.ProductService.core.events.ProductCreatedEvent;

@Component
@ProcessingGroup("product-group")
public class ProductEventsHandler {

  private final ProductsRepository productsRepository;

  public ProductEventsHandler(ProductsRepository productsRepository) {
    this.productsRepository = productsRepository;
  }

  @ExceptionHandler(resultType = Exception.class)
  public void handle(Exception ex) throws Exception {
    throw ex;
  }

  @ExceptionHandler(resultType = IllegalArgumentException.class)
  public void handle(IllegalArgumentException ex) {
    // Log error message
  }

  @EventHandler
  public void on(ProductCreatedEvent event) throws Exception {
    
    ProductEntity productEntity = new ProductEntity();
    BeanUtils.copyProperties(event, productEntity);
    
    try {
      productsRepository.save(productEntity);
    } catch (IllegalArgumentException ex) {
      ex.printStackTrace();
    }
    
  }

}
