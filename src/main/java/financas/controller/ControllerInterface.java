package financas.controller;

import java.net.URISyntaxException;
import java.util.List;

import org.springframework.http.ResponseEntity;

public interface ControllerInterface<T> {
	ResponseEntity<List<T>> getAll();
	ResponseEntity<T> get(Long id);
	ResponseEntity<T> post(T obj) throws URISyntaxException;
	ResponseEntity<T> put(T obj);
	ResponseEntity<Void> delete(Long id);
}
