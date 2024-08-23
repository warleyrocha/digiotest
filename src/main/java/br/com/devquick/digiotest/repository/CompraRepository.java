package br.com.devquick.digiotest.repository;

import br.com.devquick.digiotest.model.Compra;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompraRepository extends JpaRepository<Compra, Long> {

}
