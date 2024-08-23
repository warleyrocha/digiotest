package br.com.devquick.digiotest.repository;

import br.com.devquick.digiotest.model.Compra;
import br.com.devquick.digiotest.model.response.CompraResponse;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CompraRepository extends JpaRepository<Compra, Long> {

  @Query("""
      SELECT new br.com.devquick.digiotest.model.response.CompraResponse(
        cliente.nome, cliente.cpf, produto.codigo, produto.tipoVinho, produto.preco, produto.safra, produto.ano, compra.quantidade
      )
      FROM Compra compra
      JOIN compra.cliente cliente
      JOIN compra.produto produto
      """)
  List<CompraResponse> findResponseCompras();

  @Query("""
      SELECT new br.com.devquick.digiotest.model.response.CompraResponse(
        cliente.nome, cliente.cpf, produto.codigo, produto.tipoVinho, produto.preco, produto.safra, produto.ano, compra.quantidade
      )
      FROM Compra compra
      JOIN compra.cliente cliente
      JOIN compra.produto produto
      WHERE produto.ano = :ano
      """)
  List<CompraResponse> findResponseComprasByAno(Integer ano);


}