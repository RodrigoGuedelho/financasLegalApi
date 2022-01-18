package br.com.guedelho.controllers;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import br.com.guedelho.enums.StatusGenerico;
import br.com.guedelho.models.Usuario;
import br.com.guedelho.services.UsuarioService;
import br.com.guedelho.utils.Problema;

@RestController
@RequestMapping(value = "/api")
@CrossOrigin(origins = "*")
public class UsuarioController {
	
	@Autowired
	private UsuarioService usuarioService;
	
	@PostMapping("/usuarios")
	@ResponseStatus(value = HttpStatus.CREATED)
	public ResponseEntity<Object> salvar(@Valid @RequestBody Usuario usuario)  {
		try {
			try {	
				usuario = usuarioService.salvar(usuario);	
				return  ResponseEntity.ok(usuario);
			} catch (Exception e) {
				Problema problema = new Problema(400, e.getMessage());
				return ResponseEntity.status(problema.getStatus()).body(problema);
			}
		} catch (Exception e) {
			Problema problema = new Problema(400, e.getMessage());
			return ResponseEntity.status(400).body(problema);
		}
	}
	
	@PutMapping("/usuarios/{id}")
	@ResponseStatus(value = HttpStatus.ACCEPTED)
	public ResponseEntity<Object> editar(@PathVariable Long id, @Valid @RequestBody Usuario usuario)  {
		try {
			try {	
				usuario = usuarioService.editar(usuario, id);	
				return  ResponseEntity.ok(usuario);
			} catch (Exception e) {
				Problema problema = new Problema(400, e.getMessage());
				return ResponseEntity.status(problema.getStatus()).body(problema);
			}
		} catch (Exception e) {
			Problema problema = new Problema(400, e.getMessage());
			return ResponseEntity.status(400).body(problema);
		}
	}
	
	@GetMapping("/usuarios")
	public ResponseEntity<Object> find(
		@RequestParam(value="login", required = false, defaultValue = "") String login,
		@RequestParam(value="nome", required = false, defaultValue = "") String nome,
		@RequestParam(value="id", required = false, defaultValue="0") Long id, 
		@RequestParam(value="status", required = false) StatusGenerico status) {
		try {
			return ResponseEntity.ok(usuarioService.find(login, nome, id, status));
		} catch (Exception e) {
			Problema problema = new Problema(400, e.getMessage());
			System.out.println("e.getClass()" + e.getClass());
			return ResponseEntity.status(problema.getStatus()).body(problema);
		} 
	}
	
	@PutMapping("/usuarios/cancelar/{id}")
	public ResponseEntity<Object> cancelar(@PathVariable("id") Long id, @RequestHeader("Authorization") String token) {
		try {	
			return ResponseEntity.ok(usuarioService.cancelar(id));
		} catch (Exception e) {
			Problema problema = new Problema(400, e.getMessage());
			System.out.println("e.getClass()" + e.getClass());
			return ResponseEntity.status(problema.getStatus()).body(problema);
		} 
	}
	
	@PutMapping("/usuarios/{id}/upload")
	public ResponseEntity<Object> uploadImagem(@RequestParam("file") MultipartFile file, @PathVariable Long id) {
		try {	
			usuarioService.uploadImagem(id, file);
			return ResponseEntity.status(204).body(null);
		} catch (Exception e) {
			Problema problema = new Problema(400, e.getMessage());
			return ResponseEntity.status(problema.getStatus()).body(problema);
		}
	}
	
	@GetMapping(value = "/usuarios/upload/{usuarioId}", produces = "application/text")
	public ResponseEntity<Object> getUploadImagem(@PathVariable("usuarioId") Long usuarioId,
		 HttpServletRequest httpServletRequest
		) throws Exception {	
		String img;
		try {
			img = usuarioService.findImgById(usuarioId, httpServletRequest.getServletContext());
			return ResponseEntity.status(HttpStatus.OK).body(img);
		} catch (Exception e) {
			if (e.getMessage().equals("Usuário Não possui imagem"))
				return ResponseEntity.status(204).body(null);
			Problema problema = new Problema(400, e.getMessage());
			return ResponseEntity.status(problema.getStatus()).body(problema);
		}
	}
	
	@GetMapping(value = "/usuarios/upload/login/{login}", produces = "application/text")
	public ResponseEntity<Object> getUploadImagem(@PathVariable("login") String login,
		 HttpServletRequest httpServletRequest
		)  {	
		String img;
		try {
			img = usuarioService.findImgByLogin(login, httpServletRequest.getServletContext());
			return ResponseEntity.status(HttpStatus.OK).body(img);
		} catch (Exception e) {
			if (e.getMessage().equals("Usuário Não possui imagem"))
				return ResponseEntity.status(204).body(null);
			Problema problema = new Problema(400, e.getMessage());
			return ResponseEntity.status(problema.getStatus()).body(problema);
		}
		
	}

}
