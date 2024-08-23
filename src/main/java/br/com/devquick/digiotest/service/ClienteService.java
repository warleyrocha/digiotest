package br.com.devquick.digiotest.service;

import br.com.devquick.digiotest.model.dto.ClienteFielDTO;
import br.com.devquick.digiotest.model.response.ClienteResponse;
import br.com.devquick.digiotest.repository.ClienteRepository;
import br.com.devquick.digiotest.utils.ClienteMapper;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ClienteService {

  private final ClienteRepository clienteRepository;

  private final ClienteMapper clienteMapper;

  public List<ClienteResponse> findClientesFieis() {
    List<ClienteFielDTO> clientes = clienteRepository.findClientesFieis();
    BigDecimal avarageValue = getAvarageValue(clientes);
    Map<Long, AggregatedData> result = clientes.stream()
        .collect(Collectors.toMap(
            ClienteFielDTO::getId,
            fiel -> new AggregatedData(1, fiel.getTotal()),
            (existing, replacement) -> {
              existing.addValue(replacement.getSum(), avarageValue);
              return existing;
            }
        ));

    List<Long> sortedResult = result.entrySet().stream()
        .sorted((e1, e2) -> {
          int totalComparison = Integer.compare(e2.getValue().getCount(), e1.getValue().getCount());
          // First sort by sum in descending order
          if (totalComparison != 0) {
            return totalComparison;
          }
          // If sums are equal, sort by count in descending order
          return e2.getValue().getSum().compareTo(e1.getValue().getSum());
        }).map(Entry::getKey)
        .collect(Collectors.toList());
    return clienteMapper.toResponseList(clienteRepository.findAllById(sortedResult.subList(0, 3)));
  }

  private BigDecimal getAvarageValue(List<ClienteFielDTO> clientes) {
    BigDecimal totalSum = clientes.stream()
        .map(ClienteFielDTO::getTotal)
        .reduce(BigDecimal.ZERO, BigDecimal::add);

    return !clientes.isEmpty() ? totalSum.divide(
        BigDecimal.valueOf(clientes.size()), RoundingMode.UP).setScale(2, RoundingMode.UP)
        : BigDecimal.ZERO;

  }

  @Getter
  private class AggregatedData {

    private int count;
    private BigDecimal sum;

    public AggregatedData(int count, BigDecimal sum) {
      this.count = count;
      this.sum = sum;
    }

    public void addValue(BigDecimal value, BigDecimal avarageValue) {
      if (value.compareTo(avarageValue) > 0) {
        this.sum = this.sum.add(value);
        this.count++;
      }
    }
  }
}
