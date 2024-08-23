package br.com.devquick.digiotest.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import br.com.devquick.digiotest.model.response.CompraResponse;
import br.com.devquick.digiotest.repository.CompraRepository;
import br.com.devquick.digiotest.utils.exception.CompraNotFoundException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class CompraServiceTest {

  @InjectMocks
  private CompraService compraService;

  @Mock
  private CompraRepository compraRepository;
  private List<CompraResponse> expectedCompras;

  @BeforeEach
  public void beforEach() {
    try (var inputStream = getClass().getClassLoader().getResourceAsStream("compras.json")) {
      var objectMapper = new ObjectMapper();
      expectedCompras = objectMapper.readValue(inputStream,
          new TypeReference<>() {
          });

    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  @AfterEach
  public void afterEach() {
    expectedCompras = null;
  }

  @DisplayName("Should throw exception when doesn't have the year")
  @Test
  void testCompraNotFoundException() {
    Mockito.when(compraRepository.findResponseComprasByAno(Mockito.any()))
        .thenReturn(new ArrayList<>());

    CompraNotFoundException exception = assertThrows(CompraNotFoundException.class,
        () -> compraService.findMaiorCompraByAno(1000));

    assertEquals("Não foi encontrada compra para o ano informado: 1000!", exception.getMessage());
  }

  @DisplayName("Should get biggest compra ordered")
  @Test
  void testMaiorCompraSuccess() throws CompraNotFoundException {
    Mockito.when(compraRepository.findResponseComprasByAno(Mockito.any()))
        .thenReturn(expectedCompras);

    CompraResponse response = compraService.findMaiorCompraByAno(2022);
    assertEquals(new BigDecimal("1133.94"), response.getTotal());
    assertEquals(2021, response.getSafra());
    assertEquals(2022, response.getAno());

  }

  @DisplayName("Should get all Compras ordered")
  @Test
  void testComprasSuccess() {
    Mockito.when(compraRepository.findResponseCompras())
        .thenReturn(expectedCompras);

    List<CompraResponse> response = compraService.findComprasOrderByTotal();
    assertEquals(new BigDecimal("386.97"), response.getFirst().getTotal());
    assertEquals(new BigDecimal("1133.94"), response.getLast().getTotal());

  }


}
