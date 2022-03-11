package br.fatec.financas.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name = "tb_conta")
public class Conta extends AbstractEntity {
	private static final long serialVersionUID = 1L;
		
	@Column(name = "nr_agencia")
	private Integer agencia;
	
	@Column(name = "nm_numero", length = 10)
	private String numero;
	
	@Column(name = "vl_saldo")
	private Float saldo;


	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "conta")
	private List<Movimentacao> movimentacoes;

	
	public Conta() {
	}

	public Integer getAgencia() {
		return agencia;
	}

	public void setAgencia(Integer agencia) {
		this.agencia = agencia;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public Float getSaldo() {
		return saldo;
	}

	public void setSaldo(Float saldo) {
		this.saldo = saldo;
	}

	
	@JsonIgnore
	public List<Movimentacao> getMovimentacoes() {
		return movimentacoes;
	}

	@JsonProperty
	public void setMovimentacoes(List<Movimentacao> movimentacoes) {
		this.movimentacoes = movimentacoes;
	}
		
	
}
