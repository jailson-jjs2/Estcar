package br.com.estcar.Estcar.models;

import java.time.Duration;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "vaga_estacionamento")
public class VagaEstacionamento {
	
	@ManyToOne
	@JoinColumn(name = "administrador_id")
	private Administrador administrador;
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;
	
	@Column(name = "numVaga", length = 10, nullable = false)
	private String numVaga;
	
	@Column(name = "placaCarro", length = 10, nullable = false)
	private String placaCarro;
	
	@Column(name = "tempoEstacionado", length = 30, nullable = false)
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	private LocalDateTime tempoEstacionado;
	
	@Column(name = "valorAPagar")
	private double valorAPagar;
	
	
	

	public double getValorAPagar() {
		return valorAPagar;
	}

	public void setValorAPagar(double valorAPagar) {
		this.valorAPagar = valorAPagar;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNumVaga() {
		return numVaga;
	}

	public void setNumVaga(String numVaga) {
		this.numVaga = numVaga;
	}

	public String getPlacaCarro() {
		return placaCarro;
	}

	public void setPlacaCarro(String placaCarro) {
		this.placaCarro = placaCarro;
	}

	public LocalDateTime getTempoEstacionado() {
		return tempoEstacionado;
	}

	public void setTempoEstacionado(LocalDateTime tempoEstacionado) {
		this.tempoEstacionado = tempoEstacionado;
	}

	public Administrador getAdministrador() {
		return administrador;
	}

	public void setAdministrador(Administrador administrador) {
		this.administrador = administrador;
	}
	
	
	public String getPlacaFormatada() {
	    if (placaCarro != null && placaCarro.length() == 7) {
	    	String placaMaiuscula = placaCarro.toUpperCase();
	        return placaMaiuscula.substring(0, 3) + "-" + placaCarro.substring(3);
	    }
	    return placaCarro;
	}
	
	public String getTempoEstacionadoFormatado() {
		
        LocalDateTime now = LocalDateTime.now();
        Duration tempoDecorrido = Duration.between(tempoEstacionado, now);
        
        long horas = tempoDecorrido.toHours();
        long minutos = tempoDecorrido.toMinutesPart();
        
        if (horas > 0) {
            return String.format("%d H e %d min", horas, minutos);
        } else {
            return String.format("%d min", minutos);
        }
    }
}
