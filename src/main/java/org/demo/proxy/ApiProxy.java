package org.demo.proxy;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import java.util.List;

@RegisterRestClient(configKey = "api-client")
@Path("/api/clients")
public interface ApiProxy {

  @GET
  List<ClientProxy> getAllClient();

  @GET
  @Path("/{id}")
  ClientProxy getClientById(@PathParam("id")Long id);

  @GET
  @Path("/count")
  @Produces(MediaType.APPLICATION_JSON)
  Long countAllClient();
}
