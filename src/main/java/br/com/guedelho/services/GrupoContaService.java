package br.com.guedelho.services;

import java.time.OffsetDateTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import br.com.guedelho.enums.StatusGenerico;
import br.com.guedelho.models.GrupoConta;
import br.com.guedelho.models.Usuario;
import br.com.guedelho.repository.GrupoContaRepository;
import br.com.guedelho.utils.Utils;

@Service
public class GrupoContaService {
	
	@Autowired
	private GrupoContaRepository grupoContaRepository;
	
	public GrupoConta salvar(GrupoConta grupoConta, String token) throws Exception {
		Usuario usuario = Utils.getUsuarioLogado(token);
		Exception exception = validarSalvar(grupoConta, usuario);
		if (exception != null)
			throw exception;
		grupoConta.setData(OffsetDateTime.now());
		grupoConta.setUltimaAlteracao(OffsetDateTime.now());
		grupoConta.setStatus(StatusGenerico.ATIVO);
		grupoConta.setUsuario(usuario);
		return grupoContaRepository.save(grupoConta);
	}
	
	public GrupoConta editar(GrupoConta grupoConta, String token) throws Exception {
		Exception exception = validarEditar(grupoConta, Utils.getUsuarioLogado(token));
		if (exception != null)
			throw exception;
		GrupoConta grupoContaAuxiar = grupoContaRepository.findById(grupoConta.getId()).get();
		grupoContaAuxiar.setDescricao(grupoConta.getDescricao());
		grupoContaAuxiar.setUltimaAlteracao(OffsetDateTime.now());
		grupoContaAuxiar.setUsuario(Utils.getUsuarioLogado(token));
		return grupoContaRepository.save(grupoContaAuxiar);
	}
	
	public Exception validarSalvar(GrupoConta grupoConta, Usuario usuario) {
		List<GrupoConta> grupoContaAuxiliar = grupoContaRepository.findByDescricaoAtivos(grupoConta.getDescricao(), 
			usuario.getId());
		
		if (grupoContaAuxiliar.size() > 0)
			return new Exception("Descrição já existe.");
		
		return null;
	}
	
	public Exception validarEditar(GrupoConta grupoConta, Usuario usuario) {
		List<GrupoConta> grupoContaAuxiliar = grupoContaRepository
			.findByDescricaoAtivos(grupoConta.getDescricao(), usuario.getId());
		GrupoConta grupoContaAuxiliar2 = grupoContaRepository
			.findById(grupoConta.getId()).get();
		
		if (grupoContaAuxiliar.size() > 0 
			&& grupoContaAuxiliar.get(0).getId() != grupoConta.getId()
			&& grupoContaAuxiliar2.getUsuario().getId()
				.equals(usuario.getId()))
			return new Exception("Descrição já existe.");
		if (grupoContaAuxiliar2.getStatus().equals(StatusGenerico.CANCELADO))
			return new Exception("Grupo de Contas está cancelado.");
		if (!grupoContaAuxiliar2.getUsuario().getId()
			.equals(usuario.getId()))
			return new Exception("Usuário não tem permissão para editar esse grupo de contas.");
		
		return null;
	}
	
	public List<GrupoConta> find(String descricao, Long id, StatusGenerico status, 
		String token) {
		return grupoContaRepository.find("%" + descricao + "%", id, status, 
			Utils.getUsuarioLogado(token).getId());
	}
	
	public GrupoConta cancelar(Long id, String token) throws Exception {
		GrupoConta grupoConta = grupoContaRepository.findById(id).get();
		Usuario usuario = Utils.getUsuarioLogado(token);
		Exception exception = validarCancelar(grupoConta, usuario);
		if (exception != null)
			throw exception;

		grupoConta.setStatus(StatusGenerico.CANCELADO);
		grupoConta.setUltimaAlteracao(OffsetDateTime.now());
		grupoConta.setUsuario(usuario);
		return grupoContaRepository.save(grupoConta);
	}

	public Exception validarCancelar(GrupoConta grupoConta, Usuario usuario){
		if (grupoConta == null)
			return new Exception("Grupo de conta não existe");
		else if (grupoConta.getStatus().equals(StatusGenerico.CANCELADO))
			return new Exception("Grupo de conta já está cancelado");
		else if (!grupoConta.getUsuario().getId().equals(usuario.getId()))
			return new Exception("Usuário não tem permissão para cancelar esse grupo de contas.");
		return null;
	}
}
