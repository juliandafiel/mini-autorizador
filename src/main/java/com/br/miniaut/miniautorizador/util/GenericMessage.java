package com.br.miniaut.miniautorizador.util;


import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class GenericMessage   {
  @JsonProperty("mensagem")
  private String mensagem;
  
}

