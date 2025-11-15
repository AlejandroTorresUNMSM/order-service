package org.demo.utils;

import io.quarkus.logging.Log;
import jakarta.ws.rs.NotFoundException;
import org.demo.dto.OrderRequest;
import org.demo.dto.OrderStatus;
import org.demo.entity.CustomerOrder;

public class Utils {

  public static void validateOrderConfirm(CustomerOrder order){
    validateOrder(order);
    validateStatusConfirm(order.orderStatus);
  }

  public static void validateOrder(CustomerOrder order){
    if(order == null) {
      throw new NotFoundException();
    }
  }

  public static void validateOrder(OrderRequest order){
    if(order == null) {
      throw new NotFoundException();
    }
  }

  public static void validateStatusCancelled(OrderStatus orderStatus){
    if(orderStatus.equals(OrderStatus.CANCELLED)){
      throw new RuntimeException("Orden se encuentra cancelada");
    }
  }

  public static void validateStatusConfirm(OrderStatus orderStatus){
    Log.info("validateStatusConfirm -> orderStatus: " + orderStatus);
    if(!OrderStatus.PENDING.equals(orderStatus)){
      throw new IllegalStateException("Orden no se encuentra pendiente");
    }
  }
}
