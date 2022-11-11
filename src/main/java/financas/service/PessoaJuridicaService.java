package financas.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import financas.exception.AuthorizationException;
import financas.model.PessoaJuridica;
import financas.repository.PessoaJuridicaRepository;
import financas.security.JWTUtil;

@Service
public class PessoaJuridicaService implements ServiceInterface<PessoaJuridica> {

	@Autowired
	private PessoaJuridicaRepository repository;
		
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	@Autowired
	private JWTUtil jwtUtil;
	
	@Override
	public PessoaJuridica create(PessoaJuridica obj) {
		obj.setSenha(passwordEncoder.encode(obj.getSenha()));
	 	return repository.save(obj);
	}

	@Override
	public PessoaJuridica findById(Long id) throws AuthorizationException {
		if (!jwtUtil.authorized(id)) {
			throw new AuthorizationException("Acesso negado!");
		}
		Optional<PessoaJuridica> obj = repository.findById(id);
		if (obj.isPresent()) {
			return obj.get();
		}
		return null;
	}

	@Override
	public List<PessoaJuridica> findAll() {
		return repository.findAll();
	}

	@Override
	public boolean update(PessoaJuridica obj) throws AuthorizationException {
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
