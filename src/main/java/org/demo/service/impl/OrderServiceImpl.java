package org.demo.service.impl;

import io.quarkus.logging.Log;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.core.Response;
import lombok.AllArgsConstructor;
import org.demo.dto.ClientDto;
import org.demo.dto.OrderCancelledEvent;
import org.demo.dto.OrderCreatedEvent;
import org.demo.dto.OrderRequest;
import org.demo.dto.OrderStatus;
import org.demo.entity.CustomerOrder;
import org.demo.mapper.OrderMapper;
import org.demo.service.ClientService;
import org.demo.service.OrderService;
import org.demo.utils.Utils;
import org.demo.validator.ClientValidator;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;

import java.net.URI;
import java.util.List;

@ApplicationScoped
public class OrderServiceImpl implements OrderService {
  @Inject
  private ClientService clientService;

  @Inject
  ClientValidator clientValidator;

  @Inject
  @Channel("orders-out")
  Emitter<OrderCreatedEvent> orderEmitter;

  @Inject
  @Channel("orders-cancel")
  Emitter<OrderCancelledEvent> cancelEmitter;

  @Override
  public List<CustomerOrder> geAllOrder() {
    Log.info("OrderServiceImpl -> Trayendo todas ordenes");
    return CustomerOrder.listAll();
  }

  @Override
  public CustomerOrder getOrderById(Long id) {
    return CustomerOrder.findById(id);
  }

  @Override
  @Transactional
  public Response postOrder(OrderRequest order) {
    Utils.validateOrder(order);
    CustomerOrder customerOrderResult = OrderMapper.INSTANCE.orderRequestToOrder(order);
    ClientDto clientDto = clientValidator.validateClient(order.clientId);
    if (clientDto == null) {
      customerOrderResult.orderStatus = OrderStatus.PENDING;
    }else {
      customerOrderResult.orderStatus = OrderStatus.CONFIRMED;
    }
    Log.info("OrderServiceImpl -> Creando orden");

    CustomerOrder.persist(customerOrderResult);
    Log.info("OrderServiceImpl -> Orden creada con exito");

    if(customerOrderResult.orderStatus.equals(OrderStatus.CONFIRMED)){
      publishOrderCreate(customerOrderResult);
    }
    return Response.created(URI.create("/api/orders/" + customerOrderResult.id)).build();
  }

  @Override
  @Transactional
  public CustomerOrder postConfirmOrder(Long id) {
    Log.info("OrderServiceImpl -> Confirmando orden");
    CustomerOrder entity = CustomerOrder.findById(id);
    ClientDto clientDto = clientService.getClientById(entity.clientId);
    if (clientDto == null) {
      throw new IllegalArgumentException("El cliente no esta validado");
    }
    Utils.validateOrderConfirm(entity);
    entity.orderStatus = OrderStatus.CONFIRMED;
    CustomerOrder.persist(entity);
    Log.info("OrderServiceImpl -> Orden confirmada con exito");
    publishOrderCreate(entity);
    return entity;
  }

  @Override
  @Transactional
  public Response postCancelOrder(Long id) {
    CustomerOrder entity = CustomerOrder.findById(id);
    Utils.validateOrder(entity);
    Utils.validateStatusCancelled(entity.orderStatus);
    entity.orderStatus = OrderStatus.CANCELLED;
    CustomerOrder.persist(entity);
    Log.info("OrderServiceImpl -> Orden cancelada con exito");
    publishOrderCancel(entity.id);
    return Response.noContent().build();
  }

  public void publishOrderCreate(CustomerOrder order) {
    Log.info("OrderServiceImpl -> Enviando evento creacion");
    orderEmitter.send(OrderMapper.INSTANCE.customerOrderToOrderCreatedEvent(order));
    Log.info("OrderServiceImpl -> Evento creacion enviado");
  }

  public void publishOrderCancel(Long orderId) {
    Log.info("OrderServiceImpl -> Enviando evento cancelacion");
    cancelEmitter.send(new OrderCancelledEvent(orderId));
    Log.info("OrderServiceImpl -> Evento cancelacion enviado");
  }
}
