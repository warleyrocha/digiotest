package br.com.devquick.digiotest.repository;

import br.com.devquick.digiotest.model.Produto;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {

  Produto findByCodigo(String codigo);

  List<Produto> findByTipoVinho(String tipoVinho);

}
