package br.fatec.financas.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import br.fatec.financas.dto.MovimentacaoDTO;
import br.fatec.financas.exception.AuthorizationException;
import br.fatec.financas.mapper.MovimentacaoMapper;
import br.fatec.financas.model.Cliente;
import br.fatec.financas.model.Movimentacao;
import br.fatec.financas.repository.ClienteRepository;
import br.fatec.financas.repository.MovimentacaoRepository;
import br.fatec.financas.security.JWTUtil;
import lombok.NoArgsConstructor;

@Service
@NoArgsConstructor
public class MovimentacaoService implements ServiceInterface<MovimentacaoDTO>{
	
	@Autowired
	private MovimentacaoRepository repository;
	
	@Autowired
	private ClienteRepository clienteRepo;
	
	@Autowired	
	private MovimentacaoMapper mapper;
	
	@Autowired
	private JWTUtil jwtUtil;
	
	@Override
	@PreAuthorize("hasAnyRole('ADMIN')")
	public MovimentacaoDTO create(MovimentacaoDTO obj) {
		Cliente cliente = clienteRepo.findByConta(obj.getId());
		if (cliente != null && !jwtUtil.authorized(cliente.getId())) {
			
		}
		Movimentacao movimentacao = repository.save(mapper.toEntity(obj));
		return mapper.toDTO(movimentacao);
	}
	
	@Override
	@PreAuthorize("hasAnyRole('ADMIN')")
	public List<MovimentacaoDTO> findAll() {
		return mapper.toDTO(repository.findAll());
	}

	@Override
	public MovimentacaoDTO findById(Long id) throws AuthorizationException {
		Optional<Movimentacao> obj = repository.findById(id);
		if (obj.isPresent()) {
			Movimentacao m = (obj.get());
			Cliente cliente = clienteRepo.findByConta(m.getConta().getId());
			if (cliente != null && !jwtUtil.authorized(cliente.getId())) {
				throw new AuthorizationException("Acesso negado!");
			}
			return mapper.toDTO(m);
		}
		return null;
	}
	
	@Override
	public boolean update(MovimentacaoDTO obj) throws AuthorizationException {
		Cliente cliente = clienteRepo.findByConta(obj.getId());
		if (cliente != null && !jwtUtil.authorized(cliente.getId())) {
			throw new AuthorizationException("Acesso negado!");

		}
		if (repository.existsById(obj.getId())) {
			repository.save(mapper.toEntity(obj));
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
	
	public List<MovimentacaoDTO> movimentacoesDoCliente(Long clienteId) throws AuthorizationException {
		if (!jwtUtil.authorized(clienteId)) {
			throw new AuthorizationException("Acesso negado!");
		}
		return mapper.toDTO(repository.findByCliente(clienteId));
	}
	
	public List<MovimentacaoDTO> movimentacoesDaConta(Long contaId) throws AuthorizationException {
		Cliente cliente = clienteRepo.findByConta(contaId);		
		if (cliente != null && !jwtUtil.authorized(cliente.getId())) {
			throw new AuthorizationException("Acesso negado!");
		}
		return mapper.toDTO(repository.findByConta(contaId));
	}
}
