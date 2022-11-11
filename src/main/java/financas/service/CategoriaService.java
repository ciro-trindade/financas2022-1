package financas.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import financas.model.Categoria;
import financas.repository.CategoriaRepository;

@Service
public class CategoriaService implements ServiceInterface<Categoria> {
	
	@Autowired
	private CategoriaRepository repository;
	
	public CategoriaService() {
	}

	public Categoria create(Categoria obj) {
		return repository.save(obj);
	}
	
	public List<Categoria> findAll() {
		return repository.findAll();
	}
	
	public Categoria findById(Long id) {
		Optional<Categoria> obj = repository.findById(id);
		if (obj.isPresent()) {
			return obj.get();
		}
		return null;
	}
	
	public boolean update(Categoria categoria) {
		if (repository.existsById(categoria.getId())) {
			repository.save(categoria);
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
