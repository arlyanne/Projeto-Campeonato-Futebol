package bean;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.context.Flash;

import dao.CampeonatoDao;
import dao.JogoDao;
import entities.Campeonato;
import entities.Jogo;

@ManagedBean
@ViewScoped
public class JogoBean {
    
    private Jogo jogo = new Jogo();
    private List<Campeonato> campeonatos;
    private Integer campeonatoId;
    private List<Jogo> listaJogos;
    
    @PostConstruct
    public void init() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        Flash flash = facesContext.getExternalContext().getFlash();
        if (flash.containsKey("jogo")) {
            jogo = (Jogo) flash.get("jogo");
        }
    }
    
    public String salvar() {
        // Validação para garantir que os times não sejam iguais
        if (jogo.getTime1().equals(jogo.getTime2())) {
            FacesContext.getCurrentInstance().addMessage(null, 
                new FacesMessage(FacesMessage.SEVERITY_ERROR, 
                "Erro", "Não é permitido cadastrar um jogo com times iguais"));
            return null; // Retorna null para não prosseguir com o salvamento
        }
        
        try {
            // Vincula o campeonato ao jogo, se um campeonato for selecionado
            if (campeonatoId != null) {
                Campeonato campeonato = CampeonatoDao.findOneCampeonato(campeonatoId);
                jogo.setCampeonato(campeonato); // Atribui o campeonato ao jogo
            }
            
            // Preenche automaticamente a data de cadastro com a data e hora atual
            jogo.setDataCadastro(new Date());
            
            // Salva o jogo no banco de dados
            if(jogo.getId() != null) {
            	JogoDao.edit(jogo);
            }else {
            JogoDao.save(jogo);
            }
            
            // Exibe uma mensagem de sucesso
            FacesContext.getCurrentInstance().addMessage(null, 
                new FacesMessage(FacesMessage.SEVERITY_INFO, 
                "Sucesso", "Jogo cadastrado com sucesso"));
            
            // Limpa os campos após salvar
            jogo = new Jogo(); // Cria um novo objeto jogo para o próximo cadastro
            campeonatoId = null; // Limpa o ID do campeonato
            
            // Redireciona para a página de listagem de jogos
            return "listagem.xhtml?faces-redirect=true";
        } catch (Exception e) {
            // Exibe uma mensagem de erro caso ocorra algum problema
            FacesContext.getCurrentInstance().addMessage(null, 
                new FacesMessage(FacesMessage.SEVERITY_ERROR, 
                "Erro", "Erro ao cadastrar jogo"));
            return null; // Retorna null para não prosseguir com o redirecionamento
        }
    }
    
    public void editarJogo(Jogo j) {
        try {

            jogo.setId(j.getId());
            jogo.setCampeonato(j.getCampeonato());
            jogo.setDataCadastro(j.getDataCadastro());
            jogo.setDataPartida(j.getDataPartida());
            jogo.setGolsTime1(j.getGolsTime1());
            jogo.setGolsTime2(j.getGolsTime2());
            jogo.setTime1(j.getTime1());
            jogo.setTime2(j.getTime2());

            // Armazena o objeto jogo no Flash
            FacesContext facesContext = FacesContext.getCurrentInstance();
            Flash flash = facesContext.getExternalContext().getFlash();
            flash.put("jogo", jogo);

            // Redireciona para a página de cadastro de jogo
            facesContext.getExternalContext().redirect("cadastro_jogo.xhtml?faces-redirect=true");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void excluirJogo(Jogo jogo) {
        try {
        	JogoDao.delete(jogo.getId());
        	 FacesContext.getCurrentInstance().addMessage(null, 
                     new FacesMessage(FacesMessage.SEVERITY_INFO, 
                     "Sucesso", "Jogo excluido com sucesso"));

            FacesContext.getCurrentInstance().getExternalContext().redirect("listagem.xhtml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    
    public List<Jogo> getListaJogos() {
        if (listaJogos == null) {
            listaJogos = JogoDao.list();
        }
        return listaJogos;
    }
    
    public void setListaJogos(List<Jogo> listaJogos) {
        this.listaJogos = listaJogos;
    }
    
    public Integer getCampeonatoId() {
        return campeonatoId;
    }
    
    public void setCampeonatoId(Integer campeonatoId) {
        this.campeonatoId = campeonatoId;
    }
    
    public Jogo getJogo() {
        return jogo;
    }
    
    public void setJogo(Jogo jogo) {
        this.jogo = jogo;
    }
    
    public List<Campeonato> getCampeonatos() {
        if (campeonatos == null) {
            campeonatos = CampeonatoDao.list();
        }
        return campeonatos;
    }
}