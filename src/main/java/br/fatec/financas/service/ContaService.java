package br.fatec.financas.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import br.fatec.financas.model.Conta;

@Service
public class ContaService {
	private static List<Conta> contas = new ArrayList<>();

	public ContaService() {
	}

	public void create(Conta conta) {
		conta.setId(conta.generateId());
		contas.add(conta);
	}
	
	public List<Conta> findAll() {
		return contas;
	}
	
	public Conta find(Conta conta) {
		for (Conta c : contas) {
			if (c.equals(conta)) {
				return c;
			}
		}
		return null;
	}
	
	public Conta find(Long id) {
		return find(new Conta(id));
	}
	
	public boolean update(Conta conta) {
		Conta _conta = find(conta);
		if (_conta != null) {
			_conta.setAgencia(conta.getAgencia());
			_conta.setNumero(conta.getNumero());
			_conta.setTitular(conta.getTitular());
			_conta.setSaldo(conta.getSaldo());
			return true;
		}
		return false;
	}
	
	public boolean delete(Long id) {
		Conta _conta = find(id);
		if (_conta != null) {
			contas.remove(_conta);
			return true;
		}
		return false;
	}
	
}
