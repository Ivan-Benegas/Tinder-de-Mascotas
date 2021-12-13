package edu.egg.tinder.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import edu.egg.tinder.entidades.Zona;

@Repository
public interface ZonaRepositorio extends JpaRepository<Zona, String>{
	
	
	
}
