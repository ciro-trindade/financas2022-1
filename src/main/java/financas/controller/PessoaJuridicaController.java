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

import financas.dto.PessoaJuridicaDTO;
import financas.exception.AuthorizationException;
import financas.mapper.PessoaJuridicaMapper;
import financas.model.PessoaJuridica;
import financas.service.PessoaJuridicaService;

@RestController
@RequestMapping("/pessoas-juridicas")
public class PessoaJuridicaController implements ControllerInterface<PessoaJuridicaDTO> {

	@Autowired
	private PessoaJuridicaService service;
	
	@Autowired
	private PessoaJuridicaMapper mapper;

	@Override
	@GetMapping
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<List<PessoaJuridicaDTO>> getAll() {
		return ResponseEntity.ok(mapper.toDTO(service.findAll()));
	}

	@Override
	@GetMapping("/{id}")
	public ResponseEntity<PessoaJuridicaDTO> get(@PathVariable("id") Long id) {
		try {
			PessoaJuridica obj = service.findById(id);
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
	public ResponseEntity<PessoaJuridicaDTO> post(@Valid @RequestBody PessoaJuridicaDTO obj) throws URISyntaxException {
		PessoaJuridica pj = service.create(mapper.toEntity(obj));
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(pj.getId())
				.toUri();
		return ResponseEntity.created(location).body(mapper.toDTO(pj));
	}

	@Override
	@PutMapping
	public ResponseEntity<PessoaJuridicaDTO> put(@Valid @RequestBody PessoaJuridicaDTO obj) {
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
