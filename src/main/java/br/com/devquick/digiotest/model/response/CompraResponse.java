package br.com.devquick.digiotest.model.response;

import java.math.BigDecimal;
import java.math.RoundingMode;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CompraResponse {

  private String nome;

  private String cpf;

  private String codigo;

  private String tipoVinho;

  private BigDecimal preco;

  private Integer safra;

  private Integer ano;

  private Integer quantidade;

  private BigDecimal total;

  public CompraResponse(String nome, String cpf, String codigo, String tipoVinho, BigDecimal preco,
      Integer safra, Integer ano, Integer quantidade) {
    this.nome = nome;
    this.cpf = cpf;
    this.codigo = codigo;
    this.tipoVinho = tipoVinho;
    this.preco = preco;
    this.safra = safra;
    this.ano = ano;
    this.quantidade = quantidade;
    this.total = preco.multiply(BigDecimal.valueOf(quantidade)).setScale(2, RoundingMode.UP);
  }

}
