package org.demo.service;

import jakarta.ws.rs.core.Response;
import org.demo.dto.OrderRequest;
import org.demo.entity.CustomerOrder;

import java.util.List;

public interface OrderService {
  List<CustomerOrder> geAllOrder();
  CustomerOrder getOrderById(Long id);
  Response postOrder(OrderRequest order);
  CustomerOrder postConfirmOrder(Long id);
  Response postCancelOrder(Long id);
}
