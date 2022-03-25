package br.fatec.financas.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.fatec.financas.model.Conta;
import br.fatec.financas.repository.ContaRepository;

@Service
public class ContaService implements ServiceInterface<Conta> {

	@Autowired
	private ContaRepository repository;

	public ContaService() {
	}

	@Override
	public Conta create(Conta conta) {
		repository.save(conta);
		return conta;
	}

	@Override
	public List<Conta> findAll() {
		return repository.findAll();
	}

	public Page<Conta> findAll(Pageable pageable) {
		return repository.findAll(pageable);
	}
	
	@Override
	public Conta findById(Long id) {
		Optional<Conta> obj = repository.findById(id);
		return obj.orElse(null);
	}

	
	@Override
	public boolean update(Conta conta) {
		if (repository.existsById(conta.getId())) {
			repository.save(conta);
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

	public Float depositar(Long id, Float valor) {
		Conta _conta = findById(id);
		if (_conta != null) {
			_conta.setSaldo(_conta.getSaldo() + valor);
			update(_conta);
			return _conta.getSaldo();
		}
		return null;
	}

	public Float sacar(Long id, Float valor) throws IllegalArgumentException {
		Conta _conta = findById(id);
		if (_conta != null) {
			Float _saldo = _conta.getSaldo();
			if (_saldo >= valor) {
				_conta.setSaldo(_saldo - valor);
				update(_conta);
				return _conta.getSaldo();
			}
			throw new IllegalArgumentException("Saldo insuficiente");
		}
		return null;
	}

	public List<Conta> listarPorAgencia(Integer agencia) {
		return repository.listarPorAgencia(agencia);
		//return repository.findByAgencia(agencia);
	}

	public List<Conta> listarPorAgenciaESaldo(Integer agencia, Float from, Float to) {
		//return repository.listarPorAgenciaESaldo(agencia, from, to);
		return repository.findByAgenciaAndSaldoBetween(agencia, from, to);
	}

	public List<Conta> listarPorNomeCliente(String nome) {
		return repository.listarPorNomeCliente(nome);

	}
}
