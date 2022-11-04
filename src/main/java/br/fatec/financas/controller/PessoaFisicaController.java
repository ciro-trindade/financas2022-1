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

import br.fatec.financas.dto.PessoaFisicaDTO;
import br.fatec.financas.exception.AuthorizationException;
import br.fatec.financas.service.PessoaFisicaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("/pessoas-fisicas")
public class PessoaFisicaController implements ControllerInterface<PessoaFisicaDTO> {

	@Autowired
	private PessoaFisicaService service;

	@Override
	@GetMapping
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<List<PessoaFisicaDTO>> getAll() {
		return ResponseEntity.ok(service.findAll());
	}

	@Override
	@Operation(summary = "Devolve uma pessoa física dado seu identificador")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Retorna uma pessoa física dado seu id"),
			@ApiResponse(responseCode = "401", description = "Você não está autenticado na API"),
			@ApiResponse(responseCode = "403", description = "Você não tem permissão para executar essa operação"),
			@ApiResponse(responseCode = "404", description = "Id da pessoa física não foi encontrado")
	})
	@GetMapping(value = "/{id}", produces = "application/json")
	public ResponseEntity<?> get(@PathVariable("id") Long id) {
		try {
			PessoaFisicaDTO obj = service.findById(id);
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
	public ResponseEntity<PessoaFisicaDTO> post(@Valid @RequestBody PessoaFisicaDTO obj) throws URISyntaxException {
		PessoaFisicaDTO dto = service.create(obj);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(dto.getId())
				.toUri();
		return ResponseEntity.created(location).body(dto);
	}

	@Override
	@PutMapping
	public ResponseEntity<?> put(@Valid @RequestBody PessoaFisicaDTO obj) {
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
