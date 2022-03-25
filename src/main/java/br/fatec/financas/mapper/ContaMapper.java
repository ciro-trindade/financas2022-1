package br.fatec.financas.mapper;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import br.fatec.financas.dto.ContaDTO;
import br.fatec.financas.model.Conta;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Component
public class ContaMapper {

	public Conta toEntity(ContaDTO obj) {
		Conta conta = new Conta();
		conta.setId(obj.getId());
		conta.setAgencia(obj.getAgencia());
		conta.setNumero(obj.getNumero());
		conta.setSaldo(obj.getSaldo());
		conta.setMovimentacoes(obj.getMovimentacoes());
		return conta;
	}
	
	public ContaDTO toDTO(Conta obj) {
		ContaDTO contaDTO = new ContaDTO();
		contaDTO.setId(obj.getId());
		contaDTO.setAgencia(obj.getAgencia());
		contaDTO.setNumero(obj.getNumero());
		contaDTO.setSaldo(obj.getSaldo());
		contaDTO.setMovimentacoes(obj.getMovimentacoes());
		return contaDTO;
	}

	public List<Conta> toEntity(List<ContaDTO> list) {
		List<Conta> lista = new ArrayList<>();
		for (ContaDTO c : list) {
			lista.add(toEntity(c));
		}
		return lista;
	}

	public List<ContaDTO> toDTO(List<Conta> list) {
		List<ContaDTO> lista = new ArrayList<>();
		for (Conta c : list) {
			lista.add(toDTO(c));
		}
		return lista;
	}

}
