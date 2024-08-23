package br.com.devquick.digiotest.utils;

import br.com.devquick.digiotest.model.Cliente;
import br.com.devquick.digiotest.model.Compra;
import br.com.devquick.digiotest.model.Produto;
import br.com.devquick.digiotest.model.dto.JsonClienteDTO;
import br.com.devquick.digiotest.model.dto.JsonClienteDTO.JsonCompraDTO;
import br.com.devquick.digiotest.model.dto.JsonProdutoDTO;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface DataMapper {

  List<Cliente> toListCliente(List<JsonClienteDTO> jsonClienteDTOS);

  @Mapping(target = "id", ignore = true)
  Cliente toClient(JsonClienteDTO jsonClienteDTO);

  @Mappings({
      @Mapping(target = "tipoVinho", ignore = true),
      @Mapping(target = "preco", ignore = true),
      @Mapping(target = "safra", ignore = true),
      @Mapping(target = "ano", ignore = true),
  })
  Produto toProduto(String codigo);

  @Mappings({
      @Mapping(target = "id", ignore = true),
      @Mapping(target = "cliente", ignore = true),
      @Mapping(target = "produto", source = "codigo")
  })
  Compra toCompra(JsonCompraDTO jsonCompraDTO);

  List<Produto> toListProduto(List<JsonProdutoDTO> jsonProdutos);

  Produto toProduto(JsonProdutoDTO jsonProduto);
}
