package org.demo.service.impl;

import io.quarkus.logging.Log;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.core.Response;
import org.demo.dto.OrderCancelledEvent;
import org.demo.dto.OrderCreatedEvent;
import org.demo.dto.OrderRequest;
import org.demo.dto.OrderStatus;
import org.demo.entity.CustomerOrder;
import org.demo.mapper.OrderMapper;
import org.demo.service.OrderService;
import org.demo.utils.Utils;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;

import java.net.URI;
import java.util.List;

@ApplicationScoped
//@AllArgsConstructor
public class OrderServiceImpl implements OrderService {
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
    Log.info("OrderServiceImpl -> Creando orden");
    Utils.validateOrder(order);
    CustomerOrder customerOrderResult = OrderMapper.INSTANCE.orderRequestToOrder(order);
    customerOrderResult.orderStatus = OrderStatus.PENDING;
    CustomerOrder.persist(customerOrderResult);
    Log.info("OrderServiceImpl -> Orden creada con exito");
    return Response.created(URI.create("/api/orders/" + customerOrderResult.id)).build();
  }

  @Override
  @Transactional
  public CustomerOrder postConfirmOrder(Long id) {
    Log.info("OrderServiceImpl -> Confirmando orden");
    CustomerOrder entity = CustomerOrder.findById(id);
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

  @Inject
  @Channel("orders-out")
  Emitter<OrderCreatedEvent> orderEmitter;
  public void publishOrderCreate(CustomerOrder order) {
    Log.info("OrderServiceImpl -> Enviando evento creacion");
    orderEmitter.send(OrderMapper.INSTANCE.customerOrderToOrderCreatedEvent(order));
    Log.info("OrderServiceImpl -> Evento creacion enviado");
  }

  @Inject
  @Channel("orders-cancel")
  Emitter<OrderCancelledEvent> cancelEmitter;
  public void publishOrderCancel(Long orderId) {
    Log.info("OrderServiceImpl -> Enviando evento cancelacion");
    cancelEmitter.send(new OrderCancelledEvent(orderId));
    Log.info("OrderServiceImpl -> Evento cancelacion enviado");
  }
}
