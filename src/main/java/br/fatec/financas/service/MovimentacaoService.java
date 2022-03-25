package br.fatec.financas.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.fatec.financas.dto.MovimentacaoDTO;
import br.fatec.financas.mapper.MovimentacaoMapper;
import br.fatec.financas.model.Movimentacao;
import br.fatec.financas.repository.MovimentacaoRepository;
import lombok.NoArgsConstructor;

@Service
@NoArgsConstructor
public class MovimentacaoService implements ServiceInterface<MovimentacaoDTO>{
	
	@Autowired
	private MovimentacaoRepository repository;
	
	@Autowired	
	private MovimentacaoMapper mapper;
	
	@Override
	public MovimentacaoDTO create(MovimentacaoDTO obj) {
		Movimentacao movimentacao = repository.save(mapper.toEntity(obj));
		return mapper.toDTO(movimentacao);
	}
	
	@Override
	public List<MovimentacaoDTO> findAll() {
		return mapper.toDTO(repository.findAll());
	}
	
	@Override
	public MovimentacaoDTO findById(Long id) {
		Optional<Movimentacao> obj = repository.findById(id);
		if (obj.isPresent()) {
			return mapper.toDTO(obj.get());
		}
		return null;
	}
	
	@Override
	public boolean update(MovimentacaoDTO obj) {
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
	
}
