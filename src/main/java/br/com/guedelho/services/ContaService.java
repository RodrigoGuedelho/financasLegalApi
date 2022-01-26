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
import br.com.guedelho.repository.ContaRepository;
import br.com.guedelho.repository.GrupoContaRepository;

@Service
public class ContaService {
	
	@Autowired
	private ContaRepository contaRepository;
	@Autowired
	private GrupoContaRepository grupoContaRepository;
	
	public Conta salvar(Conta conta) throws Exception {
		Exception exception = validarSalvar(conta);
		if (exception != null)
			throw exception;
		conta.setData(OffsetDateTime.now());
		conta.setUltimaAlteracao(OffsetDateTime.now());
		conta.setStatus(StatusGenerico.ATIVO);
		return contaRepository.save(conta);
	}
	
	public Conta editar(Conta conta) throws Exception {
		Exception exception = validarEditar(conta);
		if (exception != null)
			throw exception;
		
		Conta contaAuxiliar = contaRepository.findById(conta.getId()).get();
		
		conta.setData(contaAuxiliar.getData());
		conta.setStatus(StatusGenerico.ATIVO);
		conta.setUltimaAlteracao(OffsetDateTime.now());
		return contaRepository.save(conta);
	}
	
	public List<Conta> find(String descricao, Long id, StatusGenerico status, TipoConta tipoConta){
		return contaRepository.find("%" + descricao + "%", id, status, tipoConta);
	}
	
	public Conta cancelar(Long id) throws Exception {
		Conta conta = contaRepository.findById(id).get();
		if (conta == null)
			throw new Exception("conta invalida.");
		else if (conta.getStatus().equals(StatusGenerico.CANCELADO))
			throw new Exception("conta já está cancelada.");
		conta.setStatus(StatusGenerico.CANCELADO);
		conta.setUltimaAlteracao(OffsetDateTime.now());
		return contaRepository.save(conta);
	}
	
	private Exception validarSalvar(Conta conta) {
		if (conta.getValor().compareTo(BigDecimal.ZERO) == 0 || conta.getValor().compareTo(BigDecimal.ZERO) == -1)
			return new Exception("Valor inválido");
		
		GrupoConta grupoConta = grupoContaRepository.findById(conta.getGrupoConta().getId()).get();
		if (grupoConta == null)
			return new Exception("Grupo de conta inválido");
		return null;
	}
	
	private Exception validarEditar(Conta conta) {
		Exception exception = validarSalvar(conta);
		if (exception != null)
			return exception;
		
		Conta contaAuxiliar = contaRepository.findById(conta.getId()).get();
		if (contaAuxiliar.getStatus().equals(StatusGenerico.CANCELADO))
			return new Exception("Essa conta já está cancelada");
		return null;
	}
	
}
