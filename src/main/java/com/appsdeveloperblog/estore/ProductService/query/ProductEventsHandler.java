package com.appsdeveloperblog.estore.ProductService.query;

import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.messaging.interceptors.ExceptionHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import com.appsdeveloperblog.estore.ProductService.core.data.ProductEntity;
import com.appsdeveloperblog.estore.ProductService.core.data.ProductsRepository;
import com.appsdeveloperblog.estore.ProductService.core.events.ProductCreatedEvent;
import com.appsdeveloperblog.estore.core.events.ProductReservationCancellledEvent;
import com.appsdeveloperblog.estore.core.events.ProductReservedEvent;

@Component
@ProcessingGroup("product-group")
public class ProductEventsHandler {

  private final ProductsRepository productsRepository;

  private static final Logger LOGGER = LoggerFactory.getLogger(ProductEventsHandler.class);

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

  @EventHandler
  public void on(ProductReservedEvent productReservedEvent) {
    ProductEntity productEntity = productsRepository.findByProductId(productReservedEvent.getProductId());
    productEntity.setQuantity(productEntity.getQuantity() - productReservedEvent.getQuantity());
    productsRepository.save(productEntity);

    LOGGER.info("ProductReservedEvent is called for productId: " + productReservedEvent.getProductId() + " and orderId: " + productReservedEvent.getOrderId());
  }


  @EventHandler
  public void on(ProductReservationCancellledEvent productReservationCancellledEvent) {
     ProductEntity currentStoredProduct = productsRepository.findByProductId(productReservationCancellledEvent.getProductId());

     int newQuantity = currentStoredProduct.getQuantity() + productReservationCancellledEvent.getQuantity();

      currentStoredProduct.setQuantity(newQuantity);
      productsRepository.save(currentStoredProduct);
  }
}
