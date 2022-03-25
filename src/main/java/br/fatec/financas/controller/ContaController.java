package br.fatec.financas.controller;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.fatec.financas.model.Conta;
import br.fatec.financas.service.ContaService;

@RestController
@RequestMapping("/contas")
public class ContaController implements ControllerInterface<Conta> {

	@Autowired
	private ContaService service;

	@Override
	@GetMapping
	public ResponseEntity<List<Conta>> getAll() {
		return ResponseEntity.ok(service.findAll());
	}

	@GetMapping("/page")
	public ResponseEntity<Page<Conta>> getAll(Pageable pageable) {
		return ResponseEntity.ok(service.findAll(pageable));
	}

	
	@Override
	@GetMapping("/{id}")
	public ResponseEntity<?> get(@PathVariable("id") Long id) {
		Conta _conta = service.findById(id);
		if (_conta != null) {
			return ResponseEntity.ok(_conta);
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
	}

	@Override
	@PostMapping
	public ResponseEntity<Conta> post(@RequestBody Conta conta) throws URISyntaxException {
		service.create(conta);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(conta.getId())
				.toUri();
		return ResponseEntity.created(location).body(conta);
	}

	@Override
	@PutMapping
	public ResponseEntity<?> put(@RequestBody Conta conta) {
		if (service.update(conta)) {
			return ResponseEntity.ok(conta);
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
	}

	@Override
	@DeleteMapping("/{id}")
	public ResponseEntity<?> delete(@PathVariable Long id) {
		if (service.delete(id)) {
			return ResponseEntity.ok().build();
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
	}

	@PutMapping("/depositar/{id}/{valor}")
	public ResponseEntity<?> depositar(@PathVariable("id") Long id, @PathVariable("valor") Float valor) {
		Float _saldo = service.depositar(id, valor);
		if (_saldo != null) {
			return ResponseEntity.ok(_saldo);
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
	}

	@PutMapping("/sacar/{id}/{valor}")
	public ResponseEntity<?> sacar(@PathVariable("id") Long id, @PathVariable("valor") Float valor) {
		try {
			Float _saldo = service.sacar(id, valor);
			if (_saldo != null) {
				return ResponseEntity.ok(_saldo);
			}
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		} catch (IllegalArgumentException e) {
			return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).build();
		}
	}

	@GetMapping(value = "/agencia/{agencia}")
	public ResponseEntity<List<Conta>> getByAgencia(@PathVariable("agencia") Integer agencia) {
		return ResponseEntity.ok(service.listarPorAgencia(agencia));
	}

	@GetMapping(value = "/agencia/{agencia}/{from}/{to}")
	public ResponseEntity<List<Conta>> getByAgenciaESaldo(@PathVariable("agencia") Integer agencia,
			@PathVariable("from") Float from, @PathVariable("to") Float to) {
		return ResponseEntity.ok(service.listarPorAgenciaESaldo(agencia, from, to));
	}

	@GetMapping(value = "/cliente/{nome}")
	public ResponseEntity<List<Conta>> getByNomeCliente(@PathVariable("nome") String nome) {
		return ResponseEntity.ok(service.listarPorNomeCliente(nome));
	}
}
