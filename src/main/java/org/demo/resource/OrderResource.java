package org.demo.resource;


import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.demo.dto.ClientDto;
import org.demo.dto.OrderRequest;
import org.demo.entity.CustomerOrder;

import java.util.List;

@Path("/api/orders")
public interface OrderResource {

  @POST
  @Produces(MediaType.APPLICATION_JSON)
  Response postOrder(OrderRequest order);

  @GET
  @Produces(MediaType.APPLICATION_JSON)
  List<CustomerOrder> getAllOrders();

  @GET
  @Path("/{id}")
  @Produces(MediaType.APPLICATION_JSON)
  CustomerOrder getOrderById(Long id);

  @POST
  @Path("/{id}/confirm")
  @Produces(MediaType.APPLICATION_JSON)
  CustomerOrder getConfirmOrder(Long id);

  @GET
  @Path("/clients")
  @Produces(MediaType.APPLICATION_JSON)
  List<ClientDto> getAllClients();

}
