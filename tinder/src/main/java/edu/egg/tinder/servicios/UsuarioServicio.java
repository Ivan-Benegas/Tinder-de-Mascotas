package edu.egg.tinder.servicios;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import edu.egg.tinder.entidades.Foto;
import edu.egg.tinder.entidades.Usuario;
import edu.egg.tinder.entidades.Zona;
import edu.egg.tinder.errores.ErrorServicio;
import edu.egg.tinder.repositorios.UsuarioRepositorio;
import edu.egg.tinder.repositorios.ZonaRepositorio;

@Service
public class UsuarioServicio implements UserDetailsService{
	
	@Autowired
	private UsuarioRepositorio usuarioRepositorio;
	
	//@Autowired
	//private NotificacionServicio notificacionServicio;
	
	@Autowired
	private ZonaRepositorio zonaRepositorio;
	
	@Autowired
	private FotoServicio fotoServicio;
	

	
	@Transactional
	public void registrar(MultipartFile archivo, String nombre, String apellido, String mail, String clave1, String clave2, String idZona) throws ErrorServicio{
		
		Zona zona = zonaRepositorio.getById(idZona);
		
		validar(nombre, apellido, mail, clave1, clave2, zona);
		
		
		Usuario usuario = new Usuario();
		
		usuario.setNombre(nombre);
		usuario.setApellido(apellido);
		usuario.setMail(mail);
		usuario.setZona(zona);
		
		String encriptada = new BCryptPasswordEncoder().encode(clave1);
		usuario.setClave(encriptada);
		
		usuario.setAlta(new Date());
		
		Foto foto = fotoServicio.guardar(archivo);
		usuario.setFoto(foto);
		
		usuarioRepositorio.save(usuario);
		
		//notificacionServicio.enviar("¡Bienvenido al Tinder de Mascota!", "Tinder de Mascota", usuario.getMail());
		
	}
	
	@Transactional
	public void modificar(MultipartFile archivo, String id, String nombre, String apellido, String mail, String clave1, String clave2, String idZona) throws ErrorServicio {
		
		Zona zona = zonaRepositorio.getById(idZona);
		
		validar(nombre, apellido, mail, clave1, clave2, zona);
		
		Optional<Usuario> respuesta = usuarioRepositorio.findById(id);
		
		if(respuesta.isPresent()) {
			
			Usuario usuario = respuesta.get();
			usuario.setNombre(nombre);
			usuario.setApellido(apellido);
			usuario.setMail(mail);
			usuario.setZona(zona);
			
			String encriptada = new BCryptPasswordEncoder().encode(clave1);
			usuario.setClave(encriptada);
			
			String idFoto = null;
			
			if(usuario.getFoto() != null) {
				
				idFoto = usuario.getFoto().getId();
				
			}
			
			Foto foto = fotoServicio.actualizar(idFoto, archivo);
			
			usuario.setFoto(foto);
			
			usuarioRepositorio.save(usuario);
		} else {
			throw new ErrorServicio("No se encontró el usuario solicitado.");
		}
		
	}
	
	@Transactional
	public void deshabilitar(String id) throws ErrorServicio {
		
		Optional<Usuario> respuesta = usuarioRepositorio.findById(id);
		
		if(respuesta.isPresent()) {
			
			Usuario usuario = respuesta.get();
			usuario.setBaja(new Date());
			usuarioRepositorio.save(usuario);
			
		} else {
			throw new ErrorServicio("No se encontró el usuario solicitado.");
		}
		
	}
	
	@Transactional
	public void habilitar(String id) throws ErrorServicio {
		
		Optional<Usuario> respuesta = usuarioRepositorio.findById(id);
		
		if(respuesta.isPresent()) {
			
			Usuario usuario = respuesta.get();
			usuario.setBaja(null);
			usuarioRepositorio.save(usuario);
			
		} else {
			throw new ErrorServicio("No se encontró el usuario solicitado.");
		}
		
	}
	
	private void validar(String nombre, String apellido, String mail, String clave1, String clave2, Zona zona) throws ErrorServicio{
		
		if(nombre == null || nombre.isEmpty()) {
			throw new ErrorServicio("El nombre del usuario no puede ser nulo.");
		}
		
		if(apellido == null || apellido.isEmpty()) {
			throw new ErrorServicio("El apellido del usuario no puede ser nulo.");
		}
		
		if(mail == null || mail.isEmpty()) {
			throw new ErrorServicio("El mail del usuario no puede ser nulo.");
		}
		
		if(clave1 == null || clave1.isEmpty() || clave1.length() <= 6) {
			throw new ErrorServicio("La clave del usuario no puede ser nula y tiene que tener más de 6 dígitos.");
		}
		
		if(!clave1.equals(clave2)) {
			throw new ErrorServicio("Las claves deben ser iguales.");
		}
		
		if(zona == null) {
			throw new ErrorServicio("No se encontró la zona solicitada.");
		}
		
	}

	@Override
	public UserDetails loadUserByUsername(String mail) throws UsernameNotFoundException {
		
		Usuario usuario = usuarioRepositorio.buscarPorMail(mail);
		
		if(usuario != null) {
			
			List<GrantedAuthority> permisos = new ArrayList<>();
			
			GrantedAuthority p1 = new SimpleGrantedAuthority("ROLE_USUARIO_REGISTRADO");
			permisos.add(p1);
			
			ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
			HttpSession session = attr.getRequest().getSession(true);
			session.setAttribute("usuariosession", usuario);
			
			User user = new User(usuario.getMail(), usuario.getClave(), permisos);
			
			return user;
		} else {
			return null;
		}
		
	}
	
	public Usuario buscarUsuarioPorId(String id) throws ErrorServicio{
		
		Optional<Usuario> respuesta = usuarioRepositorio.findById(id);
		
		if(respuesta.isPresent()) {
			return respuesta.get();
		} else {
			throw new ErrorServicio("El usuario solicitado no existe.");
		}
		
	}
	
}
