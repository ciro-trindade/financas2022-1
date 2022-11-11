package financas.dto;

import java.util.Calendar;
import java.util.List;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import financas.model.Categoria;
import financas.model.Conta;
import financas.model.TipoMovimentacao;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class MovimentacaoDTO {
	private Long id;
	
	@Min(1)
	@Max(100000)
	private Float valor;
	
	private TipoMovimentacao tipo;
	
	@Length(max = 120)
	private String descricao;
	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
	private Calendar data;

	@NotNull
	@Getter(onMethod = @__(@JsonIgnore))
	@Setter(onMethod = @__(@JsonProperty))
	private Conta conta;
	
	private List<Categoria> categorias;
}
