package org.demo.validator;

import io.quarkus.logging.Log;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.demo.dto.ClientDto;
import org.demo.service.ClientService;
import org.eclipse.microprofile.faulttolerance.Fallback;
import org.eclipse.microprofile.faulttolerance.Retry;

@ApplicationScoped
public class ClientValidator {

  @Inject
  ClientService clientService;

  @Retry(maxRetries = 2, delay = 1000)
  @Fallback(fallbackMethod = "fallbackValidateClient")
  public ClientDto validateClient(Long clientId) {
    return clientService.getClientById(clientId);
  }

  public ClientDto fallbackValidateClient(Long clientId) {
    Log.infof("Fallback activado: client-service no disponible para clientId: %s", clientId);
    return null;
  }
}