package br.com.estcar.Estcar.repositorio;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import br.com.estcar.Estcar.models.RegistrosVaga;

public interface RegistrosVagaRepo extends CrudRepository<RegistrosVaga, Integer> {
	
	@Query(value="select CASE WHEN count(1) > 0 THEN 'true' ELSE 'false' END from registro_liberacao where id = :id", nativeQuery = true)
	public boolean exist(int id);
	
}
