package org.demo.service;

import org.demo.dto.ClientDto;

import java.util.List;

public interface ClientService {
  List<ClientDto> geAllClient();
  ClientDto getClientById(Long id);
}
