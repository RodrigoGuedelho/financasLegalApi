package br.com.guedelho.models;

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
import javax.persistence.Table;
import br.com.guedelho.enums.StatusGenerico;

@Entity
@Table(name = "grupo_conta")
@SequenceGenerator(name = "seq_grupo_conta", sequenceName = "seq_grupo_conta", allocationSize = 1, initialValue = 1)
public class GrupoConta {	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_grupo_conta")
	private Long id;
	@Column(length = 70)
	private String descricao;
	@OneToOne
	private Usuario usuario;
	
	@Enumerated(EnumType.STRING)
	private StatusGenerico status;
	private OffsetDateTime data; 
	@Column(name = "ultima_alteracao")
	private OffsetDateTime ultimaAlteracao;
	
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
	public StatusGenerico getStatus() {
		return status;
	}
	public void setStatus(StatusGenerico status) {
		this.status = status;
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
	public Usuario getUsuario() {
		return usuario;
	}
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
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
		GrupoConta other = (GrupoConta) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}
