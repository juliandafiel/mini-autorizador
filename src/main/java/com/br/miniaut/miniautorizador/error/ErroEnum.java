package com.br.miniaut.miniautorizador.error;

import lombok.Getter;

@Getter
public enum ErroEnum {

	SENHA_INVALIDA("1"),SALDO_INSUFICIENTE("2"),CARTAO_INEXISTENTE("3");
	
	private String descricao;
	
	private ErroEnum(String descricao) {
		this.descricao = descricao;
	}
	
}
