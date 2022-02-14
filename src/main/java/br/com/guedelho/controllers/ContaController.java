package br.com.guedelho.controllers;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.guedelho.dto.ContaRequest;
import br.com.guedelho.enums.StatusGenerico;
import br.com.guedelho.enums.TipoConta;
import br.com.guedelho.models.Conta;
import br.com.guedelho.services.ContaService;
import br.com.guedelho.utils.Problema;

@RestController
@RequestMapping(value = "/api")
@CrossOrigin(origins = "*")
public class ContaController {
	@Autowired
	private ContaService contaService;
	@Autowired
	private ModelMapper modelMapper;

	@PostMapping("/contas")
	public ResponseEntity<Object> salvar(@Valid @RequestBody ContaRequest conta, 
		@RequestHeader("Authorization") String token) {
		try {
			return ResponseEntity.ok(contaService.salvar(toEntity(conta), token));
		} catch (Exception e) {
			return ResponseEntity.status(400).body(new Problema(400, e.getMessage()));
		}
	}
	
	@GetMapping("/contas")
	public ResponseEntity<Object> find(
			@RequestParam(value="descricao", required = false, defaultValue = "") String descricao, 
			@RequestParam(value="id", required = false, defaultValue = "0") Long id, 
			@RequestParam(value="status", required = false) StatusGenerico status, 
			@RequestParam(value="tipoConta", required = false) TipoConta tipoConta,
			@RequestHeader("Authorization") String token) {
		try {
			return ResponseEntity.ok(contaService.find(descricao, id, status, tipoConta, token));
		} catch (Exception e) {
			return ResponseEntity.status(400).body(new Problema(400, e.getMessage()));
		}
	}
	
	@PutMapping("/contas/{id}")
	public ResponseEntity<Object> editar(@PathVariable("id") Long id, 
		@Valid @RequestBody ContaRequest conta, @RequestHeader("Authorization") String token) {
		try {
			Conta contaAuxiliar = toEntity(conta);
			contaAuxiliar.setId(id);
			return ResponseEntity.ok(contaService.editar(contaAuxiliar, token));
		} catch (Exception e) {
			return ResponseEntity.status(400).body(new Problema(400, e.getMessage()));
		}
	}
	
	@PutMapping("/contas/{id}/cancelar")
	public ResponseEntity<Object> cancelar(@PathVariable("id") Long id, 
		@RequestHeader("Authorization") String token) {
		try {
			return ResponseEntity.ok(contaService.cancelar(id, token));
		} catch (Exception e) {
			return ResponseEntity.status(400).body(new Problema(400, e.getMessage()));
		}
	}
	
	private Conta toEntity(ContaRequest contaRequest) {
		return modelMapper.map(contaRequest, Conta.class);
	}
}
