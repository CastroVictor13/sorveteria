package com.estoque.repository;

import com.estoque.model.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long> {

    List<Produto> findByQuantidadeLessThan(int quantidade);

    List<Produto> findByQuantidadeGreaterThan(int quantidade);

    List<Produto> findByCategoria(String categoria);

    List<Produto> findByNomeContainingIgnoreCase(String nome);

}
