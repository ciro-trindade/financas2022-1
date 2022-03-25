package br.fatec.financas.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Table(name = "tb_pessoa_juridica")
@Entity
public class PessoaJuridica extends Cliente {
	private static final long serialVersionUID = 1L;
	
	@Column(name="cd_cnpj", length=14)
	private String cnpj;
	
	@Column(name="nm_ramo_atividade", length=20)
	private String ramoAtividade;
}
