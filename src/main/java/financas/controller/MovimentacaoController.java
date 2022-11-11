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

import financas.dto.MovimentacaoDTO;
import financas.exception.AuthorizationException;
import financas.mapper.MovimentacaoMapper;
import financas.model.Movimentacao;
import financas.service.MovimentacaoService;

@RestController
@RequestMapping("/movimentacoes")
public class MovimentacaoController implements ControllerInterface<MovimentacaoDTO> {

	@Autowired
	private MovimentacaoService service;

	@Autowired
	private MovimentacaoMapper mapper;
	
	@Override
	@GetMapping
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<List<MovimentacaoDTO>> getAll() {
		return ResponseEntity.ok(mapper.toDTO(service.findAll()));
	}

	@Override
	@GetMapping("/{id}")
	public ResponseEntity<MovimentacaoDTO> get(@PathVariable("id") Long id) {
		Movimentacao obj = service.findById(id);
		if (obj != null) {
			return ResponseEntity.ok(mapper.toDTO(obj));
		}
		return ResponseEntity.notFound().build();
	}

	@Override
	@PostMapping
	public ResponseEntity<MovimentacaoDTO> post(@Valid @RequestBody MovimentacaoDTO obj) throws URISyntaxException {
		try {
			Movimentacao mov = service.create(mapper.toEntity(obj));
			URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(mov.getId())
					.toUri();
			return ResponseEntity.created(location).body(mapper.toDTO(mov));
		} catch (AuthorizationException e) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}
	}

	@Override
	@PutMapping
	public ResponseEntity<MovimentacaoDTO> put(@Valid @RequestBody MovimentacaoDTO obj) {
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

	@GetMapping("/cliente/{id}")
	public ResponseEntity<List<MovimentacaoDTO>> getMovimentacoesCliente(@PathVariable("id") Long clienteId) {
		try {
			return ResponseEntity.ok(mapper.toDTO(service.movimentacoesDoCliente(clienteId)));
		} catch (AuthorizationException e) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}
	}

	@GetMapping("/conta/{id}")
	public ResponseEntity<List<MovimentacaoDTO>> getMovimentacoesConta(@PathVariable("id") Long contaId) {
		try {
			return ResponseEntity.ok(mapper.toDTO(service.movimentacoesDaConta(contaId)));
		} catch (AuthorizationException e) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}
	}
}
