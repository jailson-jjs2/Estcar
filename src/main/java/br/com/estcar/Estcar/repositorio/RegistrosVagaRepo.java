package br.com.estcar.Estcar.repositorio;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import br.com.estcar.Estcar.models.Administrador;
import br.com.estcar.Estcar.models.RegistrosVaga;

public interface RegistrosVagaRepo extends CrudRepository<RegistrosVaga, Integer> {
	
	@Query(value="select CASE WHEN count(1) > 0 THEN 'true' ELSE 'false' END from registro_liberacao where id = :id", nativeQuery = true)
	public boolean exist(int id);
	
	// Consulta SQL nativa para calcular a soma dos valores pagos
    @Query(value = "SELECT SUM(valor_pago) FROM registro_liberacao", nativeQuery = true)
    Double calcularSomaValorPago();

	public List<RegistrosVaga> findByAdministrador(Administrador administradorLogado);
	
	@Query(value = "SELECT * FROM registro_liberacao WHERE MONTH(data_liberacao) = :mes AND administrador_id = :administradorId", nativeQuery = true)
    List<RegistrosVaga> findByAdministradorAndMonth(
        @Param("administradorId") int administradorId, 
        @Param("mes") String mes
    );




	
}
