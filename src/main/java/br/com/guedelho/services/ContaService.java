package br.com.guedelho.services;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.guedelho.enums.StatusGenerico;
import br.com.guedelho.enums.TipoConta;
import br.com.guedelho.models.Conta;
import br.com.guedelho.models.GrupoConta;
import br.com.guedelho.models.Usuario;
import br.com.guedelho.repository.ContaRepository;
import br.com.guedelho.repository.GrupoContaRepository;
import br.com.guedelho.utils.Utils;

@Service
public class ContaService {
	
	@Autowired
	private ContaRepository contaRepository;
	@Autowired
	private GrupoContaRepository grupoContaRepository;
	
	public Conta salvar(Conta conta, String token) throws Exception {
		Usuario usuario = Utils.getUsuarioLogado(token);
		Exception exception = validarSalvar(conta, usuario);
		if (exception != null)
			throw exception;
		conta.setData(OffsetDateTime.now());
		conta.setUltimaAlteracao(OffsetDateTime.now());
		conta.setStatus(StatusGenerico.ATIVO);
		conta.setUsuario(usuario);
		
		return contaRepository.save(conta);
	}
	
	public Conta editar(Conta conta, String token) throws Exception {
		Usuario usuario = Utils.getUsuarioLogado(token);
		Exception exception = validarEditar(conta, usuario);
		if (exception != null)
			throw exception;
		
		Conta contaAuxiliar = contaRepository.findById(conta.getId()).get();
		
		conta.setData(contaAuxiliar.getData());
		conta.setStatus(StatusGenerico.ATIVO);
		conta.setUltimaAlteracao(OffsetDateTime.now());
		conta.setUsuario(usuario);
		return contaRepository.save(conta);
	}
	
	public List<Conta> find(String descricao, Long id, StatusGenerico status, TipoConta tipoConta, 
		String token){
		return contaRepository.find("%" + descricao + "%", id, status, tipoConta, 
			Utils.getUsuarioLogado(token).getId());
	}
	
	public Conta cancelar(Long id, String token) throws Exception {
		Usuario usuario = Utils.getUsuarioLogado(token);
		Conta conta = contaRepository.findById(id).get();
		if (conta == null)
			throw new Exception("conta invalida.");
		else if (conta.getStatus().equals(StatusGenerico.CANCELADO))
			throw new Exception("conta j?? est?? cancelada.");
		else if (!conta.getUsuario().getId().equals(usuario.getId()))
			throw new Exception("Usu??rio n??o possui permiss??o para alterar uma conta que n??o ?? dele.");
		conta.setStatus(StatusGenerico.CANCELADO);
		conta.setUltimaAlteracao(OffsetDateTime.now());
		conta.setUsuario(usuario);
		return contaRepository.save(conta);
	}
	
	private Exception validarSalvar(Conta conta, Usuario usuario) {
		if (conta.getValor().compareTo(BigDecimal.ZERO) == 0 || conta.getValor().compareTo(BigDecimal.ZERO) == -1)
			return new Exception("Valor inv??lido");
		
		GrupoConta grupoConta = grupoContaRepository.findById(conta.getGrupoConta().getId()).get();
		if (grupoConta == null)
			return new Exception("Grupo de conta inv??lido");
		if (grupoConta.getStatus().equals(StatusGenerico.CANCELADO))
			return new Exception("Grupo de conta est?? cancelado e n??o pode ser usado.");
			if (!grupoConta.getUsuario().getId().equals(usuario.getId()))
			return new Exception("Usu??rio n??o possui permiss??o para usar um grupo de contas que n??o foi criado por ele.");
		return null;
	}
	
	private Exception validarEditar(Conta conta, Usuario usuario) {
		Exception exception = validarSalvar(conta, usuario);
		if (exception != null)
			return exception;
		
		Conta contaAuxiliar = contaRepository.findById(conta.getId()).get();
		if (contaAuxiliar.getStatus().equals(StatusGenerico.CANCELADO))
			return new Exception("Essa conta j?? est?? cancelada");
		if (!contaAuxiliar.getUsuario().getId().equals(usuario.getId()))
			return new Exception("Usu??rio n??o possui permiss??o para alterar uma conta que n??o ?? dele.");
		
		return null;
	}
	
}
