package org.demo.utils;

import jakarta.ws.rs.NotFoundException;
import org.demo.entity.CustomerOrder;

public class Utils {

  public static void validateOrder(CustomerOrder order){
    if(order == null) {
      throw new NotFoundException();
    }
  }
}
