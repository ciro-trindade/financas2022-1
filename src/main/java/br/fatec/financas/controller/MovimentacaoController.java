package br.fatec.financas.controller;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
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

import br.fatec.financas.dto.MovimentacaoDTO;
import br.fatec.financas.exception.AuthorizationException;
import br.fatec.financas.service.MovimentacaoService;

@RestController
@RequestMapping("/movimentacoes")
public class MovimentacaoController implements ControllerInterface<MovimentacaoDTO> {

	@Autowired
	private MovimentacaoService service;

	@Override
	@GetMapping
	public ResponseEntity<List<MovimentacaoDTO>> getAll() {
		return ResponseEntity.ok(service.findAll());
	}

	@Override
	@GetMapping("/{id}")
	public ResponseEntity<?> get(@PathVariable("id") Long id) {
		MovimentacaoDTO obj = service.findById(id);
		if (obj != null) {
			return ResponseEntity.ok(obj);
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
	}

	@Override
	@PostMapping
	public ResponseEntity<MovimentacaoDTO> post(@Valid @RequestBody MovimentacaoDTO obj) throws URISyntaxException {
		try {
			MovimentacaoDTO dto = service.create(obj);
			URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(dto.getId())
					.toUri();
			return ResponseEntity.created(location).body(dto);
		} catch (AuthorizationException e) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}
	}

	@Override
	@PutMapping
	public ResponseEntity<?> put(@Valid @RequestBody MovimentacaoDTO obj) {
		try {
			if (service.update(obj)) {
				return ResponseEntity.ok(obj);
			}
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		} catch (AuthorizationException e) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}
	}

	@Override
	@DeleteMapping("/{id}")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<?> delete(@PathVariable("id") Long id) {
		if (service.delete(id)) {
			return ResponseEntity.ok().build();
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
	}

	@GetMapping("/cliente/{id}")
	public ResponseEntity<?> getMovimentacoesCliente(@PathVariable("id") Long clienteId) {
		try {
			return ResponseEntity.ok(service.movimentacoesDoCliente(clienteId));
		} catch (AuthorizationException e) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}
	}

	@GetMapping("/conta/{id}")
	public ResponseEntity<?> getMovimentacoesConta(@PathVariable("id") Long contaId) {
		try {
			return ResponseEntity.ok(service.movimentacoesDaConta(contaId));
		} catch (AuthorizationException e) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}
	}
}
