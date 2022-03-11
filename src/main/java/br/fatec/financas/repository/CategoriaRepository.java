package br.fatec.financas.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.fatec.financas.model.Categoria;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoriaRepository extends JpaRepository<Categoria, Long> {

}
