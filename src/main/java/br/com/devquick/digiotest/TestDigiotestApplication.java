package br.com.devquick.digiotest;

import br.com.devquick.digiotest.service.MigrateDataService;
import br.com.devquick.digiotest.utils.exception.ImportFileException;
import java.nio.file.Paths;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Profile;

@Profile("test")
@SpringBootApplication
@RequiredArgsConstructor
public class TestDigiotestApplication implements CommandLineRunner {

  private final MigrateDataService migrateDataService;

  @Override
  public void run(String... args) throws ImportFileException {
    migrateDataService.clearData();

    String produtoJsonFilePath = "src/test/resources/data/produtos.json";
    migrateDataService.migrateProdutoJsonToDatabase(Paths.get(produtoJsonFilePath));

    String clienteJsonFilePath = "src/test/resources/data/clientes.json";
    migrateDataService.migrateClienteJsonToDatabase(Paths.get(clienteJsonFilePath));
  }

}
