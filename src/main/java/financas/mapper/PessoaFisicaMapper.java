package financas.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import financas.dto.PessoaFisicaDTO;
import financas.model.PessoaFisica;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Component
public class PessoaFisicaMapper {
	
	private ModelMapper modelMapper;
	
	public PessoaFisica toEntity(PessoaFisicaDTO obj) {
		return modelMapper.map(obj, PessoaFisica.class);				
	}
	
	public PessoaFisicaDTO toDTO(PessoaFisica obj) {
		return modelMapper.map(obj, PessoaFisicaDTO.class);				
	}
	
	public List<PessoaFisica> toEntity(List<PessoaFisicaDTO> list) {
		return list.stream()
				.map(this::toEntity)
				.collect(Collectors.toList());
	}
	
	public List<PessoaFisicaDTO> toDTO(List<PessoaFisica> list) {
		return list.stream()
				.map(this::toDTO)
				.collect(Collectors.toList());
	}
}
