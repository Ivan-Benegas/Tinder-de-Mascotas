package edu.egg.tinder.controladores;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import edu.egg.tinder.entidades.Zona;
import edu.egg.tinder.errores.ErrorServicio;
import edu.egg.tinder.repositorios.ZonaRepositorio;
import edu.egg.tinder.servicios.UsuarioServicio;

@Controller
@RequestMapping("/")
public class PortalControlador {
	
	@Autowired
	private UsuarioServicio usuarioServicio;
	
	@Autowired
	private ZonaRepositorio zonaRepositorio;
	
	@GetMapping("/")
	public String index() {
		return "index.html";
	}
	
	@PreAuthorize("hasAnyRole('ROLE_USUARIO_REGISTRADO')")
	@GetMapping("/inicio")
	public String inicio() {
		return "inicio.html";
	}
	
	@GetMapping("/login")
	public String login(@RequestParam(required = false) String error, @RequestParam(required = false) String logout, ModelMap model) {
		if(error != null) {
			model.put("error", "Nombre de usuario o clave incorrectos.");
		}
		
		if(logout != null) {
			model.put("logout", "Ha salido correctamente de la plataforma.");
		}
		
		return "login.html";
	}
	
	@GetMapping("/registro")
	public String registro(ModelMap modelo) {
		List<Zona> zonas = zonaRepositorio.findAll();
		modelo.put("zonas", zonas);
		return "registro.html";
	}
	
	@PostMapping("/registrar")
	public String registrar(ModelMap modelo, MultipartFile archivo , @RequestParam String nombre, @RequestParam String apellido, @RequestParam String email, @RequestParam String clave1, @RequestParam String clave2, @RequestParam String idZona) {
		System.out.println("Nombre: " + nombre);
		System.out.println("Apellido: " + apellido);
		System.out.println("Email: " + email);
		System.out.println("Clave1: " + clave1);
		System.out.println("Clave2: " + clave2);
		System.out.println("Zona: " + idZona);
		
		try {
			usuarioServicio.registrar(archivo, nombre, apellido, email, clave1, clave2, idZona);
		} catch (ErrorServicio e) {
			List<Zona> zonas = zonaRepositorio.findAll();
			modelo.put("zonas", zonas);
			
			modelo.put("error", e.getMessage());
			modelo.put("nombre", nombre);
			modelo.put("apellido", apellido);
			modelo.put("email", email);
			modelo.put("clave1", clave1);
			modelo.put("clave2", clave2);
			e.printStackTrace();
			return "registro.html";
		}
		
		modelo.put("titulo", "Bienvenido a Tinder de Mascotas.");
		modelo.put("descripcion", "Tu usuario fue registrado de manera satisfactoria.");
		return "exito.html";
	}
	
	
}
