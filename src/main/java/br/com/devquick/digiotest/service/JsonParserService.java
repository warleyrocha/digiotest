package br.com.devquick.digiotest.service;

import br.com.devquick.digiotest.model.dto.JsonClienteDTO;
import br.com.devquick.digiotest.model.dto.JsonProdutoDTO;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Path;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class JsonParserService {

  private final Gson gson = new Gson();

  public List<JsonClienteDTO> loadUsersFromJson(Path filePath) throws IOException {
    return getJsonProdutoDTOS(filePath, JsonClienteDTO.class);
  }

  public List<JsonProdutoDTO> loadProdutosFromJson(Path filePath) throws IOException {
    return getJsonProdutoDTOS(filePath, JsonProdutoDTO.class);
  }

  private <T> List<T> getJsonProdutoDTOS(Path filePath, Class<T> dtoClass) throws IOException {
    try (FileReader reader = new FileReader(filePath.toFile())) {
      Type clienteListType = TypeToken.getParameterized(List.class, dtoClass).getType();
      return gson.fromJson(reader, clienteListType);
    }
  }
}
