package br.com.guedelho.dto;

import java.math.BigDecimal;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import br.com.guedelho.enums.TipoConta;

public class ContaRequest {
	@NotBlank
	private String descricao;
	@NotNull
	private BigDecimal valor;
	@NotNull
	private Long grupoContaId;
	@NotNull
	private TipoConta tipoConta;
	
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public BigDecimal getValor() {
		return valor;
	}
	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}
	public Long getGrupoContaId() {
		return grupoContaId;
	}
	public void setGrupoContaId(Long grupoContaId) {
		this.grupoContaId = grupoContaId;
	}
	public TipoConta getTipoConta() {
		return tipoConta;
	}
	public void setTipoConta(TipoConta tipoConta) {
		this.tipoConta = tipoConta;
	}
}
