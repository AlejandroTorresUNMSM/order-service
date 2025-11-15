package org.demo.service.impl;

import io.quarkus.logging.Log;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.core.Response;
import lombok.AllArgsConstructor;
import org.demo.dto.OrderRequest;
import org.demo.dto.OrderStatus;
import org.demo.entity.CustomerOrder;
import org.demo.mapper.OrderMapper;
import org.demo.service.OrderService;
import org.demo.utils.Utils;

import java.net.URI;
import java.util.List;

@ApplicationScoped
@AllArgsConstructor
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
  public Response postOrder(OrderRequest order) {
    Log.info("OrderServiceImpl -> Creando orden");
//    Utils.validateOrder(order);
    CustomerOrder customerOrderResult = OrderMapper.INSTANCE.orderRequestToOrder(order);
    customerOrderResult.orderStatus = OrderStatus.PENDING;
    CustomerOrder.persist(customerOrderResult);
    Log.info("OrderServiceImpl -> Orden creada con exito");
    return Response.created(URI.create("/api/orders/" + customerOrderResult.id)).build();
  }

  @Override
  public CustomerOrder postConfirmOrder(Long id) {
    Log.info("OrderServiceImpl -> Confirmando orden");
    CustomerOrder entity = CustomerOrder.findById(id);
    Utils.validateOrder(entity);
    entity.orderStatus = OrderStatus.CONFIRMED;
    CustomerOrder.persist(entity);
    Log.info("OrderServiceImpl -> Orden confirmada con exito");
    return entity;
  }
}
