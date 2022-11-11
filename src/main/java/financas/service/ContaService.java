package financas.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import financas.exception.AuthorizationException;
import financas.model.Cliente;
import financas.model.Conta;
import financas.repository.ClienteRepository;
import financas.repository.ContaRepository;
import financas.security.JWTUtil;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Service
public class ContaService implements ServiceInterface<Conta> {

	@Autowired
	private ContaRepository repository;
	
	@Autowired
	private ClienteRepository clienteRepo;
	
	@Autowired
	private JWTUtil jwtUtil;
	
	@Override
	public Conta create(Conta conta) {
		Conta obj = repository.save(conta);
		return obj;
	}

	@Override
	public List<Conta> findAll() {
		return repository.findAll();
	}

	public Page<Conta> findAll(Pageable pageable) {
		return repository.findAll(pageable);
	}
	
	@Override
	public Conta findById(Long id) throws AuthorizationException {
		Cliente cliente = clienteRepo.findByConta(id);
		if (cliente != null && !jwtUtil.authorized(cliente.getId())) {
			throw new AuthorizationException("Acesso negado!"); 
		}
		Optional<Conta> obj = repository.findById(id);
		if (obj.isPresent()) {
			return obj.get();
		}
		return null;
	}

	
	@Override
	public boolean update(Conta conta) throws AuthorizationException {
		Cliente cliente = clienteRepo.findByConta(conta.getId());
		if (cliente != null && !jwtUtil.authorized(cliente.getId())) {
			throw new AuthorizationException("Acesso negado!"); 
		}
		if (repository.existsById(conta.getId())) {
			repository.save(conta);
			return true;
		}
		return false;
	}

	@Override
	public boolean delete(Long id) {
		if (repository.existsById(id)) {
			repository.deleteById(id);			
			return true;
		}
		return false;
	}

	public List<Conta> listarPorAgencia(Integer agencia) {
		return repository.listarPorAgencia(agencia);
	}

	public List<Conta> listarPorAgenciaESaldo(Integer agencia, Float from, Float to) {
		return repository.findByAgenciaAndSaldoBetween(agencia, from, to);
	}

	public List<Conta> listarPorNomeCliente(String nome) {
		return repository.listarPorNomeCliente(nome);
	}
}
