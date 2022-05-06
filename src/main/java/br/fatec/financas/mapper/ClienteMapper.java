package br.fatec.financas.mapper;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import br.fatec.financas.dto.ClienteDTO;
import br.fatec.financas.model.PessoaFisica;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Component
public class ClienteMapper {
	
	public PessoaFisica toEntity(ClienteDTO obj) {
		PessoaFisica pessoaFisica = new PessoaFisica();
		pessoaFisica.setId(obj.getId());
		pessoaFisica.setNome(obj.getNome());
		pessoaFisica.setEndereco(obj.getEndereco());
		pessoaFisica.setConta(obj.getConta());
		pessoaFisica.setPerfis(obj.getPerfis());
		pessoaFisica.setLogin(obj.getLogin());
		pessoaFisica.setSenha(obj.getSenha());
		return pessoaFisica;				
	}
	
	public ClienteDTO toDTO(PessoaFisica obj) {
		ClienteDTO ClienteDTO = new ClienteDTO();
		ClienteDTO.setId(obj.getId());
		ClienteDTO.setNome(obj.getNome());
		ClienteDTO.setEndereco(obj.getEndereco());
		ClienteDTO.setConta(obj.getConta());
		ClienteDTO.setPerfis(obj.getPerfisAsInteger());
		ClienteDTO.setLogin(obj.getLogin());
		ClienteDTO.setSenha(obj.getSenha());
		return ClienteDTO;				
	}
	
	public List<PessoaFisica> toEntity(List<ClienteDTO> list) {
		List<PessoaFisica> lista = new ArrayList<>();
		for (ClienteDTO p : list) {
			lista.add(toEntity(p));
		}
		return lista;
	}
	
	public List<ClienteDTO> toDTO(List<PessoaFisica> list) {
		List<ClienteDTO> lista = new ArrayList<>();
		for (PessoaFisica p : list) {
			lista.add(toDTO(p));
		}
		return lista;
	}
}
