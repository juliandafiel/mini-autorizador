package com.br.miniaut.miniautorizador.controller;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.br.miniaut.miniautorizador.dto.CartaoDto;
import com.br.miniaut.miniautorizador.error.ErroEnum;
import com.br.miniaut.miniautorizador.model.Cartao;
import com.br.miniaut.miniautorizador.service.CartaoService;
import com.br.miniaut.miniautorizador.util.GenericMessage;
import com.br.miniaut.miniautorizador.util.GenericResponse;
import com.br.miniaut.miniautorizador.util.Messages;

@RestController
public class CartaoController {

	@Autowired
	private CartaoService cartaoService;
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@PostMapping(path = "/cartoes", produces = "application/json")
	public ResponseEntity<GenericResponse<CartaoDto>> criar(@RequestBody @Valid Cartao cartao) {
		
		try {
			if(cartaoExiste(cartao)) {
				CartaoDto cartaoDto = cartaoService.buscar(cartao.getNumeroCartao()).get();
				return new ResponseEntity(new GenericResponse(new GenericMessage(Messages.CARTAO_EXISTENTE), cartaoDto),HttpStatus.UNPROCESSABLE_ENTITY);
			}
			Optional<CartaoDto> cartaoDto = cartaoService.criarNovoCartao(cartao);
			return new ResponseEntity(new GenericResponse(new GenericMessage(Messages.SUCESSO), cartaoDto), HttpStatus.CREATED);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity(new GenericResponse(new GenericMessage(Messages.EXCECAO), null), HttpStatus.BAD_GATEWAY);
		}
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@GetMapping(path = "/cartoes/{numeroCartao}", produces = "application/json")
	public ResponseEntity<GenericResponse<BigDecimal>> obterCartao(@PathVariable @Valid String numeroCartao) {
		
		try {
			Optional<CartaoDto> cartaoDto = cartaoService.buscar(numeroCartao);
			if(!Objects.isNull(cartaoDto) && cartaoDto.isPresent()) {
				return new ResponseEntity(new GenericResponse(new GenericMessage(Messages.SUCESSO), new Cartao(cartaoDto.get())),HttpStatus.OK);
			}
 
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity(null, HttpStatus.NOT_FOUND);
	}
	
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@GetMapping(path = "/transacoes", produces = "application/json")
	public ResponseEntity<GenericMessage> obterCartao(@RequestBody @Valid  Cartao cartao) {
		
		try {
			
			Optional<CartaoDto> cartaoDto = cartaoService.acessarCartao(cartao.getNumeroCartao(),cartao.getSenha());

			if(!cartaoExiste(cartao)) {
				return tratarExcecao(ErroEnum.CARTAO_INEXISTENTE);
			}
			if(senhaEstaIncorreta(cartaoDto)) {
				return tratarExcecao(ErroEnum.SENHA_INVALIDA);
			}
			if(saldoEhInsuficiente(cartaoDto,cartao)){
				return tratarExcecao(ErroEnum.SALDO_INSUFICIENTE);
			}
			
			 
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity(new GenericMessage("Processo concluído com sucesso"), HttpStatus.OK);
	}
	
	 
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private ResponseEntity tratarExcecao(ErroEnum erro) {
		
	    switch (erro) {
	        case SENHA_INVALIDA:
	        	return new ResponseEntity(new GenericMessage(Messages.SENHA_INCORRETA), HttpStatus.UNPROCESSABLE_ENTITY);
	        case CARTAO_INEXISTENTE:
	        	return new ResponseEntity(new GenericMessage(Messages.CARTAO_INEXISTENTE), HttpStatus.UNPROCESSABLE_ENTITY);
	        case SALDO_INSUFICIENTE:
	        	new ResponseEntity(new GenericMessage(Messages.SALDO_INSUFICIENTE), HttpStatus.UNPROCESSABLE_ENTITY);
	    }
		return null;
	}
	  
	
	private boolean saldoEhInsuficiente(Optional<CartaoDto> cartaoDto, @Valid Cartao cartao) {
		return cartaoDto.get().getSaldo().compareTo(cartao.getValor()) < 0;
	}

	private boolean senhaEstaIncorreta(Optional<CartaoDto> cartaoDto) {
		return Objects.isNull(cartaoDto) || cartaoDto.isEmpty() || !cartaoDto.isPresent();
	}

	private boolean cartaoExiste(Cartao cartao) throws Exception {
		return cartaoService.existeObjeto(cartao);
	}
	 
	
	
}
