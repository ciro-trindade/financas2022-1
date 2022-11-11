package financas.dto;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.br.CPF;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PessoaFisicaDTO extends ClienteDTO {
		
	@CPF
	private String cpf;
	
	@Length(max = 30)
	private String profissao;
		
}
