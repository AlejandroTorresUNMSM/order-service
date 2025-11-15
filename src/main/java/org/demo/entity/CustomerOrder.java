package org.demo.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import org.demo.dto.Item;
import org.demo.dto.OrderStatus;

import java.util.List;

@Entity
public class CustomerOrder extends PanacheEntity {
  public Long clientId;
  @Enumerated(EnumType.STRING)
  public OrderStatus orderStatus;
  @ElementCollection
  public List<Item> listaProductos;

  public static CustomerOrder findById(Long id){
    return find("id",id).firstResult();
  }
}
