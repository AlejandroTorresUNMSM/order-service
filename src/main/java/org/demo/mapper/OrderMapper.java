package org.demo.mapper;

import org.demo.dto.OrderRequest;
import org.demo.entity.CustomerOrder;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface OrderMapper {
  OrderMapper INSTANCE = Mappers.getMapper(OrderMapper.class);

  CustomerOrder orderRequestToOrder(OrderRequest orderRequest);
}
