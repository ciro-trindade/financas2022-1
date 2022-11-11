package financas.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "tb_pessoa_fisica")
public class PessoaFisica extends Cliente {
	private static final long serialVersionUID = 2075728590859600139L;

	@Column(name="cd_cpf", length = 11, unique = true)
	private String cpf;
	
	@Column(name="nm_profissao", length = 30)
	private String profissao;
}
