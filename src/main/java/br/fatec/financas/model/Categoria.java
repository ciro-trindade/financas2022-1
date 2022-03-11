package br.fatec.financas.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "tb_categoria")
public class Categoria extends AbstractEntity {
	private static final long serialVersionUID = 1L;
	
	@Column(name = "nm_categoria", length = 50)
	private String nome;

	public Categoria() {
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}	

}
