package com.br.miniaut.miniautorizador.util;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GenericResponse<T>{
	private GenericMessage mensagem;
	private T objeto;
	
}
