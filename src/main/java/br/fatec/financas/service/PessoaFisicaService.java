package br.fatec.financas.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import br.fatec.financas.dto.PessoaFisicaDTO;
import br.fatec.financas.exception.AuthorizationException;
import br.fatec.financas.mapper.PessoaFisicaMapper;
import br.fatec.financas.model.PessoaFisica;
import br.fatec.financas.repository.PessoaFisicaRepository;
import br.fatec.financas.security.JWTUtil;

@Service
public class PessoaFisicaService implements ServiceInterface<PessoaFisicaDTO> {

	@Autowired
	private PessoaFisicaRepository repository;
	
	@Autowired
	private PessoaFisicaMapper mapper;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	@Autowired
	private JWTUtil jwtUtil;
	
	@Override
	public PessoaFisicaDTO create(PessoaFisicaDTO obj) {
		obj.setSenha(passwordEncoder.encode(obj.getSenha()));
		PessoaFisica pf = repository.save(mapper.toEntity(obj));
		return mapper.toDTO(pf);
	}

	@Override
	public PessoaFisicaDTO findById(Long id) throws AuthorizationException {
		if (!jwtUtil.authorized(id)) {
			throw new AuthorizationException("Acesso negado!");
		}
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
	public boolean update(PessoaFisicaDTO obj) throws AuthorizationException {
		if (!jwtUtil.authorized(obj.getId())) {
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

}
