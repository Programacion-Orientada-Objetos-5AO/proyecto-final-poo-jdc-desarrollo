package ar.edu.huergo.jdecadido.rpg.mapper;

import java.util.List;

import org.springframework.stereotype.Component;

import ar.edu.huergo.jdecadido.rpg.dto.security.RegistrarDto;
import ar.edu.huergo.jdecadido.rpg.dto.security.UsuarioDto;
import ar.edu.huergo.jdecadido.rpg.entity.Rol;
import ar.edu.huergo.jdecadido.rpg.entity.Usuario;

@Component
public class UsuarioMapper {
    public UsuarioDto toDTO(Usuario usuario) {
        if (usuario == null) {
            return null;
        }
        return new UsuarioDto(usuario.getUsername(), usuario.getRoles().stream()
                .map(Rol::getNombre)
                .toList());
    }

    public List<UsuarioDto> toDTOList(List<Usuario> usuarios) {
        return usuarios.stream()
                .map(this::toDTO)
                .toList();
    }

    public Usuario toEntity(RegistrarDto registrarDTO) {
        if (registrarDTO == null) {
            return null;
        }
        Usuario usuario = new Usuario();
        usuario.setUsername(registrarDTO.username());
        return usuario;
    }
}
