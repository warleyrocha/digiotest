package br.com.devquick.digiotest.repository;

import br.com.devquick.digiotest.model.Produto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {

}
