package br.fatec.financas.service;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.fatec.financas.dto.ContaDTO;
import br.fatec.financas.mapper.ContaMapper;
import br.fatec.financas.model.Conta;
import br.fatec.financas.repository.ContaRepository;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Service
public class ContaService implements ServiceInterface<ContaDTO> {

	@Autowired
	private ContaRepository repository;

	@Autowired
	private ContaMapper mapper;
	
	@Override
	public ContaDTO create(ContaDTO conta) {
		Conta obj = repository.save(mapper.toEntity(conta));
		return mapper.toDTO(obj);
	}

	@Override
	public List<ContaDTO> findAll() {
		return mapper.toDTO(repository.findAll());
	}

	public Page<ContaDTO> findAll(Pageable pageable) {
		Page<Conta> contas = repository.findAll(pageable);
		Page<ContaDTO> dto = contas.map(new Function<Conta, ContaDTO>() {
			
			@Override
			public ContaDTO apply(Conta conta) {
				return mapper.toDTO(conta);
			}
		});
		return dto;
	}
	
	@Override
	public ContaDTO findById(Long id) {
		Optional<Conta> obj = repository.findById(id);
		if (obj.isPresent()) {
			return mapper.toDTO(obj.get());
		}
		return null;
	}

	
	@Override
	public boolean update(ContaDTO conta) {
		if (repository.existsById(conta.getId())) {
			repository.save(mapper.toEntity(conta));
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
		ContaDTO _conta = findById(id);
		if (_conta != null) {
			_conta.setSaldo(_conta.getSaldo() + valor);
			update(_conta);
			return _conta.getSaldo();
		}
		return null;
	}

	public Float sacar(Long id, Float valor) throws IllegalArgumentException {
		ContaDTO _conta = findById(id);
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

	public List<ContaDTO> listarPorAgencia(Integer agencia) {
		return mapper.toDTO(repository.listarPorAgencia(agencia));
	}

	public List<ContaDTO> listarPorAgenciaESaldo(Integer agencia, Float from, Float to) {
		return mapper.toDTO(repository.findByAgenciaAndSaldoBetween(agencia, from, to));
	}

	public List<ContaDTO> listarPorNomeCliente(String nome) {
		return mapper.toDTO(repository.listarPorNomeCliente(nome));
	}
}
