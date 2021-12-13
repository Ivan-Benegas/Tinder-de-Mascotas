package edu.egg.tinder.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import edu.egg.tinder.entidades.Usuario;

@Repository
public interface UsuarioRepositorio extends JpaRepository<Usuario, String>{
	
	@Query("SELECT c FROM Usuario c WHERE c.mail = :mail")
	public Usuario buscarPorMail(@Param("mail") String mail);
	
	@Query("SELECT c FROM Usuario c WHERE c.id = :id")
	public Usuario buscarPorId(@Param("id") String id);
	
}
