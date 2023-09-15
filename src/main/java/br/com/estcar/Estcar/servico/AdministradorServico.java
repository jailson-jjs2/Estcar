package br.com.estcar.Estcar.servico;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.estcar.Estcar.models.Administrador;
import br.com.estcar.Estcar.models.VagaEstacionamento;
import br.com.estcar.Estcar.repositorio.AdministradoresRepo;
import br.com.estcar.Estcar.repositorio.VagaEstacionamentoRepo;

@Service
public class AdministradorServico {
	
	@Autowired
	private AdministradoresRepo administradoresRepo;
	
	@Autowired
	private VagaEstacionamentoRepo vagaEstacionamentoRepo;
	
	public void criarAdministradorComVaga(Administrador administrador, VagaEstacionamento vaga) {
		
		vaga.setAdministrador(administrador);
		administrador.getVagasEstacionamento().add(vaga);
		
		administradoresRepo.save(administrador);
		
	}

}
