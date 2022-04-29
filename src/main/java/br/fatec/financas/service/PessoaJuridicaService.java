package br.fatec.financas.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import br.fatec.financas.dto.PessoaJuridicaDTO;
import br.fatec.financas.mapper.PessoaJuridicaMapper;
import br.fatec.financas.model.PessoaJuridica;
import br.fatec.financas.repository.PessoaJuridicaRepository;

@Service
public class PessoaJuridicaService implements ServiceInterface<PessoaJuridicaDTO> {

	@Autowired
	private PessoaJuridicaRepository repository;
	
	@Autowired
	private PessoaJuridicaMapper mapper;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	@Override
	public PessoaJuridicaDTO create(PessoaJuridicaDTO obj) {
		obj.setSenha(passwordEncoder.encode(obj.getSenha()));
	 	PessoaJuridica pj =  repository.save(mapper.toEntity(obj));
		return mapper.toDTO(pj);
	}

	@Override
	public PessoaJuridicaDTO findById(Long id) {
		Optional<PessoaJuridica> obj = repository.findById(id);
		if (obj.isPresent()) {
			return mapper.toDTO(obj.get());
		}
		return null;
	}

	@Override
	public List<PessoaJuridicaDTO> findAll() {
		return mapper.toDTO(repository.findAll());
	}

	@Override
	public boolean update(PessoaJuridicaDTO obj) {
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
