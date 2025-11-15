package org.demo.repository;

import org.demo.dto.ClientDto;
import java.util.List;

public interface ClientRepository {
  List<ClientDto> getAllClient();

  ClientDto getClientById(Long id);
}
