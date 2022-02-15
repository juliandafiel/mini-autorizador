package com.br.miniaut.miniautorizador.model;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.transaction.Transactional;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.br.miniaut.miniautorizador.dto.CartaoDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "cartao")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Cartao {
	
	public static final BigDecimal SALDO_INICIAL = BigDecimal.valueOf(500);
	
	@Id
	@NotNull(message="{required.validation}")
	private String numeroCartao;
	 
	private String senha;
	
	private BigDecimal saldo;	
	
	
	@Transient
	private BigDecimal valor;
	
	
	public Cartao(CartaoDto cartao) {
		setNumeroCartao(cartao.getNumeroCartao());
		setSenha(cartao.getSenha());
		setSaldo(cartao.getSaldo());
	}
	
}
