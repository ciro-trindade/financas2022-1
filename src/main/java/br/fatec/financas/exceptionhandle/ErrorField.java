package br.fatec.financas.exceptionhandle;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ErrorField {
	private String nome;
	private String mensagem;
}
