package br.com.devquick.digiotest.repository;

import br.com.devquick.digiotest.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {

}
