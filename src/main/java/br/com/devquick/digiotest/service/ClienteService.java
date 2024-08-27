package br.com.devquick.digiotest.service;

import br.com.devquick.digiotest.model.dto.ClienteFielDTO;
import br.com.devquick.digiotest.model.response.ClienteResponse;
import br.com.devquick.digiotest.repository.ClienteRepository;
import br.com.devquick.digiotest.utils.ClienteMapper;
import br.com.devquick.digiotest.utils.comparator.ClienteFielComparator;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Limit;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ClienteService {

  private final ClienteRepository clienteRepository;

  private final ClienteMapper clienteMapper;

  public List<ClienteResponse> findClientesFieis() {
    List<ClienteFielDTO> clientes = clienteRepository.findClientesFieis(Limit.of(3));
    clientes.sort(new ClienteFielComparator());
    return clienteMapper.toResponseList(clientes);
  }

}
