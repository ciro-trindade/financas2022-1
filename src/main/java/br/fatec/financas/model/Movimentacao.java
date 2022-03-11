package br.fatec.financas.model;

import java.util.Calendar;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name = "tb_movimentacao")
public class Movimentacao extends AbstractEntity {
	private static final long serialVersionUID = 1L;

	@Column(name = "vl_valor")
	private Float valor;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "nm_tipo_movimentacao")
	private TipoMovimentacao tipo;
	
	@Column(name = "ds_descricao", length = 100)
	private String descricao;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "dt_data")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
	private Calendar data;
	
	@ManyToOne(fetch = FetchType.LAZY)
	private Conta conta;
	
	@ManyToMany
	@JoinTable(name = "tb_movimentacao_categoria",
	           joinColumns=@JoinColumn(name="fk_movimentacao_id"),
	           inverseJoinColumns=@JoinColumn(name="fk_categoria_id"))
	private List<Categoria> categorias;

	public Movimentacao() {
	}

	public Float getValor() {
		return valor;
	}

	public void setValor(Float valor) {
		this.valor = valor;
	}

	public TipoMovimentacao getTipo() {
		return tipo;
	}

	public void setTipo(TipoMovimentacao tipo) {
		this.tipo = tipo;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Calendar getData() {
		return data;
	}

	public void setData(Calendar data) {
		this.data = data;
	}

	@JsonIgnore
	public Conta getConta() {
		return conta;
	}

	@JsonProperty
	public void setConta(Conta conta) {
		this.conta = conta;
	}

	public List<Categoria> getCategorias() {
		return categorias;
	}

	public void setCategorias(List<Categoria> categorias) {
		this.categorias = categorias;
	}
		
}
