package br.com.devquick.digiotest.controller;

import br.com.devquick.digiotest.model.response.CompraResponse;
import br.com.devquick.digiotest.service.CompraService;
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
public class CompraController {

  private final CompraService compraService;

  /**
   * {@code GET /compras} : retrieve all compras ordered by total value.
   */
  @GetMapping("/compras")
  @ResponseStatus(HttpStatus.OK)
  public List<CompraResponse> getCompras() {
    return compraService.findComprasOrderByTotal();
  }

  /**
   * {@code GET /maior-compra} : retrieve biggest compra by total value.
   *
   * @param ano The ano of compra
   */
  @GetMapping("/maior-compra/{ano}")
  @ResponseStatus(HttpStatus.OK)
  public CompraResponse getMaiorCompra(@PathVariable Integer ano) throws CompraNotFoundException {
    return compraService.findMaiorCompraByAno(ano);
  }

}
