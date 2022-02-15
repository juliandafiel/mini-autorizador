package com.br.miniaut.miniautorizador.service;

import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import com.br.miniaut.miniautorizador.dto.CartaoDto;
import com.br.miniaut.miniautorizador.model.Cartao;
import com.br.miniaut.miniautorizador.repository.CartaoRepository;
import com.br.miniaut.miniautorizador.util.EncriptacaoUtil;
import com.br.miniaut.miniautorizador.util.GenericMessage;
import com.br.miniaut.miniautorizador.util.Messages;


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
	@Transactional(isolation = Isolation.SERIALIZABLE,readOnly = false)
	public GenericMessage realizarSaque(Cartao cartao) throws Exception {
		
		CartaoDto cartaoDto = acessarCartao(cartao.getNumeroCartao(),cartao.getSenha()).get();
		if(Objects.isNull(cartaoDto)) {
			return new GenericMessage(Messages.CARTAO_INEXISTENTE);
		}
		
		cartao.setSaldo(cartaoDto.getSaldo().subtract(cartao.getValor()));
		cartao.setSenha(EncriptacaoUtil.encriptarMd5(cartao.getSenha()));
		cartaoRepository.save(cartao);
		
		return new GenericMessage(Messages.SUCESSO);
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
