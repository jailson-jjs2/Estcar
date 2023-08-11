package br.com.estcar.Estcar.repositorio;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import br.com.estcar.Estcar.models.VagaEstacionamento;

public interface VagaEstacionamentoRepo extends CrudRepository<VagaEstacionamento, Integer> {
	
	@Query(value="SELECT * FROM vaga_estacionamento WHERE placa_carro = :placa", nativeQuery = true)
    VagaEstacionamento findByPlacaCarro(String placa);
}
