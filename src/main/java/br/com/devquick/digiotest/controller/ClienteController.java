package br.com.devquick.digiotest.controller;

import br.com.devquick.digiotest.model.Produto;
import br.com.devquick.digiotest.model.response.ClienteResponse;
import br.com.devquick.digiotest.service.ClienteService;
import br.com.devquick.digiotest.utils.exception.CompraNotFoundException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class ClienteController {

  private final ClienteService clienteService;

  /**
   * {@code GET /clientes-fieis} : retrieve Top 3 Cliente with more compras and bigger value.
   */
  @GetMapping("/clientes-fieis")
  @ResponseStatus(HttpStatus.OK)
  public List<ClienteResponse> getClientesFieis() {
    return clienteService.findClientesFieis();
  }

  /**
   * {@code GET /recomendacao/cliente/tipo} : retrieve a wine suggestion based on the most bayed
   * wines.
   */
  @GetMapping("/recomendacao/cliente/{cpf}/tipo")
  @ResponseStatus(HttpStatus.OK)
  public Produto getRecommendation(@PathVariable String cpf) throws CompraNotFoundException {
    return clienteService.findWineRecommendation(cpf);
  }

}
