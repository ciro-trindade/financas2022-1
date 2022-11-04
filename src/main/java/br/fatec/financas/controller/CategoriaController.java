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
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.fatec.financas.dto.CategoriaDTO;
import br.fatec.financas.mapper.CategoriaMapper;
import br.fatec.financas.model.Categoria;
import br.fatec.financas.service.CategoriaService;

@RestController
@RequestMapping("/categorias")
public class CategoriaController implements ControllerInterface<CategoriaDTO>{

	@Autowired
	private CategoriaService service;
	
	@Autowired
	private CategoriaMapper mapper;

	@Override
	@GetMapping
	public ResponseEntity<List<CategoriaDTO>> getAll() {
		return ResponseEntity.ok(mapper.toDTO(service.findAll()));
	}

	@Override
	@GetMapping("/{id}")
	public ResponseEntity<CategoriaDTO> get(@PathVariable("id") Long id) {
		Categoria obj = service.findById(id);
		if (obj != null) {
			return ResponseEntity.ok(mapper.toDTO(obj));
		}
		return ResponseEntity.notFound().build();
	}

	@Override
	@PostMapping
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<CategoriaDTO> post(@Valid @RequestBody CategoriaDTO obj) throws URISyntaxException {
		Categoria categoria = service.create(mapper.toEntity(obj));
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(categoria.getId())
				.toUri();
		return ResponseEntity.created(location).body(mapper.toDTO(categoria));
	}

	@PostMapping("/v2")
	@ResponseStatus(HttpStatus.CREATED)
	public CategoriaDTO postv2(@Valid @RequestBody CategoriaDTO obj) throws URISyntaxException {
		return mapper.toDTO(service.create(mapper.toEntity(obj)));
	}

	@Override
	@PutMapping
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<CategoriaDTO> put(@Valid @RequestBody CategoriaDTO obj) {
		if (service.update(mapper.toEntity(obj))) {
			return ResponseEntity.ok(obj);
		}
		return ResponseEntity.notFound().build();
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
