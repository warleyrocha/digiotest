package br.com.devquick.digiotest.model.dto;

import com.google.gson.annotations.SerializedName;
import java.math.BigDecimal;
import lombok.Data;

@Data
public class JsonProdutoDTO {

  private String codigo;

  @SerializedName("tipo_vinho")
  private String tipoVinho;

  private BigDecimal preco;

  private Integer safra;

  @SerializedName("ano_compra")
  private Integer ano;
}
