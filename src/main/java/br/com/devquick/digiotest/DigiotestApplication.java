package br.com.devquick.digiotest;

import br.com.devquick.digiotest.service.MigrateDataService;
import br.com.devquick.digiotest.utils.exception.ImportFileException;
import java.nio.file.Paths;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@RequiredArgsConstructor
public class DigiotestApplication implements CommandLineRunner {

  private final MigrateDataService migrateDataService;

  public static void main(String[] args) {
    SpringApplication.run(DigiotestApplication.class, args);
  }

  @Override
  public void run(String... args) throws ImportFileException {
    String produtoJsonFilePath = "src/main/resources/data/produtos.json";
    migrateDataService.migrateProdutoJsonToDatabase(Paths.get(produtoJsonFilePath));

    String clienteJsonFilePath = "src/main/resources/data/clientes.json";
    migrateDataService.migrateClienteJsonToDatabase(Paths.get(clienteJsonFilePath));
  }

}
