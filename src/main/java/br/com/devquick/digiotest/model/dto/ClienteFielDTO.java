package br.com.devquick.digiotest.model.dto;

import java.math.BigDecimal;
import java.math.RoundingMode;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class ClienteFielDTO {

  private Long id;

  private String nome;

  private String cpf;

  private Integer amount;

  private BigDecimal total;

  public ClienteFielDTO(Long id, String nome, String cpf, Long amount, BigDecimal total) {
    this.id = id;
    this.nome = nome;
    this.cpf = cpf;
    this.amount = amount.intValue();
    this.total = total.setScale(2, RoundingMode.UP);
  }
}
