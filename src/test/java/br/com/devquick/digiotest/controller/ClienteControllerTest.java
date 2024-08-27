package br.com.devquick.digiotest.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import br.com.devquick.digiotest.model.response.ClienteResponse;
import br.com.devquick.digiotest.service.ClienteService;
import br.com.devquick.digiotest.utils.exception.CompraNotFoundException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
class ClienteControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private ClienteService clienteService;

  private List<ClienteResponse> expectedClientes;

  @BeforeEach
  public void beforEach() {
    var objectMapper = new ObjectMapper();
    try (var inputStream = getClass().getClassLoader().getResourceAsStream("clientesFieis.json")) {
      expectedClientes = objectMapper.readValue(inputStream,
          new TypeReference<>() {
          });
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  @AfterEach
  public void afterEach() {
    expectedClientes = null;
  }

  @DisplayName("Should return 200 when it is success")
  @Test
  void testSuccessClienteFieis() throws Exception {
    when(clienteService.findClientesFieis())
        .thenReturn(expectedClientes);

    mockMvc.perform(get("/clientes-fieis")
            .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().is2xxSuccessful());
  }

  @DisplayName("Should return 500 when it has a generic Exception")
  @Test
  void testGenericException() throws Exception {
    when(clienteService.findClientesFieis())
        .thenThrow(new RuntimeException());

    mockMvc.perform(get("/clientes-fieis")
            .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isInternalServerError());
  }

  @DisplayName("Should return 404 when does not find Entity Compra")
  @Test
  void testEntityCompraNotFound() throws Exception {
    when(clienteService.findWineRecommendation("12345678900"))
        .thenThrow(new CompraNotFoundException("TEST"));

    mockMvc.perform(get("/recomendacao/cliente/12345678900/tipo")
            .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isNotFound());
  }

  @DisplayName("Should return 500 when it has a generic Exception")
  @Test
  void testGenericRecommendationException() throws Exception {
    when(clienteService.findWineRecommendation(any()))
        .thenThrow(new RuntimeException());

    mockMvc.perform(get("/recomendacao/cliente/12345678900/tipo")
            .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isInternalServerError());
  }

}
