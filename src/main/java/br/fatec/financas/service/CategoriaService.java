package br.fatec.financas.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.fatec.financas.model.Categoria;
import br.fatec.financas.repository.CategoriaRepository;

@Service
public class CategoriaService implements ServiceInterface<Categoria>{
	
	@Autowired
	private CategoriaRepository repository;

	public CategoriaService() {
	}

	@Override
	public Categoria create(Categoria Categoria) {
		repository.save(Categoria);
		return Categoria;
	}
	
	@Override
	public List<Categoria> findAll() {
		return repository.findAll();
	}
	
	@Override
	public Categoria findById(Long id) {
		Optional<Categoria> obj = repository.findById(id);
		return obj.orElse(null);
	}
	
	@Override
	public boolean update(Categoria Categoria) {
		if (repository.existsById(Categoria.getId())) {
			repository.save(Categoria);
			return true;
		}
		return false;
	}
	
	@Override
	public boolean delete(Long id) {
		if (repository.existsById(id)) {
			repository.deleteById(id);
			return true;
		}
		return false;
	}
	
}
