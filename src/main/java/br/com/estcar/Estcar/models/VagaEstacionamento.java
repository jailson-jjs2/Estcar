package br.com.estcar.Estcar.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "vaga_estacionamento")
public class VagaEstacionamento {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;
	
	@Column(name = "numVaga", length = 10, nullable = false)
	private String numVaga;
	
	@Column(name = "placaCarro", length = 10, nullable = false)
	private String placaCarro;
	
	@Column(name = "tempoEstacionado", length = 10, nullable = false)
	private int tempoEstacionado;
	

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

	public int getTempoEstacionado() {
		return tempoEstacionado;
	}

	public void setTempoEstacionado(int tempoEstacionado) {
		this.tempoEstacionado = tempoEstacionado;
	}
	

}
