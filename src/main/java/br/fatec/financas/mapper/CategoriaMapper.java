package br.fatec.financas.mapper;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import br.fatec.financas.dto.CategoriaDTO;
import br.fatec.financas.model.Categoria;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Component
public class CategoriaMapper {

	public Categoria toEntity(CategoriaDTO categoriaDTO) {
		Categoria categoria = new Categoria();
		categoria.setId(categoriaDTO.getId());
		categoria.setNome(categoriaDTO.getNome());
		return categoria;
	}

	public CategoriaDTO toDTO(Categoria categoria) {
		CategoriaDTO categoriaDTO = new CategoriaDTO();
		categoriaDTO.setId(categoria.getId());
		categoriaDTO.setNome(categoria.getNome());
		return categoriaDTO;
	}

	public List<CategoriaDTO> toDTO(List<Categoria> categorias) {
		List<CategoriaDTO> categoriasDTO = new ArrayList<>();
		for (Categoria c : categorias) {
			categoriasDTO.add(toDTO(c));
		}
		return categoriasDTO;
	}

	public List<Categoria> toEntity(List<CategoriaDTO> categoriasDTO) {
		List<Categoria> categorias = new ArrayList<>();
		for (CategoriaDTO c : categoriasDTO) {
			categorias.add(toEntity(c));
		}
		return categorias;
	}
}
