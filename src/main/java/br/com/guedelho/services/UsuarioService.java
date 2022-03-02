package br.com.guedelho.services;

import java.util.List;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import br.com.guedelho.enums.StatusGenerico;
import br.com.guedelho.models.Usuario;
import br.com.guedelho.repository.UsuarioRepository;
import br.com.guedelho.utils.Utils;


@Service
public class UsuarioService {
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	private String CAMINHO_IMG = Utils.CAMINHO_RAIZ_IMAGEM + "usuarios/";
	
	public Usuario salvar(Usuario usuario) throws Exception {
		Exception validaUsuario = validaUsuario(usuario);
		
		if (validaUsuario != null)
			throw validaUsuario;
		usuario.setStatus(StatusGenerico.ATIVO);
		usuario.setSenha(new BCryptPasswordEncoder().encode(usuario.getSenha()));
		return usuarioRepository.save(usuario);
	}
	
	public Usuario editar(Usuario usuario, Long id, String token) throws Exception {
		Exception validaUsuario = validaUsuarioEditar(usuario, id, token);
		
		if (validaUsuario != null)
			throw validaUsuario;
		Usuario usuarioAux = usuarioRepository.findById(id).get();
		usuario.setId(id);
		usuario.setStatus(StatusGenerico.ATIVO);
		usuario.setSenha(new BCryptPasswordEncoder().encode(usuario.getSenha()));
		usuario.setNomeImagem(usuarioAux.getNomeImagem());
		return usuarioRepository.save(usuario);
	}
	
	public Exception validaUsuario(Usuario usuario) {
		Usuario usuarioExiste = usuarioRepository.findUserByLogin(usuario.getLogin());
		if(usuarioExiste != null) {
			return new Exception("Usuário com esse nome já existe");
		}
		
		return null;
	}
	
	public Exception validaUsuarioEditar(Usuario usuario, Long id, String token) {
		Usuario usuarioExiste = usuarioRepository.findUserByLogin(usuario.getLogin());
		Usuario usuarioAuxiliar = usuarioRepository.findById(id).get();
		Usuario usuarioLogado = Utils.getUsuarioLogado(token);
		if (usuarioAuxiliar == null)
			return new Exception("Usuário com esse id não existe");
		if(usuarioExiste != null && !usuarioExiste.getId().equals(id)) {
			return new Exception("Usuário com esse nome já existe");
		}
		if(!usuarioAuxiliar.getStatus().equals(StatusGenerico.ATIVO)) {
			return new Exception("Usuário Está cancelado");
		}
		if (!usuarioLogado.getId().equals(usuarioAuxiliar.getId()) )
			return new Exception("Usuário não pode editar informações de outro usuário.");
		return null;
	}
	
	public Usuario cancelar(Long id) throws Exception {
		Usuario usuarioAuxiliar = usuarioRepository.findById(id).get();
		if (usuarioAuxiliar.getStatus().equals(StatusGenerico.CANCELADO) && usuarioAuxiliar != null) 
			throw new Exception("Usuário está cancelada.");
		
		usuarioAuxiliar.setStatus(StatusGenerico.CANCELADO);
		return usuarioRepository.save(usuarioAuxiliar);
	}
	
	public List<Usuario> find(String login, String nome, Long id, StatusGenerico status) {
		return usuarioRepository.find("%" + login + "%", "%" + nome + "%", id, status);
	}
	
	public Usuario uploadImagem(Long id, MultipartFile file) throws Exception {
		if (file.isEmpty())
			throw new Exception("Upload vazio.");
		Usuario usuarioAxiliar = usuarioRepository.findById(id).get();
		if (usuarioAxiliar == null)
			throw new Exception("Id do produto invalido.");	
		try {
			String nomeImagem = Utils.uploadImagem(id, file, CAMINHO_IMG);	
			usuarioAxiliar.setNomeImagem(nomeImagem);
			return usuarioRepository.save(usuarioAxiliar);	
		} catch (Exception e) {
			throw new Exception("Erro inesperado ao fazer o upload do arquivo");
		}
	}
	
	public String findImgById(Long usuarioId, ServletContext servletContext) throws Exception {
		Usuario usuario = usuarioRepository.findById(usuarioId).get();	
		if (usuario == null)
			throw new Exception("Id do usuário invalido.");
		if (usuario.getNomeImagem().isEmpty())
			throw new Exception("Usuário Não possui imagem");
		
		return Utils.getImagem(CAMINHO_IMG + usuario.getNomeImagem());
	}

	public Usuario findByToken(String token) throws Exception {
		Usuario usuario = Utils.getUsuarioLogado(token);
		if (usuario == null)
			throw new Exception("Id do usuário invalido.");
	
		return usuario;
	}
	
	public String findImgByLogin(String login, ServletContext servletContext) throws Exception {
		Usuario usuario = usuarioRepository.findUserByLogin(login);
		if (usuario == null)
			throw new Exception("Id do usuário invalido.");
		if (Utils.isEmpity(usuario.getNomeImagem()))
			throw new Exception("Usuário Não possui imagem");
		
		return Utils.getImagem(CAMINHO_IMG + usuario.getNomeImagem());
	}
	
}
