package br.com.guedelho.dto;

import javax.validation.constraints.NotBlank;

public class GrupoContaRequest {
	
	@NotBlank(message = "Descricao Não informada")
	private String descricao;

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
}
