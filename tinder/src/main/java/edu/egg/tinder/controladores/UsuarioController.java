package edu.egg.tinder.controladores;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import edu.egg.tinder.entidades.Usuario;
import edu.egg.tinder.entidades.Zona;
import edu.egg.tinder.errores.ErrorServicio;
import edu.egg.tinder.repositorios.ZonaRepositorio;
import edu.egg.tinder.servicios.UsuarioServicio;

@Controller
@RequestMapping("/usuario")
public class UsuarioController {
	
	@Autowired
	private UsuarioServicio usuarioServicio;
	
	@Autowired
	private ZonaRepositorio zonaRepositorio;
	
	
	@PreAuthorize("hasAnyRole('ROLE_USUARIO_REGISTRADO')")
	@GetMapping("/editar-perfil")
	public String editarPerfil(HttpSession session , @RequestParam String id, ModelMap model) {
		
		List<Zona> zonas = zonaRepositorio.findAll();
		model.put("zonas", zonas);
		
		Usuario login = (Usuario) session.getAttribute("usuariosession");
		
		if(login == null || !login.getId().equals(id)) {
			return "redirect:/inicio";
		}
		
		Usuario usuario = null;
		
		try {
			
		usuario = usuarioServicio.buscarUsuarioPorId(id);
		
		if(usuario == null) {
			throw new ErrorServicio("El usuario está vacío.");
		}
		
		model.addAttribute("perfil", usuario);
		
		} catch (ErrorServicio e) {
			model.addAttribute("error", e.getMessage());
		}
		
		return "perfil.html";
		
	}
	
	@PreAuthorize("hasAnyRole('ROLE_USUARIO_REGISTRADO')")
	@PostMapping("/actualizar-perfil")
	public String registrar(ModelMap modelo, HttpSession session, MultipartFile archivo, @RequestParam String id, @RequestParam String nombre, @RequestParam String apellido, @RequestParam String email, @RequestParam String clave1, @RequestParam String clave2, @RequestParam String idZona) {
		
		Usuario usuario = null;
		
		try {
			
			Usuario login = (Usuario) session.getAttribute("usuariosession");
			
			if(login == null || !login.getId().equals(id)) {
				return "redirect:/inicio";
			}
			
			usuario = usuarioServicio.buscarUsuarioPorId(id);
			usuarioServicio.modificar(archivo, id, nombre, apellido, email, clave1, clave2, idZona);
			session.setAttribute("usuariosession", usuario);
			return "redirect:/inicio";
			
		} catch (ErrorServicio ex) {
			
			List<Zona> zonas = zonaRepositorio.findAll();
			modelo.put("zonas", zonas);
			modelo.put("error", ex.getMessage());
			modelo.put("perfil", usuario);
			
			return "perfil.html";
			
		}
		
	}
	
	
	
	
	
	
	
	
	
	
}
