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

import br.fatec.financas.dto.PessoaJuridicaDTO;
import br.fatec.financas.exception.AuthorizationException;
import br.fatec.financas.service.PessoaJuridicaService;

@RestController
@RequestMapping("/pessoas-juridicas")
public class PessoaJuridicaController implements ControllerInterface<PessoaJuridicaDTO> {

	@Autowired
	private PessoaJuridicaService service;

	@Override
	@GetMapping
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<List<PessoaJuridicaDTO>> getAll() {
		return ResponseEntity.ok(service.findAll());
	}

	@Override
	@GetMapping("/{id}")
	public ResponseEntity<?> get(@PathVariable("id") Long id) {
		try {
			PessoaJuridicaDTO obj = service.findById(id);
			if (obj != null) {
				return ResponseEntity.ok(obj);
			}
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		} catch (AuthorizationException e) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}
	}

	@Override
	@PostMapping
	public ResponseEntity<PessoaJuridicaDTO> post(@Valid @RequestBody PessoaJuridicaDTO obj) throws URISyntaxException {
		PessoaJuridicaDTO pj = service.create(obj);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(pj.getId())
				.toUri();
		return ResponseEntity.created(location).body(pj);
	}

	@Override
	@PutMapping
	public ResponseEntity<?> put(@Valid @RequestBody PessoaJuridicaDTO obj) {
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

}
