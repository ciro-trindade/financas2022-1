package br.fatec.financas.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.fatec.financas.dto.PessoaFisicaDTO;
import br.fatec.financas.mapper.PessoaFisicaMapper;
import br.fatec.financas.model.PessoaFisica;
import br.fatec.financas.repository.PessoaFisicaRepository;

@Service
public class PessoaFisicaService implements ServiceInterface<PessoaFisicaDTO> {

	@Autowired
	private PessoaFisicaRepository repository;
	
	@Autowired
	private PessoaFisicaMapper mapper;
	
	@Override
	public PessoaFisicaDTO create(PessoaFisicaDTO obj) {
		PessoaFisica pf = repository.save(mapper.toEntity(obj));
		return mapper.toDTO(pf);
	}

	@Override
	public PessoaFisicaDTO findById(Long id) {
		Optional<PessoaFisica> obj = repository.findById(id);
		if (obj.isPresent()) {
			return mapper.toDTO(obj.get());
		}
		return null;
	}

	@Override
	public List<PessoaFisicaDTO> findAll() {
		return mapper.toDTO(repository.findAll());
	}

	@Override
	public boolean update(PessoaFisicaDTO obj) {
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
