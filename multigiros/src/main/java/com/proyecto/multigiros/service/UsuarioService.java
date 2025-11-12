package com.proyecto.multigiros.service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.proyecto.multigiros.entity.Usuario;
import com.proyecto.multigiros.repository.UsuarioRepository;

@RestController
@RequestMapping("/usuario")
@CrossOrigin(origins = "http://localhost:4200")
public class UsuarioService {
    @Autowired
    private UsuarioRepository dbUsuario;

    @GetMapping("/listar")
    public List<Usuario> listar() {
        return dbUsuario.findAll();
    }

    @GetMapping("/buscar/{id}")
    public Optional<Usuario> buscar(@PathVariable Long id) {
        return dbUsuario.findById(id);
    }

    @GetMapping("/buscarporusuario/{usuario}")
    public Optional<Usuario> buscarporusuario(@PathVariable String usuario) {
        return dbUsuario.findByNombreUsuario(usuario);
    }

    @PostMapping("/insertar")
    public Usuario insertar(@RequestBody Usuario usuario) {
        usuario.setFechaCreacion(LocalDateTime.now());
        usuario.setActivo(true);
        usuario.setBloqueado(false);
        usuario.setIntentosFallidos(0);
        return dbUsuario.save(usuario);
    }

    @PutMapping("/modificar")
    public Usuario modificar(@RequestBody Usuario usuario) {
        return dbUsuario.save(usuario);
    }

    @DeleteMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Long id) {
        dbUsuario.deleteById(id);
        return "Éxito: Usuario eliminado: " + id;
    }

    @PostMapping("/login")
    public Map<String, Object> login(@RequestBody Map<String, String> credenciales) {
        Map<String, Object> respuesta = new HashMap<>();
        
        String email = credenciales.get("email");
        String password = credenciales.get("password");
        
        Optional<Usuario> usuarioOpt = dbUsuario.findByEmail(email);
        
        if (usuarioOpt.isPresent()) {
            Usuario usuario = usuarioOpt.get();
            
            // Verificar si el usuario está activo y no bloqueado
            if (!usuario.getActivo()) {
                respuesta.put("success", false);
                respuesta.put("message", "Usuario inactivo");
                return respuesta;
            }
            
            if (usuario.getBloqueado()) {
                respuesta.put("success", false);
                respuesta.put("message", "Usuario bloqueado");
                return respuesta;
            }
            
            // Verificar contraseña (en un sistema real usarías BCrypt)
            if (usuario.getPasswordHash().equals(password)) {
                // Login exitoso
                usuario.setFechaUltimoLogin(LocalDateTime.now());
                usuario.setIntentosFallidos(0);
                dbUsuario.save(usuario);
                
                respuesta.put("success", true);
                respuesta.put("message", "Login exitoso");
                respuesta.put("usuario", usuario);
                return respuesta;
            } else {
                // Contraseña incorrecta
                usuario.setIntentosFallidos(usuario.getIntentosFallidos() + 1);
                
                // Bloquear usuario después de 3 intentos fallidos
                if (usuario.getIntentosFallidos() >= 3) {
                    usuario.setBloqueado(true);
                }
                
                dbUsuario.save(usuario);
                
                respuesta.put("success", false);
                respuesta.put("message", "Credenciales incorrectas");
                respuesta.put("intentosRestantes", 3 - usuario.getIntentosFallidos());
                return respuesta;
            }
        } else {
            respuesta.put("success", false);
            respuesta.put("message", "Usuario no encontrado");
            return respuesta;
        }
    }
}
