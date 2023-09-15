package br.com.estcar.Estcar.models;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

@Entity
@Table(name = "administradores")
public class Administrador {
	
	@OneToMany(mappedBy = "administrador", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<VagaEstacionamento> vagasEstacionamento = new ArrayList<>();
	
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;
	
	@Column(name = "nome", length = 100, nullable = false) // O SPRING BOOT PODERIA FAZER AUTO, CRIAR O BANCO
	private String nome;
	
	@Column(name = "email", length = 100, nullable = false) // Ñ ACEITA EM BRANCO NO BANCO
	private String email;
	
	@Column(name = "senha", length = 100, nullable = false)
	private String senha;
	
	@Column(name = "quant_vaga", length = 100, nullable = false)
	private int quant_vaga;
	
	@Column(name = "valor_estacionamento", length = 100, nullable = false)
	private double valor_estacionamento;
	
	@Column(name = "valor_hora", length = 100, nullable = false)
	private double valor_hora;
	
	
	public int getQuant_vaga() {
		return quant_vaga;
	}
	
	public void setQuant_vaga(int quant_vaga) {
		this.quant_vaga = quant_vaga;
	}

	public double getValor_estacionamento() {
		return valor_estacionamento;
	}

	public void setValor_estacionamento(double valor_estacionamento) {
		this.valor_estacionamento = valor_estacionamento;
	}

	public double getValor_hora() {
		return valor_hora;
	}

	public void setValor_hora(double valor_hora) {
		this.valor_hora = valor_hora;
	}


	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSenha() {
		return senha.substring(0, 2) + "*****";
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public List<VagaEstacionamento> getVagasEstacionamento() {
		return vagasEstacionamento;
	}

	public void setVagasEstacionamento(List<VagaEstacionamento> vagasEstacionamento) {
		this.vagasEstacionamento = vagasEstacionamento;
	}
}
