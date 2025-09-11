package ar.edu.huergo.jdecadido.rpg.dto.security;

import java.util.List;

public record UsuarioDto(String username, List<String> roles) {
    
}