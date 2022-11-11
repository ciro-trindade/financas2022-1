package financas.mapper;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import financas.dto.PessoaJuridicaDTO;
import financas.model.PessoaJuridica;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Component
public class PessoaJuridicaMapper {
	
	private ModelMapper modelMapper;
	
	public PessoaJuridica toEntity(PessoaJuridicaDTO obj) {
		return modelMapper.map(obj, PessoaJuridica.class);				
	}
	
	public PessoaJuridicaDTO toDTO(PessoaJuridica obj) {
		return modelMapper.map(obj, PessoaJuridicaDTO.class);				
	}
	
	public List<PessoaJuridica> toEntity(List<PessoaJuridicaDTO> list) {
		return list.stream()
				.map(this::toEntity)
				.collect(Collectors.toList());
	}
	
	public List<PessoaJuridicaDTO> toDTO(List<PessoaJuridica> list) {
		return list.stream()
				.map(this::toDTO)
				.collect(Collectors.toList());
	}
}
