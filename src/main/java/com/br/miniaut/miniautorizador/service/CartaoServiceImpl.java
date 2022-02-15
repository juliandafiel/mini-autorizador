package com.br.miniaut.miniautorizador.service;

import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.br.miniaut.miniautorizador.dto.CartaoDto;
import com.br.miniaut.miniautorizador.model.Cartao;
import com.br.miniaut.miniautorizador.repository.CartaoRepository;
import com.br.miniaut.miniautorizador.util.EncriptacaoUtil;
import com.br.miniaut.miniautorizador.util.GenericMessage;


@Service
public class CartaoServiceImpl implements CartaoService{

	@Autowired 
	private CartaoRepository cartaoRepository;
	
	@Override
	public Optional<CartaoDto> criarNovoCartao(Cartao cartao) throws Exception {
		cartao.setSaldo(Cartao.SALDO_INICIAL);
		cartao.setSenha(EncriptacaoUtil.encriptarMd5(cartao.getSenha()));
		return Optional.of(new CartaoDto(cartaoRepository.save(cartao)));
	}

	@Override
	public boolean existeObjeto(Cartao cartao) throws Exception {
		return cartaoRepository.existsById(cartao.getNumeroCartao());
	}
	
	@Override
	public Optional<CartaoDto> buscar(String numeroCartao) throws Exception {
		Optional<Cartao> cartao = cartaoRepository.findById(numeroCartao);
		if(cartao.isPresent()) {
			return Optional.of(new CartaoDto(cartao.get()));
		}
		return null;
	}
	
	@Override
	public GenericMessage realizarSaque(Cartao cartao) throws Exception {
		
		if(!existeObjeto(cartao)) {
			throw new Exception();
		}
		
		return null;
	}

	@Override
	public Optional<Cartao> buscarDadosCompletos(String numeroCartao) throws Exception {
		return cartaoRepository.findById(numeroCartao);
	}
	
	@Override
	public Optional<CartaoDto> acessarCartao(String numeroCartao, String senha) {
		Optional<Cartao> cartao = cartaoRepository.acessar(numeroCartao, EncriptacaoUtil.encriptarMd5(senha));
		
		if(Objects.isNull(cartao) || !cartao.isPresent()) {
			return null;
		}
		
		return Optional.of(new CartaoDto(cartao.get()));
	}
}
