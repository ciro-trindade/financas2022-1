package br.fatec.financas.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Entity
@ToString
@NoArgsConstructor
@Table(name = "tb_conta")
public class Conta extends AbstractEntity {
	private static final long serialVersionUID = 1L;

	@Column(name = "nr_agencia")
	private Integer agencia;

	@Column(name = "nm_numero", length = 10)
	private String numero;

	@ToString.Exclude
	@Column(name = "vl_saldo")
	private Float saldo;

	@Getter(onMethod = @__(@JsonIgnore))
	@Setter(onMethod = @__(@JsonProperty))
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "conta")
	private List<Movimentacao> movimentacoes;

}
