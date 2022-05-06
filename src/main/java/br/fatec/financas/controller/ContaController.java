package br.fatec.financas.controller;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.fatec.financas.dto.ContaDTO;
import br.fatec.financas.exception.AuthorizationException;
import br.fatec.financas.service.ContaService;

@RestController
@RequestMapping("/contas")
public class ContaController implements ControllerInterface<ContaDTO> {

	@Autowired
	private ContaService service;

	@Override
	@GetMapping
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<List<ContaDTO>> getAll() {
		return ResponseEntity.ok(service.findAll());
	}

	@GetMapping("/page")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<Page<ContaDTO>> getAll(Pageable pageable) {
		return ResponseEntity.ok(service.findAll(pageable));
	}

	@Override
	@GetMapping("/{id}")
	public ResponseEntity<?> get(@PathVariable("id") Long id) {
		try {
			ContaDTO _conta = service.findById(id);
			if (_conta != null) {
				return ResponseEntity.ok(_conta);
			}
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		} catch (AuthorizationException e) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}
	}

	@Override
	@PostMapping
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<ContaDTO> post(@Valid @RequestBody ContaDTO obj) throws URISyntaxException {
		ContaDTO dto = service.create(obj);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(dto.getId())
				.toUri();
		return ResponseEntity.created(location).body(dto);
	}

	@Override
	@PutMapping
	public ResponseEntity<?> put(@Valid @RequestBody ContaDTO conta) {
		try {
			if (service.update(conta)) {
				return ResponseEntity.ok(conta);
			}
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		} catch (AuthorizationException e) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}
	}

	@Override
	@DeleteMapping("/{id}")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<?> delete(@PathVariable Long id) {
		try {
			if (service.delete(id)) {
				return ResponseEntity.ok().build();
			}
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		} catch (IllegalArgumentException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
	}

	@PutMapping("/depositar/{id}/{valor}")
	public ResponseEntity<?> depositar(@PathVariable("id") Long id, @PathVariable("valor") Float valor) {
		try {
			Float _saldo = service.depositar(id, valor);
			if (_saldo != null) {
				return ResponseEntity.ok(_saldo);
			}
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		} catch (AuthorizationException e) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}
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
		} catch (AuthorizationException e) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}
	}

	@GetMapping(value = "/agencia/{agencia}")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<List<ContaDTO>> getByAgencia(@PathVariable("agencia") Integer agencia) {
		return ResponseEntity.ok(service.listarPorAgencia(agencia));
	}

	@GetMapping(value = "/agencia/{agencia}/{from}/{to}")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<List<ContaDTO>> getByAgenciaESaldo(@PathVariable("agencia") Integer agencia,
			@PathVariable("from") Float from, @PathVariable("to") Float to) {
		return ResponseEntity.ok(service.listarPorAgenciaESaldo(agencia, from, to));
	}

	@GetMapping(value = "/cliente/{nome}")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<List<ContaDTO>> getByNomeCliente(@PathVariable("nome") String nome) {
		return ResponseEntity.ok(service.listarPorNomeCliente(nome));
	}
}
