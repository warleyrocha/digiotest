package br.com.devquick.digiotest.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import java.math.BigDecimal;
import lombok.Data;

@Entity
@Data
public class Produto {

  @Id
  private String codigo;

  @Column(name = "tipo_vinho")
  private String tipoVinho;

  private BigDecimal preco;

  private Integer safra;

  @Column(name = "ano_compra")
  private Integer ano;

}
