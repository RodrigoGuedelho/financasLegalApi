package br.com.guedelho.services;

import java.time.OffsetDateTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import br.com.guedelho.enums.StatusGenerico;
import br.com.guedelho.models.GrupoConta;
import br.com.guedelho.repository.GrupoContaRepository;

@Service
public class GrupoContaService {
	
	@Autowired
	private GrupoContaRepository grupoContaRepository;
	
	public GrupoConta salvar(GrupoConta grupoConta) throws Exception {
		System.out.println(">>>>" + grupoConta.getDescricao());
		Exception exception = validarSalvar(grupoConta);
		if (exception != null)
			throw exception;
		grupoConta.setData(OffsetDateTime.now());
		grupoConta.setUltimaAlteracao(OffsetDateTime.now());
		grupoConta.setStatus(StatusGenerico.ATIVO);
		return grupoContaRepository.save(grupoConta);
	}
	
	public GrupoConta editar(GrupoConta grupoConta) throws Exception {
		Exception exception = validarEditar(grupoConta);
		if (exception != null)
			throw exception;
		GrupoConta grupoContaAuxiar = grupoContaRepository.findById(grupoConta.getId()).get();
		grupoContaAuxiar.setDescricao(grupoConta.getDescricao());
		grupoContaAuxiar.setUltimaAlteracao(OffsetDateTime.now());
		return grupoContaRepository.save(grupoContaAuxiar);
	}
	
	public Exception validarSalvar(GrupoConta grupoConta) {
		List<GrupoConta> grupoContaAuxiliar = grupoContaRepository.findByDescricao(grupoConta.getDescricao());
		
		if (grupoContaAuxiliar.size() > 0)
			return new Exception("Descrição já existe.");
		
		return null;
	}
	
	public Exception validarEditar(GrupoConta grupoConta) {
		List<GrupoConta> grupoContaAuxiliar = grupoContaRepository.findByDescricao(grupoConta.getDescricao());
		
		if (grupoContaAuxiliar.size() > 0 && grupoContaAuxiliar.get(0).getId() != grupoConta.getId())
			return new Exception("Descrição já existe.");
		
		
		return null;
	}
	
	public List<GrupoConta> find(String descricao, Long id, StatusGenerico status) {
		return grupoContaRepository.find("%" + descricao + "%", id, status);
	}
	
	public GrupoConta cancelar(Long id) throws Exception {
		GrupoConta grupoConta = grupoContaRepository.findById(id).get();
		if (grupoConta == null)
			throw new Exception("Grupo de conta não existe");
		else if (grupoConta.getStatus().equals(StatusGenerico.CANCELADO))
			throw new Exception("Grupo de conta já está cancelado");
		
		grupoConta.setStatus(StatusGenerico.CANCELADO);
		grupoConta.setUltimaAlteracao(OffsetDateTime.now());
		return grupoContaRepository.save(grupoConta);
	}
}
