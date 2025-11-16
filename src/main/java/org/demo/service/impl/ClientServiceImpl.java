package org.demo.service.impl;


import io.quarkus.logging.Log;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import lombok.AllArgsConstructor;
import org.demo.dto.ClientDto;
import org.demo.repository.ClientRepository;
import org.demo.service.ClientService;
import org.eclipse.microprofile.faulttolerance.Fallback;
import org.eclipse.microprofile.faulttolerance.Retry;

import java.util.List;

@ApplicationScoped
public class ClientServiceImpl implements ClientService {
  @Inject
  private ClientRepository clientRepository;

  @Override
  public List<ClientDto> geAllClient() {
    return clientRepository.getAllClient();
  }

  @Override
  public ClientDto getClientById(Long id) {
    return clientRepository.getClientById(id);
  }

  public ClientDto fallbackGetClientbyId(Long clientId) {
    Log.infof("Fallback activado: client-service no disponible para clientId: %s", clientId);
    return null;
  }
}
