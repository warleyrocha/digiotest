package br.com.devquick.digiotest.service;

import br.com.devquick.digiotest.model.Cliente;
import br.com.devquick.digiotest.model.dto.JsonClienteDTO;
import br.com.devquick.digiotest.model.dto.JsonProdutoDTO;
import br.com.devquick.digiotest.repository.ClienteRepository;
import br.com.devquick.digiotest.repository.CompraRepository;
import br.com.devquick.digiotest.repository.ProdutoRepository;
import br.com.devquick.digiotest.utils.DataMapper;
import br.com.devquick.digiotest.utils.exception.ImportFileException;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class MigrateDataService {

  private final ClienteRepository clienteRepository;

  private final ProdutoRepository produtoRepository;

  private final CompraRepository compraRepository;

  private final JsonParserService jsonLoaderService;

  private final DataMapper dataMapper;

  @Transactional
  public void migrateClienteJsonToDatabase(Path jsonFilePath) throws ImportFileException {
    try {
      List<JsonClienteDTO> jsonUsers = jsonLoaderService.loadUsersFromJson(jsonFilePath);

      List<Cliente> clientes = dataMapper.toListCliente(jsonUsers);
      clientes.forEach(cliente -> {
        clienteRepository.save(cliente);
        cliente.getCompras().forEach(compra -> {
          compra.setCliente(cliente);
          compra.setProduto(produtoRepository.findByCodigo(compra.getProduto().getCodigo()));
          compraRepository.save(compra);
        });
      });
    } catch (IOException e) {
      throw new ImportFileException("Erro ao criar dados de clientes no banco!");
    }
  }

  public void migrateProdutoJsonToDatabase(Path jsonFilePath) throws ImportFileException {
    try {
      List<JsonProdutoDTO> jsonProdutos = jsonLoaderService.loadProdutosFromJson(jsonFilePath);
      produtoRepository.saveAll(dataMapper.toListProduto(jsonProdutos));
    } catch (IOException e) {
      throw new ImportFileException("Erro ao criar dados de produtos no banco!");
    }
  }

  public void clearData() {
    compraRepository.deleteAll();
    clienteRepository.deleteAll();
    produtoRepository.deleteAll();
  }
}