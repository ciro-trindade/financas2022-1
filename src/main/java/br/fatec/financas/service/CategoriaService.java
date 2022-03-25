package br.fatec.financas.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.fatec.financas.dto.CategoriaDTO;
import br.fatec.financas.mapper.CategoriaMapper;
import br.fatec.financas.model.Categoria;
import br.fatec.financas.repository.CategoriaRepository;

@Service
public class CategoriaService implements ServiceInterface<CategoriaDTO> {
	
	@Autowired
	private CategoriaRepository repository;
	
	@Autowired
	private CategoriaMapper mapper;

	public CategoriaService() {
	}

	public CategoriaDTO create(CategoriaDTO obj) {
		Categoria categoria = repository.save(mapper.toEntity(obj));
		return mapper.toDTO(categoria);		
	}
	
	public List<CategoriaDTO> findAll() {
		return mapper.toDTO(repository.findAll());
	}
	
	public CategoriaDTO findById(Long id) {
		Optional<Categoria> obj = repository.findById(id);
		if (obj.isPresent()) {
			return mapper.toDTO(obj.get());
		}
		return null;
	}
	
	public boolean update(CategoriaDTO categoriaDTO) {
		if (repository.existsById(categoriaDTO.getId())) {
			repository.save(mapper.toEntity(categoriaDTO));
			return true;
		}
		return false;
	}
	
	public boolean delete(Long id) {
		if (repository.existsById(id)) {
			repository.deleteById(id);
			return true;
		}
		return false;
	}
	
}
