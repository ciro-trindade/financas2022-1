package br.fatec.financas.controller;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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

import br.fatec.financas.model.Categoria;
import br.fatec.financas.service.CategoriaService;

@RestController
@RequestMapping("/categorias")
public class CategoriaController implements ControllerInterface<Categoria>{

	@Autowired
	private CategoriaService service;

	@Override
	@GetMapping
	public ResponseEntity<List<Categoria>> getAll() {
		return ResponseEntity.ok(service.findAll());
	}

	@Override
	@GetMapping("/{id}")
	public ResponseEntity<?> get(@PathVariable("id") Long id) {
		Categoria _Categoria = service.findById(id);
		if (_Categoria != null) {
			return ResponseEntity.ok(_Categoria);
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
	}

	@Override
	@PostMapping
	public ResponseEntity<Categoria> post(@RequestBody Categoria Categoria) throws URISyntaxException {
		service.create(Categoria);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(Categoria.getId())
				.toUri();
		return ResponseEntity.created(location).body(Categoria);
	}

	@Override
	@PutMapping
	public ResponseEntity<?> put(@RequestBody Categoria Categoria) {
		if (service.update(Categoria)) {
			return ResponseEntity.ok(Categoria);
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
	}

	@Override
	@DeleteMapping("/{id}")
	public ResponseEntity<?> delete(@PathVariable("id") Long id) {
		if (service.delete(id)) {
			return ResponseEntity.ok().build();
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
	}

}
