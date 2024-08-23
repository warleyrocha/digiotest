package br.com.devquick.digiotest.model.dto;

import java.math.BigDecimal;
import java.math.RoundingMode;
import lombok.Data;

@Data
public class ClienteFielDTO {

  private Long id;

  private Integer quantidade;

  private BigDecimal preco;

  private BigDecimal total;

  public ClienteFielDTO(Long id, Integer quantidade, BigDecimal preco) {
    this.id = id;
    this.quantidade = quantidade;
    this.preco = preco;
    this.total = preco.multiply(BigDecimal.valueOf(quantidade)).setScale(2, RoundingMode.UP);
  }
}
