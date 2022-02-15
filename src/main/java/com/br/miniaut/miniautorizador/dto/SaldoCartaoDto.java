package com.br.miniaut.miniautorizador.dto;

import java.math.BigDecimal;

import com.br.miniaut.miniautorizador.model.Cartao;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class SaldoCartaoDto {

	private BigDecimal saldo;
	
	
	public SaldoCartaoDto(Cartao cartao) {
		setSaldo(cartao.getSaldo());
	}
}
