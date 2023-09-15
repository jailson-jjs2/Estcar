package br.com.estcar.Estcar.repositorio;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import br.com.estcar.Estcar.models.Administrador;

public interface AdministradoresRepo extends CrudRepository<Administrador, Integer> {
	
	// CASO USAR BUSCA COM QUERY
	@Query(value="select CASE WHEN count(1) > 0 THEN 'true' ELSE 'false' END from administradores where id = :id", nativeQuery = true)
	public boolean exist(int id);
	
	@Query(value="select * from administradores where email = :email and senha = :senha", nativeQuery = true)
	public Administrador findByEmailAndSenha(String email, String senha);
}
