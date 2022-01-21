package br.com.guedelho.controllers;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.guedelho.dto.GrupoContaRequest;
import br.com.guedelho.enums.StatusGenerico;
import br.com.guedelho.models.GrupoConta;
import br.com.guedelho.services.GrupoContaService;
import br.com.guedelho.utils.Problema;

@RestController
@RequestMapping(value = "/api")
public class GrupoContaController {
	
	@Autowired
	private GrupoContaService grupoContaService;
	@Autowired
	private ModelMapper modelMapper;
	
	@PostMapping("/grupo-contas")
	public ResponseEntity<Object> salvar(@Valid @RequestBody GrupoContaRequest grupoConta) {
		try {
			return ResponseEntity.ok(grupoContaService.salvar(toEntity(grupoConta)));
		} catch (Exception e) {
			return ResponseEntity.status(400).body(new Problema(400, e.getMessage()));
		}
	}
	
	@PutMapping("/grupo-contas/{id}")
	public ResponseEntity<Object> editar(@PathVariable("id") Long id, @Valid @RequestBody GrupoContaRequest grupoConta) {
		try {
			GrupoConta gruContaAuxiliar = toEntity(grupoConta);
			gruContaAuxiliar.setId(id);
			return ResponseEntity.ok(grupoContaService.editar(gruContaAuxiliar));
		} catch (Exception e) {
			return ResponseEntity.status(400).body(new Problema(400, e.getMessage()));
		}
	}
	
	@PutMapping("/grupo-contas/{id}/cancelar")
	public ResponseEntity<Object> cancelar(@PathVariable("id") Long id) {
		try {
			return ResponseEntity.ok(grupoContaService.cancelar(id));
		} catch (Exception e) {
			return ResponseEntity.status(400).body(new Problema(400, e.getMessage()));
		}
	}
	
	@GetMapping("/grupo-contas")
	public ResponseEntity<Object> find(@RequestParam(value="id", required = false, defaultValue = "0") Long id,
			@RequestParam(value="descricao", required = false, defaultValue = "") String descricao, 
			@RequestParam(value="status", required = false) StatusGenerico status) {
		try {
			return ResponseEntity.ok(grupoContaService.find(descricao, id, status));
		} catch (Exception e) {
			return ResponseEntity.status(400).body(new Problema(400, e.getMessage()));
		}
	}
	
	private GrupoConta toEntity(GrupoContaRequest grupoContaRequest) {
		return modelMapper.map(grupoContaRequest, GrupoConta.class);
	}
}
