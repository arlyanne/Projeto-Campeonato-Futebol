package bean;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import dao.UsuarioDao;

import entities.Usuario;

@ManagedBean
public class UsuarioBean {
	
	private Usuario usuario = new Usuario();
	
	private List<Usuario> lista = new ArrayList<Usuario>();

	public String save() {
		try {
			UsuarioDao.save(usuario);
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Sucesso", "Usuario cadastrado com sucesso!"));
			usuario = new Usuario();
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Ops! Não foi possível realizar essa operação."));
		}
		
		return null;
	}
	
	public List<Usuario> getLista() {
		lista = UsuarioDao.listarTodos();
		return lista;
	}
	
	public String autenticar() {
		Usuario userAuth = UsuarioDao.searchLogin(usuario.getLogin());
		
		if(userAuth == null) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Usuário não encontrado.", ""));
			return null;
		}
		
		 if (!userAuth.getSenha().equals(usuario.getSenha())) {
	            FacesContext.getCurrentInstance().addMessage(null,
	                new FacesMessage(FacesMessage.SEVERITY_ERROR, "Senha incorreta", ""));
	            return null;
	        }
		 
		 return "opcoes.xhtml?faces-redirect=true";
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	
	
}
