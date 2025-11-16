package org.demo.resource.impl;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.core.Response;
import lombok.AllArgsConstructor;
import org.demo.dto.ClientDto;
import org.demo.dto.OrderRequest;
import org.demo.entity.CustomerOrder;
import org.demo.resource.OrderResource;
import org.demo.service.ClientService;
import org.demo.service.OrderService;

import java.util.List;

@ApplicationScoped
@AllArgsConstructor
public class OrderResourceImpl implements OrderResource {
  private ClientService clientService;
  private OrderService orderService;

  @Override
  @Transactional
  public Response postOrder(OrderRequest order) {
    return orderService.postOrder(order);
  }

  @Override
  public List<CustomerOrder> getAllOrders() {
    return orderService.geAllOrder();
  }

  @Override
  public CustomerOrder getOrderById(Long id) {
    return orderService.getOrderById(id);
  }

  @Override
  public CustomerOrder postConfirmOrder(Long id) {
    return orderService.postConfirmOrder(id);
  }

  @Override
  public Response postCancelOrder(Long orderId) {
    return orderService.postCancelOrder(orderId);
  }


  @Override
  public List<ClientDto> getAllClients() {
    return clientService.geAllClient();
  }
}
