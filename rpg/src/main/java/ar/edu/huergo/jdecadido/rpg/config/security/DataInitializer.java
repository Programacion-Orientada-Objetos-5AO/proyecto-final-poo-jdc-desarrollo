package ar.edu.huergo.jdecadido.rpg.config.security;

import java.util.Set;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import ar.edu.huergo.jdecadido.rpg.entity.Rol;
import ar.edu.huergo.jdecadido.rpg.entity.Usuario;
import ar.edu.huergo.jdecadido.rpg.repository.RolRepository;
import ar.edu.huergo.jdecadido.rpg.repository.UsuarioRepository;

@Configuration
public class DataInitializer {
    
    @Bean
    CommandLineRunner initData(RolRepository rolRepository, UsuarioRepository usuarioRepository, PasswordEncoder encoder) {
        return args -> {
            Rol admin = rolRepository.findByNombre("ADMIN").orElseGet(() -> rolRepository.save(new Rol("ADMIN")));
            Rol cliente = rolRepository.findByNombre("CLIENTE").orElseGet(() -> rolRepository.save(new Rol("CLIENTE")));

            if (usuarioRepository.findByUsername("admin").isEmpty()) {
                Usuario u = new Usuario("admin", encoder.encode("admin123"));
                u.setRoles(Set.of(admin));
                usuarioRepository.save(u);
            }

            if (usuarioRepository.findByUsername("cliente").isEmpty()) {
                Usuario u = new Usuario("cliente", encoder.encode("cliente123"));
                u.setRoles(Set.of(cliente));
                usuarioRepository.save(u);
            }
        };
    }
}
