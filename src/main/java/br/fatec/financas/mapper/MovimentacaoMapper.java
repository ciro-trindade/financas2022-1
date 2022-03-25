package br.fatec.financas.mapper;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import br.fatec.financas.dto.MovimentacaoDTO;
import br.fatec.financas.model.Movimentacao;
import lombok.NoArgsConstructor;

@Component
@NoArgsConstructor
public class MovimentacaoMapper {

	public Movimentacao toEntity(MovimentacaoDTO obj) {
		Movimentacao movimentacao = new Movimentacao();
		movimentacao.setId(obj.getId());
		movimentacao.setValor(obj.getValor());
		movimentacao.setData(obj.getData());
		movimentacao.setDescricao(obj.getDescricao());
		movimentacao.setTipo(obj.getTipo());
		movimentacao.setConta(obj.getConta());
		movimentacao.setCategorias(obj.getCategorias());
		return movimentacao;
	}
	
	public MovimentacaoDTO toDTO(Movimentacao obj) {
		MovimentacaoDTO movimentacaoDTO = new MovimentacaoDTO();
		movimentacaoDTO.setId(obj.getId());
		movimentacaoDTO.setValor(obj.getValor());
		movimentacaoDTO.setData(obj.getData());
		movimentacaoDTO.setDescricao(obj.getDescricao());
		movimentacaoDTO.setTipo(obj.getTipo());
		movimentacaoDTO.setConta(obj.getConta());
		movimentacaoDTO.setCategorias(obj.getCategorias());
		return movimentacaoDTO;
	}
	
	public List<Movimentacao> toEntity(List<MovimentacaoDTO> list) {
		List<Movimentacao> lista = new ArrayList<>();
		for (MovimentacaoDTO m : list) {
			lista.add(toEntity(m));
		}
		return lista;
	}

	public List<MovimentacaoDTO> toDTO(List<Movimentacao> list) {
		List<MovimentacaoDTO> lista = new ArrayList<>();
		for (Movimentacao m : list) {
			lista.add(toDTO(m));
		}
		return lista;
	}

}
