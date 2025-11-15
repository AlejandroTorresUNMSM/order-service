package org.demo.service.impl;


import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import org.demo.dto.ClientDto;
import org.demo.repository.ClientRepository;
import org.demo.service.ClientService;

import java.util.List;

@ApplicationScoped
@AllArgsConstructor
public class ClientServiceImpl implements ClientService {
  private ClientRepository clientRepository;

  @Override
  public List<ClientDto> geAllClient() {
    return clientRepository.getAllClient();
  }

  @Override
  public ClientDto getClientById(Long id) {
    return clientRepository.getClientById(id);
  }
}
