package com.br.miniaut.miniautorizador.service;

import java.util.Optional;

import com.br.miniaut.miniautorizador.dto.CartaoDto;
import com.br.miniaut.miniautorizador.model.Cartao;
import com.br.miniaut.miniautorizador.util.GenericMessage;

public interface CartaoService {

	Optional<CartaoDto> criarNovoCartao(Cartao cartao) throws Exception;
	
	Optional<CartaoDto> buscar(String numeroCartao) throws Exception;
	
	Optional<Cartao> buscarDadosCompletos(String numeroCartao) throws Exception;
	
	boolean existeObjeto(Cartao cartao) throws Exception;

	GenericMessage realizarSaque(Cartao cartao) throws Exception;

	Optional<CartaoDto> acessarCartao(String numeroCartao, String senha);

}
