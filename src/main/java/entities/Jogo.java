package entities;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class Jogo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Temporal(TemporalType.TIMESTAMP)
    private Date dataPartida;

    @Temporal(TemporalType.TIMESTAMP)
    private Date dataCadastro;

    private String time1;
    private String time2;

    @ManyToOne
    private Campeonato campeonato;

    private Integer golsTime1;
    private Integer golsTime2;
    
    
    

	public Jogo() {
		super();
	}

	public Jogo(Integer id, Date dataPartida, Date dataCadastro, String time1, String time2, Campeonato campeonato,
			Integer golsTime1, Integer golsTime2) {
		super();
		this.id = id;
		this.dataPartida = dataPartida;
		this.dataCadastro = dataCadastro;
		this.time1 = time1;
		this.time2 = time2;
		this.campeonato = campeonato;
		this.golsTime1 = golsTime1;
		this.golsTime2 = golsTime2;
	}

	public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getDataPartida() {
        return dataPartida;
    }

    public void setDataPartida(Date dataPartida) {
        this.dataPartida = dataPartida;
    }

    public Date getDataCadastro() {
        return dataCadastro;
    }

    public void setDataCadastro(Date dataCadastro) {
        this.dataCadastro = dataCadastro;
    }

    public String getTime1() {
        return time1;
    }

    public void setTime1(String time1) {
        this.time1 = time1;
    }

    public String getTime2() {
        return time2;
    }

    public void setTime2(String time2) {
        this.time2 = time2;
    }

    public Campeonato getCampeonato() {
        return campeonato;
    }

    public void setCampeonato(Campeonato campeonato) {
        this.campeonato = campeonato;
    }

    public Integer getGolsTime1() {
        return golsTime1;
    }

    public void setGolsTime1(Integer golsTime1) {
        this.golsTime1 = golsTime1;
    }

    public Integer getGolsTime2() {
        return golsTime2;
    }

    public void setGolsTime2(Integer golsTime2) {
        this.golsTime2 = golsTime2;
    }

    // Método para definir automaticamente a data de cadastro antes de persistir o objeto
    @PrePersist
    public void prePersist() {
        if (this.dataCadastro == null) {
            this.dataCadastro = new Date(); // Definindo a data atual
        }
    }
}
