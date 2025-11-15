package org.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class OrderCreatedEvent {
  public Long orderId;
  public Long clientId;
  public OrderStatus orderStatus;
  public List<Item> listaProductos;
  public String createdAt;


}
