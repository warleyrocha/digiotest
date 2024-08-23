package br.com.devquick.digiotest.service;

import br.com.devquick.digiotest.model.response.CompraResponse;
import br.com.devquick.digiotest.repository.CompraRepository;
import br.com.devquick.digiotest.utils.exception.CompraNotFoundException;
import java.util.Comparator;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CompraService {

  private final CompraRepository compraRepository;

  public List<CompraResponse> findComprasOrderByTotal() {
    List<CompraResponse> compras = compraRepository.findResponseCompras();
    compras.sort(Comparator.comparing(CompraResponse::getTotal));
    return compras;
  }

  public CompraResponse findMaiorCompraByAno(Integer ano) throws CompraNotFoundException {
    List<CompraResponse> compras = compraRepository.findResponseComprasByAno(ano);
    compras.sort(Comparator.comparing(CompraResponse::getTotal).reversed());
    return compras.stream().findFirst().orElseThrow(
        () -> new CompraNotFoundException(
            String.format("Não foi encontrada compra para o ano informado: %s!", ano)));
  }
}
