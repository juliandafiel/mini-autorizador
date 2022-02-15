package com.br.miniaut.miniautorizador.dto;

import java.math.BigDecimal;

import com.br.miniaut.miniautorizador.model.Cartao;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CartaoDto {

	private String numeroCartao;
	
	private String senha;
	
	private BigDecimal saldo;
	
	public CartaoDto(Cartao cartao) {
		setNumeroCartao(cartao.getNumeroCartao());
		setSenha(cartao.getSenha());
		setSaldo(cartao.getSaldo());
	}
	
	
}
