package bean;

import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import dao.CampeonatoDao;
import entities.Campeonato;

@ManagedBean
@ViewScoped
public class CampeonatoBean {
	private Campeonato campeonato = new Campeonato();
	private List<Campeonato> campeonatos;
	
	public Campeonato getCampeonato() {
		return campeonato;
	}
	
	public void setCampeonato(Campeonato campeonato) {
		this.campeonato = campeonato;
	}
	
	public List<Campeonato> getCampeonatos() {
		if (campeonatos == null) {
			campeonatos = CampeonatoDao.list();
		}
		return campeonatos;
	}

	public void setCampeonatos(List<Campeonato> campeonatos) {
		this.campeonatos = campeonatos;
	}
	
	public String save() {
		if (campeonato != null) {
			CampeonatoDao.save(campeonato);
			campeonato = new Campeonato();
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, "Sucesso!", "Campeonato criado com sucesso!"));
			campeonatos = CampeonatoDao.list();
			return "listagem.xhtml";
		}
		return null;
	}
	
	public String edit(Campeonato campeonatoEdit) {
		if (campeonatoEdit != null) {
			CampeonatoDao.edit(campeonatoEdit);

			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, "Sucesso!", "Campeonato atualizado com sucesso!"));
			campeonatos = CampeonatoDao.list();
			return null;

		}
		return null;

	}

	public void delete(Campeonato campeonato) {
		CampeonatoDao.delete(campeonato.getId());
		campeonatos = CampeonatoDao.list();
	}

}
