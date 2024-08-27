package br.com.devquick.digiotest.controller;

import static org.hamcrest.Matchers.endsWith;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import br.com.devquick.digiotest.model.response.CompraResponse;
import br.com.devquick.digiotest.service.CompraService;
import br.com.devquick.digiotest.utils.exception.CompraNotFoundException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityNotFoundException;
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
class CompraControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private CompraService compraService;

  private List<CompraResponse> expectedCompras;

  private CompraResponse maiorCompra;

  @BeforeEach
  public void beforEach() {
    var objectMapper = new ObjectMapper();
    try (var inputStream = getClass().getClassLoader().getResourceAsStream("compras.json")) {
      expectedCompras = objectMapper.readValue(inputStream,
          new TypeReference<>() {
          });
    } catch (IOException e) {
      throw new RuntimeException(e);
    }

    try (var inputStream = getClass().getClassLoader().getResourceAsStream("maiorCompra.json")) {
      maiorCompra = objectMapper.readValue(inputStream,
          new TypeReference<>() {
          });
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  @AfterEach
  public void afterEach() {
    expectedCompras = null;
    maiorCompra = null;
  }

  @DisplayName("Should return 404 When does not find any Compra")
  @Test
  void testCompraOrderedNotFound() throws Exception {
    when(compraService.findMaiorCompraByAno(2021))
        .thenThrow(new CompraNotFoundException("TEST"));

    mockMvc.perform(get("/maior-compra/2021")
            .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isNotFound());
  }

  @DisplayName("Should return 404 When does not find Entity Compra")
  @Test
  void testEntityNotFound() throws Exception {
    when(compraService.findMaiorCompraByAno(2000))
        .thenThrow(new EntityNotFoundException("TEST"));

    mockMvc.perform(get("/maior-compra/2000")
            .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isNotFound());
  }

  @DisplayName("Should return 500 When it has a generic Exception")
  @Test
  void testGenericException() throws Exception {
    when(compraService.findMaiorCompraByAno(50))
        .thenThrow(new RuntimeException());

    mockMvc.perform(get("/maior-compra/50")
            .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isInternalServerError());
  }

  @DisplayName("Should get Maior Compra by Year")
  @Test
  void testCompraNotFoundException() throws Exception {
    when(compraService.findMaiorCompraByAno(2022))
        .thenReturn(maiorCompra);

    mockMvc.perform(get("/maior-compra/2022")
            .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(content().json("""
              {
               "nome": "Ian Joaquim Giovanni Santos",
               "cpf": "96718391344",
               "codigo": "15",
               "tipoVinho": "Chardonnay",
               "preco": 188.99,
               "safra": 2021,
               "ano": 2022,
               "quantidade": 6,
               "total": 1133.94
             }
            """));
  }

  @DisplayName("Should get Compras ordened")
  @Test
  void testCompraOrderedSuccess() throws Exception {
    when(compraService.findComprasOrderByTotal())
        .thenReturn(expectedCompras);

    mockMvc.perform(get("/compras")
            .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(content().string(endsWith("1133.94}]")));
  }

}
