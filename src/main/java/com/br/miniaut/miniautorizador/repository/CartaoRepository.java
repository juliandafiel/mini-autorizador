package com.br.miniaut.miniautorizador.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.br.miniaut.miniautorizador.model.Cartao;

public interface CartaoRepository extends JpaRepository<Cartao, String> {

	
	@Query("SELECT c FROM Cartao c where c.numeroCartao = :numeroCartao and c.senha = :senha")
	public Optional<Cartao> acessar(String numeroCartao, String senha);
}
