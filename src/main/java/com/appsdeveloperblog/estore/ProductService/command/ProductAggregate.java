package com.appsdeveloperblog.estore.ProductService.command;

import java.math.BigDecimal;

import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;
import org.springframework.beans.BeanUtils;

import com.appsdeveloperblog.estore.ProductService.core.events.ProductCreatedEvent;
import com.appsdeveloperblog.estore.core.commands.CancelProductReservationCommand;
import com.appsdeveloperblog.estore.core.commands.ReserveProductCommand;
import com.appsdeveloperblog.estore.core.events.ProductReservationCancellledEvent;
import com.appsdeveloperblog.estore.core.events.ProductReservedEvent;

@Aggregate(snapshotTriggerDefinition = "productSnapshotTriggerDefinition")
public class ProductAggregate {

  @AggregateIdentifier
  private String productId;
  private String title;
  private BigDecimal price;
  private Integer quantity;
  
  public ProductAggregate() {

  }

  @CommandHandler
  public ProductAggregate(CreateProductCommand createProductCommand) {
    // Validate Create Product Command

    if (createProductCommand.getPrice().compareTo(BigDecimal.ZERO) <= 0) {
      throw new IllegalArgumentException("Price cannot be less or equal than zero");
    }

    if (createProductCommand.getTitle() == null || createProductCommand.getTitle().isBlank()) {
      throw new IllegalArgumentException("Title cannot be empty");
    }

    ProductCreatedEvent productCreatedEvent = new ProductCreatedEvent();
    BeanUtils.copyProperties(createProductCommand, productCreatedEvent);

    AggregateLifecycle.apply(productCreatedEvent);

  }

  @CommandHandler
  public void handle(ReserveProductCommand reserveProductCommand) {
    if (quantity < reserveProductCommand.getQuantity()) {
      throw new IllegalArgumentException("Insufficent number of items in stock");
    }

    ProductReservedEvent productReservedEvent = ProductReservedEvent.builder()
    .orderId(reserveProductCommand.getOrderId())
    .productId(reserveProductCommand.getProductId())
    .quantity(reserveProductCommand.getQuantity())
    .userId(reserveProductCommand.getUserId())
    .build();

    AggregateLifecycle.apply(productReservedEvent);
  }

  @CommandHandler
  public void handle(CancelProductReservationCommand cancelProductReservationCommand) {
    ProductReservationCancellledEvent productReservationCancellledEvent = ProductReservationCancellledEvent.builder()
                                                                          .orderId(cancelProductReservationCommand.getOrderId())
                                                                          .productId(cancelProductReservationCommand.getProductId())
                                                                          .quantity(cancelProductReservationCommand.getQuantity())
                                                                          .userId(cancelProductReservationCommand.getUserId())
                                                                          .reason(cancelProductReservationCommand.getReason())
                                                                          .build();

    AggregateLifecycle.apply(productReservationCancellledEvent);                                                       
  }

  @EventSourcingHandler
  public void on(ProductReservationCancellledEvent productReservationCancellledEvent) {
    this.quantity += productReservationCancellledEvent.getQuantity();
    
  }

  /**
   * This method updates the aggreate with the latest values.
   * 
   * @param productCreatedEvent
   */
  @EventSourcingHandler
  public void on(ProductCreatedEvent productCreatedEvent) {
    this.productId = productCreatedEvent.getProductId();
    this.price = productCreatedEvent.getPrice();
    this.title = productCreatedEvent.getTitle();
    this.quantity = productCreatedEvent.getQuantity();
  }

  @EventSourcingHandler
  public void on(ProductReservedEvent productReservedEvent) {
    this.quantity -= productReservedEvent.getQuantity();
  }

}
