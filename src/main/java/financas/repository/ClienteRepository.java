package financas.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import financas.model.Cliente;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long>{

	Cliente findByLogin(String login);
	
	@Query("select cl from Cliente cl join cl.conta c where c.id = ?1")
	Cliente findByConta(Long contaId);
}
