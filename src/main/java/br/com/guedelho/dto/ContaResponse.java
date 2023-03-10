package br.com.guedelho.dto;

import br.com.guedelho.enums.StatusGenerico;
import br.com.guedelho.enums.TipoConta;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.OffsetDateTime;

public class ContaResponse {
    private Long id;
    private String descricao;
    private BigDecimal valor;
    private Long grupoContaId;
    private String grupoContaDescricao;
    private TipoConta tipoConta;
    private LocalDate data;
    private OffsetDateTime ultimaAlteracao;
    private StatusGenerico status;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public String getGrupoContaDescricao() {
        return grupoContaDescricao;
    }

    public void setGrupoContaDescricao(String grupoContaDescricao) {
        this.grupoContaDescricao = grupoContaDescricao;
    }

    public TipoConta getTipoConta() {
        return tipoConta;
    }

    public void setTipoConta(TipoConta tipoConta) {
        this.tipoConta = tipoConta;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public OffsetDateTime getUltimaAlteracao() {
        return ultimaAlteracao;
    }

    public void setUltimaAlteracao(OffsetDateTime ultimaAlteracao) {
        this.ultimaAlteracao = ultimaAlteracao;
    }

    public StatusGenerico getStatus() {
        return status;
    }

    public void setStatus(StatusGenerico status) {
        this.status = status;
    }
}
