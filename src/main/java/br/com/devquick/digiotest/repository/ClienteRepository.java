package br.com.devquick.digiotest.repository;

import br.com.devquick.digiotest.model.Cliente;
import br.com.devquick.digiotest.model.dto.ClienteFielDTO;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {

  @Query("""
      SELECT new br.com.devquick.digiotest.model.dto.ClienteFielDTO(
         cliente.id,
         cliente.nome,
         cliente.cpf,
         COUNT(compra.id) AS compraAmount,
         SUM(produto.preco * compra.quantidade) AS totalPreco)
       FROM Compra compra
       JOIN compra.cliente cliente
       JOIN compra.produto produto
       GROUP BY cliente.id, cliente.nome, cliente.cpf
      """)
  List<ClienteFielDTO> findClientesFieis();

}
