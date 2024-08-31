package com.appsdeveloperblog.estore.ProductService.command;

import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.stereotype.Component;

import com.appsdeveloperblog.estore.ProductService.core.events.ProductCreatedEvent;

@Component
@ProcessingGroup("product-group")
public class ProductLookupEventsHandler {

  @EventHandler
  public void on(ProductCreatedEvent event) {

  }
}
