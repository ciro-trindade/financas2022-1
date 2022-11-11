package financas.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import financas.exception.AuthorizationException;
import financas.model.PessoaFisica;
import financas.repository.PessoaFisicaRepository;
import financas.security.JWTUtil;

@Service
public class PessoaFisicaService implements ServiceInterface<PessoaFisica> {

	@Autowired
	private PessoaFisicaRepository repository;
		
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	@Autowired
	private JWTUtil jwtUtil;
	
	@Override
	public PessoaFisica create(PessoaFisica obj) {
		obj.setSenha(passwordEncoder.encode(obj.getSenha()));
		return repository.save(obj);
	}

	@Override
	public PessoaFisica findById(Long id) throws AuthorizationException {
		if (!jwtUtil.authorized(id)) {
			throw new AuthorizationException("Acesso negado!");
		}
		Optional<PessoaFisica> obj = repository.findById(id);
		if (obj.isPresent()) {
			return obj.get();
		}
		return null;
	}

	@Override
	public List<PessoaFisica> findAll() {
		return repository.findAll();
	}

	@Override
	public boolean update(PessoaFisica obj) throws AuthorizationException {
		if (!jwtUtil.authorized(obj.getId())) {
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

}
