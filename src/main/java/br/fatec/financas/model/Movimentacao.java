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

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter
@Setter
@NoArgsConstructor
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
	private Calendar data;

	@Getter(onMethod = @__(@JsonIgnore))
	@Setter(onMethod = @__(@JsonProperty))
	@ManyToOne(fetch = FetchType.LAZY)
	private Conta conta;
	
	@ManyToMany
	@JoinTable(name = "tb_movimentacao_categoria",
	           joinColumns=@JoinColumn(name="fk_movimentacao_id"),
	           inverseJoinColumns=@JoinColumn(name="fk_categoria_id"))
	private List<Categoria> categorias;
		
}
