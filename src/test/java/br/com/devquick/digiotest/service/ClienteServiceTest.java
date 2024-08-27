package br.com.devquick.digiotest.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import br.com.devquick.digiotest.model.Produto;
import br.com.devquick.digiotest.model.response.ClienteResponse;
import br.com.devquick.digiotest.utils.exception.CompraNotFoundException;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
class ClienteServiceTest {

  @Autowired
  private ClienteService clienteService;

  @DisplayName("Should return ordered Clientes")
  @Test
  void testClientFieisSuccess() {
    List<ClienteResponse> response = clienteService.findClientesFieis();
    assertEquals(3, response.size());
    assertEquals("Geraldo Pedro Julio Nascimento", response.get(0).getNome());
    assertEquals("Teresinha Daniela Galvão", response.get(1).getNome());
    assertEquals("Vitória Alícia Mendes", response.get(2).getNome());
  }

  @DisplayName("Should Recommend Wine by cliente cpf")
  @Test
  void testWineRecommendationSuccess() throws CompraNotFoundException {
    Produto wineRecommendation = clienteService.findWineRecommendation("05870189179");

    assertEquals("20", wineRecommendation.getCodigo());
    assertEquals(2020, wineRecommendation.getSafra());
  }

  @DisplayName("Should throw exception when doesn't have purchase from client")
  @Test
  void testCompraNotFoundException() {
    CompraNotFoundException exception = assertThrows(CompraNotFoundException.class,
        () -> clienteService.findWineRecommendation("12345678900"));

    assertEquals("Não existe nenhuma compra para o cliente informado!", exception.getMessage());
  }

}
