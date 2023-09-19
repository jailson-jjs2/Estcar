package br.com.estcar.Estcar.models;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "registro_liberacao")
public class RegistrosVaga {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;
	
	@ManyToOne
	@JoinColumn(name = "administrador_id")
	private Administrador administrador;

	@ManyToOne
	@JoinColumn(name = "placa_id")
	private VagaEstacionamento placa;
	
	@Column(name = "placa")
	private String placaCarro;
	
	@Column(name = "data_liberacao")
	private LocalDateTime dataLiberacao;
	
	@Column(name = "valor_pago")
	private double valorPago;
	
	@Column(name = "data_liberacao_formatada")
	@Transient
	private String dataLiberacaoFormatada;
	
	
	
	public String getPlacaCarro() {
		return placaCarro;
	}
	
	public void setPlacaCarro(String placaCarro) {
		this.placaCarro = placaCarro;
	}
	
	public VagaEstacionamento getPlaca() {
		return placa;
	}
	
	public void setPlaca(VagaEstacionamento placa) {
		this.placa = placa;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Administrador getAdministrador() {
		return administrador;
	}

	public void setAdministrador(Administrador administrador) {
		this.administrador = administrador;
	}

	public LocalDateTime getDataLiberacao() {
		return dataLiberacao;
	}

	public void setDataLiberacao(LocalDateTime dataLiberacao) {
		this.dataLiberacao = dataLiberacao;
	}

	public double getValorPago() {
		return valorPago;
	}

	public void setValorPago(double valorPago) {
		this.valorPago = valorPago;
	}
		

	public String getDataLiberecaoFormatada() {
		return dataLiberacaoFormatada;
	}

	public void setDataLiberecaoFormatada(String dataLiberecaoFormatada) {
		this.dataLiberacaoFormatada = dataLiberecaoFormatada;
	}
	
	
	
	public String getDataLiberacaoFormatada() {
		if (dataLiberacao != null) {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
			return dataLiberacao.format(formatter);
		}
		return null;
	}
}
