package org.demo.repository.impl;

import jakarta.enterprise.context.ApplicationScoped;
import org.demo.dto.ClientDto;
import org.demo.mapper.ClientMapper;
import org.demo.proxy.ApiProxy;
import org.demo.proxy.ClientProxy;
import org.demo.repository.ClientRepository;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import java.util.List;

@ApplicationScoped
public class ClientRepositoryImpl implements ClientRepository {
  private final ApiProxy apiProxy;

  public ClientRepositoryImpl(@RestClient ApiProxy apiProxy){
    this.apiProxy = apiProxy;
  }


  @Override
  public List<ClientDto> getAllClient() {
    List<ClientProxy> proxyPosts = apiProxy.getAllClient();
    return ClientMapper.INSTANCE.clientProxyListToclientDtoList(proxyPosts);
  }

  @Override
  public ClientDto getClientById(Long id) {
    ClientProxy clientProxy = apiProxy.getClientById(id);
    return ClientMapper.INSTANCE.clientProxyToClientDto(clientProxy);
  }

  
}
