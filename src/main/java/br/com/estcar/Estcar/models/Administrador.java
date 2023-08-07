package br.com.estcar.Estcar.models;

import javax.persistence.*;

@Entity
@Table(name = "administradores")
public class Administrador {
	
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
	
	

}
