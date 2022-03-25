package br.fatec.financas.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@Table(name = "tb_cliente")
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Cliente extends AbstractEntity {
	private static final long serialVersionUID = 1L;
	
	@Column(name="nm_nome", length=60)
	private String nome;
	
	@Column(name="ds_endereco", length=120)
	private String endereco;
	
	@OneToOne(cascade = { CascadeType.PERSIST, CascadeType.REMOVE })
	@JoinColumn(name = "fk_conta_id", unique = true)
	private Conta conta;
}
