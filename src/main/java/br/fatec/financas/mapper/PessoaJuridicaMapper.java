package br.fatec.financas.mapper;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import br.fatec.financas.dto.PessoaJuridicaDTO;
import br.fatec.financas.model.PessoaJuridica;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Component
public class PessoaJuridicaMapper {
	
	public PessoaJuridica toEntity(PessoaJuridicaDTO obj) {
		PessoaJuridica pessoaJuridica = new PessoaJuridica();
		pessoaJuridica.setId(obj.getId());
		pessoaJuridica.setNome(obj.getNome());
		pessoaJuridica.setEndereco(obj.getEndereco());
		pessoaJuridica.setCnpj(obj.getCnpj());
		pessoaJuridica.setRamoAtividade(obj.getRamoAtividade());
		pessoaJuridica.setConta(obj.getConta());
		pessoaJuridica.setPerfis(obj.getPerfis());
		pessoaJuridica.setLogin(obj.getLogin());
		pessoaJuridica.setSenha(obj.getSenha());
		return pessoaJuridica;				
	}
	
	public PessoaJuridicaDTO toDTO(PessoaJuridica obj) {
		PessoaJuridicaDTO pessoaJuridicaDTO = new PessoaJuridicaDTO();
		pessoaJuridicaDTO.setId(obj.getId());
		pessoaJuridicaDTO.setNome(obj.getNome());
		pessoaJuridicaDTO.setEndereco(obj.getEndereco());
		pessoaJuridicaDTO.setCnpj(obj.getCnpj());
		pessoaJuridicaDTO.setRamoAtividade(obj.getRamoAtividade());
		pessoaJuridicaDTO.setConta(obj.getConta());
		pessoaJuridicaDTO.setPerfis(obj.getPerfisAsInteger());
		pessoaJuridicaDTO.setLogin(obj.getLogin());
		pessoaJuridicaDTO.setSenha(obj.getSenha());

		return pessoaJuridicaDTO;				
	}
	
	public List<PessoaJuridica> toEntity(List<PessoaJuridicaDTO> list) {
		List<PessoaJuridica> lista = new ArrayList<>();
		for (PessoaJuridicaDTO p : list) {
			lista.add(toEntity(p));
		}
		return lista;
	}
	
	public List<PessoaJuridicaDTO> toDTO(List<PessoaJuridica> list) {
		List<PessoaJuridicaDTO> lista = new ArrayList<>();
		for (PessoaJuridica p : list) {
			lista.add(toDTO(p));
		}
		return lista;
	}
}
