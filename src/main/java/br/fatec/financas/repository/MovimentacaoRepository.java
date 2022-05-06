package br.fatec.financas.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.fatec.financas.model.Movimentacao;

@Repository
public interface MovimentacaoRepository extends JpaRepository<Movimentacao, Long> {

	@Query("select m from Movimentacao m join m.conta c join Cliente cl on cl.conta = c  where cl.id = ?1")
	public List<Movimentacao> findByCliente(Long clienteId);
	
	@Query("select m from Movimentacao m join m.conta c where c.id = ?1")
	public List<Movimentacao> findByConta(Long contaId);
}
