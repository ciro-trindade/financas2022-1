package financas.dto;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.br.CNPJ;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PessoaJuridicaDTO extends ClienteDTO {
	
	@CNPJ
	private String cnpj;
	
	@Length(max = 20)
	private String ramoAtividade;
	
}
