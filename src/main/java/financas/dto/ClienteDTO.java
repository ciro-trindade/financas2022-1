package financas.dto;

import java.util.Set;

import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import financas.model.Conta;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ClienteDTO {
	private Long id;
	
	@NotBlank
	@Length(min = 3, max = 60)
	private String nome;
	
	@Length(max = 120)
	private String endereco;
	
	private Conta conta;
	
	private Set<Integer> perfis;
	
	private String login;
	
	@Getter(onMethod = @__(@JsonIgnore))
	@Setter(onMethod = @__(@JsonProperty))
	private String senha;
	
	public void setPerfis(Set<Integer> perfis) {
		this.perfis = perfis;
	}
	
}
