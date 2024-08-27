package br.com.devquick.digiotest.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import br.com.devquick.digiotest.model.response.ClienteResponse;
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
  void testClientFieisSucess() {
    List<ClienteResponse> response = clienteService.findClientesFieis();
    assertEquals(3, response.size());
    assertEquals("Geraldo Pedro Julio Nascimento", response.get(0).getNome());
    assertEquals("Teresinha Daniela Galvão", response.get(1).getNome());
    assertEquals("Vitória Alícia Mendes", response.get(2).getNome());
  }

}
