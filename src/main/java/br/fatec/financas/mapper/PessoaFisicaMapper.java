package br.fatec.financas.mapper;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import br.fatec.financas.dto.PessoaFisicaDTO;
import br.fatec.financas.model.PessoaFisica;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Component
public class PessoaFisicaMapper {
	
	public PessoaFisica toEntity(PessoaFisicaDTO obj) {
		PessoaFisica pessoaFisica = new PessoaFisica();
		pessoaFisica.setId(obj.getId());
		pessoaFisica.setNome(obj.getNome());
		pessoaFisica.setEndereco(obj.getEndereco());
		pessoaFisica.setCpf(obj.getCpf());
		pessoaFisica.setProfissao(obj.getProfissao());
		pessoaFisica.setConta(obj.getConta());
		return pessoaFisica;				
	}
	
	public PessoaFisicaDTO toDTO(PessoaFisica obj) {
		PessoaFisicaDTO pessoaFisicaDTO = new PessoaFisicaDTO();
		pessoaFisicaDTO.setId(obj.getId());
		pessoaFisicaDTO.setNome(obj.getNome());
		pessoaFisicaDTO.setEndereco(obj.getEndereco());
		pessoaFisicaDTO.setCpf(obj.getCpf());
		pessoaFisicaDTO.setProfissao(obj.getProfissao());
		pessoaFisicaDTO.setConta(obj.getConta());
		return pessoaFisicaDTO;				
	}
	
	public List<PessoaFisica> toEntity(List<PessoaFisicaDTO> list) {
		List<PessoaFisica> lista = new ArrayList<>();
		for (PessoaFisicaDTO p : list) {
			lista.add(toEntity(p));
		}
		return lista;
	}
	
	public List<PessoaFisicaDTO> toDTO(List<PessoaFisica> list) {
		List<PessoaFisicaDTO> lista = new ArrayList<>();
		for (PessoaFisica p : list) {
			lista.add(toDTO(p));
		}
		return lista;
	}
}
