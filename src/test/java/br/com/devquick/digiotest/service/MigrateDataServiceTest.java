package br.com.devquick.digiotest.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import br.com.devquick.digiotest.model.Cliente;
import br.com.devquick.digiotest.repository.ClienteRepository;
import br.com.devquick.digiotest.utils.exception.ImportFileException;
import java.nio.file.Paths;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
class MigrateDataServiceTest {

  @Autowired
  private ClienteRepository clienteRepository;

  @Autowired
  private MigrateDataService migrateDataService;


  @DisplayName("Should Migrate all data from files")
  @Test
  void testDataMigrated() {
    Optional<Cliente> user = clienteRepository.findById(1L);

    assertNotNull(user);
    assertTrue(user.isPresent());
    assertEquals("Geraldo Pedro Julio Nascimento", user.get().getNome());
    assertEquals("05870189179", user.get().getCpf());
  }

  @DisplayName("Should throw exception when trying to import from a empty cliente file")
  @Test
  void testIOExceptionOnWrongClienteFile() {
    ImportFileException exception = assertThrows(ImportFileException.class,
        () -> migrateDataService.migrateClienteJsonToDatabase(Paths.get("")));

    assertEquals("Erro ao criar dados de clientes no banco!", exception.getMessage());
  }

  @DisplayName("Should throw exception when trying to import from a empty produto file")
  @Test
  void testIOExceptionOnWrongProdutoFile() {
    ImportFileException exception = assertThrows(ImportFileException.class,
        () -> migrateDataService.migrateProdutoJsonToDatabase(Paths.get("")));

    assertEquals("Erro ao criar dados de produtos no banco!", exception.getMessage());
  }
}
