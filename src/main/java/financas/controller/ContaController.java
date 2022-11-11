package financas.controller;

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

import financas.dto.ContaDTO;
import financas.exception.AuthorizationException;
import financas.mapper.ContaMapper;
import financas.model.Conta;
import financas.service.ContaService;
import io.swagger.v3.oas.annotations.Hidden;

@Hidden
@RestController
@RequestMapping("/contas")
public class ContaController implements ControllerInterface<ContaDTO> {

	@Autowired
	private ContaService service;
	
	@Autowired
	private ContaMapper mapper;

	@Override
	@GetMapping
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<List<ContaDTO>> getAll() {
		return ResponseEntity.ok(mapper.toDTO(service.findAll()));
	}

	@GetMapping("/page")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<Page<ContaDTO>> getAll(Pageable pageable) {
		return ResponseEntity.ok(mapper.toDTO(service.findAll(pageable)));
	}

	@Override
	@GetMapping("/{id}")
	public ResponseEntity<ContaDTO> get(@PathVariable("id") Long id) {
		try {
			Conta conta = service.findById(id);
			if (conta != null) {
				return ResponseEntity.ok(mapper.toDTO(conta));
			}
			return ResponseEntity.notFound().build();
		} catch (AuthorizationException e) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}
	}

	@Override
	@PostMapping
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<ContaDTO> post(@Valid @RequestBody ContaDTO obj) throws URISyntaxException {
		Conta conta = service.create(mapper.toEntity(obj));
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(conta.getId())
				.toUri();
		return ResponseEntity.created(location).body(mapper.toDTO(conta));
	}

	@Override
	@PutMapping
	public ResponseEntity<ContaDTO> put(@Valid @RequestBody ContaDTO conta) {
		try {
			if (service.update(mapper.toEntity(conta))) {
				return ResponseEntity.ok(conta);
			}
			return ResponseEntity.notFound().build();
		} catch (AuthorizationException e) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}
	}

	@Override
	@DeleteMapping("/{id}")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		if (service.delete(id)) {
			return ResponseEntity.noContent().build();
		}
		return ResponseEntity.notFound().build();
	}


	@GetMapping(value = "/agencia/{agencia}")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<List<ContaDTO>> getByAgencia(@PathVariable("agencia") Integer agencia) {
		return ResponseEntity.ok(mapper.toDTO(service.listarPorAgencia(agencia)));
	}

	@GetMapping(value = "/agencia/{agencia}/{from}/{to}")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<List<ContaDTO>> getByAgenciaESaldo(@PathVariable("agencia") Integer agencia,
			@PathVariable("from") Float from, @PathVariable("to") Float to) {
		return ResponseEntity.ok(mapper.toDTO(service.listarPorAgenciaESaldo(agencia, from, to)));
	}

	@GetMapping(value = "/cliente/{nome}")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<List<ContaDTO>> getByNomeCliente(@PathVariable("nome") String nome) {
		return ResponseEntity.ok(mapper.toDTO(service.listarPorNomeCliente(nome)));
	}
}
