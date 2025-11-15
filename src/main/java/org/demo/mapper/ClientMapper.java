package org.demo.mapper;

import org.demo.dto.ClientDto;
import org.demo.proxy.ClientProxy;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface ClientMapper {
  ClientMapper INSTANCE = Mappers.getMapper(ClientMapper.class);

  ClientDto clientProxyToClientDto(ClientProxy clientProxy);
  List<ClientDto> clientProxyListToclientDtoList(List<ClientProxy> clientProxies);
}
