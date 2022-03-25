package br.fatec.financas.dto;

import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.br.CPF;

import br.fatec.financas.model.Conta;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PessoaFisicaDTO {
	
	private Long id;
	
	@NotBlank
	@Length(min = 3, max = 60)
	private String nome;
	
	@Length(max = 120)
	private String endereco;
	
	@CPF
	private String cpf;
	
	@Length(max = 30)
	private String profissao;
	
	private Conta conta;
}
