package br.com.devquick.digiotest.model.dto;

import java.util.List;
import lombok.Data;

@Data
public class JsonClienteDTO {

  private String nome;

  private String cpf;

  private List<JsonCompraDTO> compras;

  @Data
  public class JsonCompraDTO {

    private String codigo;

    private int quantidade;

  }
}
