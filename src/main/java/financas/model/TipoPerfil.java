package financas.model;

import lombok.Getter;

@Getter
public enum TipoPerfil {
	ADMIN(1, "ROLE_ADMIN"), CLIENTE(2, "ROLE_CLIENTE");

	private Integer cod;
	private String descricao;

	private TipoPerfil(Integer cod, String descricao) {
		this.cod = cod;
		this.descricao = descricao;
	}

	public static TipoPerfil toEnum(Integer cod) {
		if (cod == null)
			return null;
		for (TipoPerfil x : TipoPerfil.values()) {
			if (cod.equals(x.getCod()))
				return x;
		}
		throw new IllegalArgumentException("Código inválido: " + cod);
	}
	
}
