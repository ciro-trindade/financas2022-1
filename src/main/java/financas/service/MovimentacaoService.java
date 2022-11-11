package financas.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import financas.exception.AuthorizationException;
import financas.model.Cliente;
import financas.model.Movimentacao;
import financas.repository.ClienteRepository;
import financas.repository.MovimentacaoRepository;
import financas.security.JWTUtil;
import lombok.NoArgsConstructor;

@Service
@NoArgsConstructor
public class MovimentacaoService implements ServiceInterface<Movimentacao>{
	
	@Autowired
	private MovimentacaoRepository repository;
	
	@Autowired
	private ClienteRepository clienteRepo;
		
	@Autowired
	private JWTUtil jwtUtil;
	
	@Override
	public Movimentacao create(Movimentacao obj) throws AuthorizationException {
		Cliente cliente = clienteRepo.findByConta(obj.getId());
		if (cliente != null && !jwtUtil.authorized(cliente.getId())) {
			throw new AuthorizationException("Acesso negado!");
		}
		return repository.save(obj);
	}
	
	@Override
	public List<Movimentacao> findAll() {
		return repository.findAll();
	}

	@Override
	public Movimentacao findById(Long id) throws AuthorizationException {
		Optional<Movimentacao> obj = repository.findById(id);
		if (obj.isPresent()) {
			Movimentacao m = (obj.get());
			Cliente cliente = clienteRepo.findByConta(m.getConta().getId());
			if (cliente != null && !jwtUtil.authorized(cliente.getId())) {
				throw new AuthorizationException("Acesso negado!");
			}
			return m;
		}
		return null;
	}
	
	@Override
	public boolean update(Movimentacao obj) throws AuthorizationException {
		Cliente cliente = clienteRepo.findByConta(obj.getId());
		if (cliente != null && !jwtUtil.authorized(cliente.getId())) {
			throw new AuthorizationException("Acesso negado!");

		}
		if (repository.existsById(obj.getId())) {
			repository.save(obj);
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
	
	public List<Movimentacao> movimentacoesDoCliente(Long clienteId) throws AuthorizationException {
		if (!jwtUtil.authorized(clienteId)) {
			throw new AuthorizationException("Acesso negado!");
		}
		return repository.findByCliente(clienteId);
	}
	
	public List<Movimentacao> movimentacoesDaConta(Long contaId) throws AuthorizationException {
		Cliente cliente = clienteRepo.findByConta(contaId);		
		if (cliente != null && !jwtUtil.authorized(cliente.getId())) {
			throw new AuthorizationException("Acesso negado!");
		}
		return repository.findByConta(contaId);
	}
}
