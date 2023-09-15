package br.com.estcar.Estcar.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "dados_administrador")
public class DadosAdm {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToOne
    @JoinColumn(name = "administrador_id")
    private Administrador administrador;

    // Outros getters e setters...

    public Administrador getAdministrador() {
        return administrador;
    }

    public void setAdministrador(Administrador administrador) {
        this.administrador = administrador;
    }

    public void setAdministradorRelacionado(Administrador administrador) {
        this.administrador = administrador;
    }

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
}