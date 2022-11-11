package financas.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import financas.dto.ClienteDTO;
import financas.model.PessoaFisica;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Component
public class ClienteMapper {

	private ModelMapper modelMapper;

	public PessoaFisica toEntity(ClienteDTO obj) {
		return modelMapper.map(obj, PessoaFisica.class);
	}

	public ClienteDTO toDTO(PessoaFisica obj) {
		return modelMapper.map(obj, ClienteDTO.class);
	}

	public List<PessoaFisica> toEntity(List<ClienteDTO> list) {
		return list.stream()
				.map(this::toEntity)
				.collect(Collectors.toList());
	}

	public List<ClienteDTO> toDTO(List<PessoaFisica> list) {
		return list.stream()
				.map(this::toDTO)
				.collect(Collectors.toList());
	}
}
