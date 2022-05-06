package br.fatec.financas.service;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import br.fatec.financas.dto.ClienteDTO;
import br.fatec.financas.security.UserDetailsImpl;

@Service
public class ClienteService {
	
	public static UserDetailsImpl authenticated() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth != null) {
			return (UserDetailsImpl) auth.getPrincipal();
		}
		return null;
	}
	
	public ClienteDTO findByConta() {
		return null;
	}
}
