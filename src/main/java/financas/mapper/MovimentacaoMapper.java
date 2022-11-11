package financas.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import financas.dto.MovimentacaoDTO;
import financas.model.Movimentacao;
import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class MovimentacaoMapper {

	private ModelMapper modelMapper;
	
	public Movimentacao toEntity(MovimentacaoDTO obj) {
		return modelMapper.map(obj, Movimentacao.class);
	}
	
	public MovimentacaoDTO toDTO(Movimentacao obj) {
		return modelMapper.map(obj, MovimentacaoDTO.class);
	}
	
	public List<Movimentacao> toEntity(List<MovimentacaoDTO> list) {
		return list.stream()
				.map(this::toEntity)
				.collect(Collectors.toList());
	}

	public List<MovimentacaoDTO> toDTO(List<Movimentacao> list) {
		return list.stream()
				.map(this::toDTO)
				.collect(Collectors.toList());
	}

}
