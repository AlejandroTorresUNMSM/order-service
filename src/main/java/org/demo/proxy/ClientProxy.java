package org.demo.proxy;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ClientProxy {
  public Long id;
  public String firstName;
  public String lastName;
  public String documentNumber;
  public String email;
}
