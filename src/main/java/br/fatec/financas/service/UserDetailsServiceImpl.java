package br.fatec.financas.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.fatec.financas.model.Cliente;
import br.fatec.financas.repository.ClienteRepository;
import br.fatec.financas.security.UserDetailsImpl;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private ClienteRepository repository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Cliente cliente = repository.findByLogin(username);
		if (cliente == null) {
			throw new UsernameNotFoundException(username);
		}
		return new UserDetailsImpl(cliente.getId(), cliente.getLogin(),
				cliente.getSenha(), cliente.getPerfis());
	}

}
