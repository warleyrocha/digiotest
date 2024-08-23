package br.com.devquick.digiotest.utils;

import br.com.devquick.digiotest.model.Cliente;
import br.com.devquick.digiotest.model.response.ClienteResponse;
import java.util.List;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ClienteMapper {

  List<ClienteResponse> toResponseList(List<Cliente> clientes);

  ClienteResponse toResponse(Cliente cliente);

}
