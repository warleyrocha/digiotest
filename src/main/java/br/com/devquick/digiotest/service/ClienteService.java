package br.com.devquick.digiotest.service;

import br.com.devquick.digiotest.model.Produto;
import br.com.devquick.digiotest.model.dto.ClienteFielDTO;
import br.com.devquick.digiotest.model.response.ClienteResponse;
import br.com.devquick.digiotest.repository.ClienteRepository;
import br.com.devquick.digiotest.repository.CompraRepository;
import br.com.devquick.digiotest.repository.ProdutoRepository;
import br.com.devquick.digiotest.utils.ClienteMapper;
import br.com.devquick.digiotest.utils.comparator.ClienteFielComparator;
import br.com.devquick.digiotest.utils.exception.CompraNotFoundException;
import java.util.List;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ClienteService {

  private final ClienteRepository clienteRepository;

  private final ProdutoRepository produtoRepository;

  private final CompraRepository compraRepository;

  private final ClienteMapper clienteMapper;

  public List<ClienteResponse> findClientesFieis() {
    List<ClienteFielDTO> clientes = clienteRepository.findClientesFieis();
    clientes.sort(new ClienteFielComparator());
    return clienteMapper.toResponseList(clientes.subList(0, 3));
  }

  public Produto findWineRecommendation(String cpf) throws CompraNotFoundException {
    List<Produto> allPurchasedWine = compraRepository.findClienteAllPurchasedWine(cpf);

    if (allPurchasedWine.isEmpty()) {
      throw new CompraNotFoundException("Não existe nenhuma compra para o cliente informado!");
    }

    String mostPurchasedWine = allPurchasedWine.stream()
        .collect(Collectors.groupingBy(Produto::getTipoVinho, Collectors.counting()))
        .entrySet()
        .stream()
        .max(Entry.comparingByValue())
        .map(Entry::getKey).orElseThrow(() -> new CompraNotFoundException(
            "Não foi possivel recuperar o vinho mais comprado!"));

    List<Produto> allWinesByType = produtoRepository.findByTipoVinho(mostPurchasedWine);

    return allWinesByType.stream()
        .filter(wine -> !allPurchasedWine.contains(wine))
        .findFirst()
        .orElseGet(() -> allWinesByType.stream().findAny().get());
  }
}
