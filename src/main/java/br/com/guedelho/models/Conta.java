package br.com.guedelho.models;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;

import br.com.guedelho.enums.StatusGenerico;
import br.com.guedelho.enums.TipoConta;

@Entity
@SequenceGenerator(name = "seq_conta", sequenceName = "seq_conta", allocationSize = 1, initialValue = 1)
public class Conta {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_conta")
	private Long id;
	private String descricao;
	private BigDecimal valor;
	private OffsetDateTime data; 
	@Column(name = "ultima_alteracao")
	private OffsetDateTime ultimaAlteracao; 
	@OneToOne
	private GrupoConta grupoConta;
	@Enumerated(EnumType.STRING)
	private StatusGenerico status;
	@Enumerated(EnumType.STRING)
	private TipoConta tipoConta;
	
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
	public OffsetDateTime getData() {
		return data;
	}
	public void setData(OffsetDateTime data) {
		this.data = data;
	}
	public OffsetDateTime getUltimaAlteracao() {
		return ultimaAlteracao;
	}
	public void setUltimaAlteracao(OffsetDateTime ultimaAlteracao) {
		this.ultimaAlteracao = ultimaAlteracao;
	}
	public GrupoConta getGrupoConta() {
		return grupoConta;
	}
	public void setGrupoConta(GrupoConta grupoConta) {
		this.grupoConta = grupoConta;
	}
	public StatusGenerico getStatus() {
		return status;
	}
	public void setStatus(StatusGenerico status) {
		this.status = status;
	}
	public TipoConta getTipoConta() {
		return tipoConta;
	}
	public void setTipoConta(TipoConta tipoConta) {
		this.tipoConta = tipoConta;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Conta other = (Conta) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}
