package org.demo.mapper;

import org.demo.dto.OrderCreatedEvent;
import org.demo.dto.OrderRequest;
import org.demo.entity.CustomerOrder;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Mapper
public interface OrderMapper {
  OrderMapper INSTANCE = Mappers.getMapper(OrderMapper.class);

  CustomerOrder orderRequestToOrder(OrderRequest orderRequest);

  @Mapping(source = "id", target = "orderId")
  @Mapping(target = "createdAt", expression = "java(getNowAsString())")
  OrderCreatedEvent customerOrderToOrderCreatedEvent(CustomerOrder customerOrder);

  @Named("getNowAsString")
  default String getNowAsString() {
    return LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
  }
}
