package financas.controller;

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

import financas.dto.PessoaFisicaDTO;
import financas.exception.AuthorizationException;
import financas.mapper.PessoaFisicaMapper;
import financas.model.PessoaFisica;
import financas.service.PessoaFisicaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("/pessoas-fisicas")
public class PessoaFisicaController implements ControllerInterface<PessoaFisicaDTO> {

	@Autowired
	private PessoaFisicaService service;
	
	@Autowired
	private PessoaFisicaMapper mapper;

	@Override
	@GetMapping
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<List<PessoaFisicaDTO>> getAll() {
		return ResponseEntity.ok(mapper.toDTO(service.findAll()));
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
	public ResponseEntity<PessoaFisicaDTO> get(@PathVariable("id") Long id) {
		try {
			PessoaFisica obj = service.findById(id);
			if (obj != null) {
				return ResponseEntity.ok(mapper.toDTO(obj));
			}
			return ResponseEntity.notFound().build();
		} catch (AuthorizationException e) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}
	}

	@Override
	@PostMapping
	public ResponseEntity<PessoaFisicaDTO> post(@RequestBody PessoaFisicaDTO obj) throws URISyntaxException {
		PessoaFisica pf = service.create(mapper.toEntity(obj));
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(pf.getId())
				.toUri();
		return ResponseEntity.created(location).body(mapper.toDTO(pf));
	}

	@Override
	@PutMapping
	public ResponseEntity<PessoaFisicaDTO> put(@Valid @RequestBody PessoaFisicaDTO obj) {
		try {
			if (service.update(mapper.toEntity(obj))) {
				return ResponseEntity.ok(obj);
			}
			return ResponseEntity.notFound().build();
		} catch (AuthorizationException e) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}
	}

	@Override
	@DeleteMapping("/{id}")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
		if (service.delete(id)) {
			return ResponseEntity.noContent().build();
		}
		return ResponseEntity.notFound().build();
	}

}
