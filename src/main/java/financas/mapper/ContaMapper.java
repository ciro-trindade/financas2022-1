package financas.mapper;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import financas.dto.ContaDTO;
import financas.model.Conta;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Component
public class ContaMapper {

	private ModelMapper modelMapper;
	
	public Conta toEntity(ContaDTO obj) {
		return modelMapper.map(obj, Conta.class);
	}
	
	public ContaDTO toDTO(Conta obj) {
		return modelMapper.map(obj, ContaDTO.class);
	}

	public List<Conta> toEntity(List<ContaDTO> list) {
		return list.stream()
				.map(this::toEntity)
				.collect(Collectors.toList());
	}

	public List<ContaDTO> toDTO(List<Conta> list) {
		return list.stream()
				.map(this::toDTO)
				.collect(Collectors.toList());
	}
	
	public Page<ContaDTO> toDTO(Page<Conta> list) {
		return list.map(new Function<Conta, ContaDTO>() {			
			@Override
			public ContaDTO apply(Conta conta) {
				return toDTO(conta);
			}
		});
	}

}
