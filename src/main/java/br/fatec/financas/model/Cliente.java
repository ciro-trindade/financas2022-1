package br.fatec.financas.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

@Table(name = "tb_cliente")
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
//@DiscriminatorColumn(name = "nm_pertence_a_classe", length = 20)
public abstract class Cliente extends AbstractEntity {
	private static final long serialVersionUID = 1L;
	
	@Column(name="nm_nome", length=60)
	private String nome;
	
	@Column(name="ds_endereco", length=120)
	private String endereco;

	public Cliente() {
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEndereco() {
		return endereco;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}
	
}
