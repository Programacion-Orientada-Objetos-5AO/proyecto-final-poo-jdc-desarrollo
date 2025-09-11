package ar.edu.huergo.jdecadido.rpg.controller.security;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ar.edu.huergo.jdecadido.rpg.dto.security.RegistrarDto;
import ar.edu.huergo.jdecadido.rpg.dto.security.UsuarioDto;
import ar.edu.huergo.jdecadido.rpg.entity.Usuario;
import ar.edu.huergo.jdecadido.rpg.mapper.UsuarioMapper;
import ar.edu.huergo.jdecadido.rpg.service.security.UsuarioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/usuarios")
public class UsuarioController {
    private final UsuarioService usuarioService;
    private final UsuarioMapper usuarioMapper;
    
    @PostMapping("/registrar")
    public ResponseEntity<UsuarioDto> registrarCliente(@Valid @RequestBody RegistrarDto registrarDto) {
        Usuario usuario = usuarioMapper.toEntity(registrarDto);
        Usuario nuevoUsuario = usuarioService.registrar(usuario, registrarDto.password(), registrarDto.verificacionPassword());
        UsuarioDto nuevoUsuarioDto = usuarioMapper.toDTO(nuevoUsuario);
        return ResponseEntity.ok(nuevoUsuarioDto);
    }


    @GetMapping
    public ResponseEntity<List<UsuarioDto>> getAllUsuarios() {
        List<Usuario> usuarios = usuarioService.getAllUsuarios();
        List<UsuarioDto> usuarioDTOs = usuarioMapper.toDTOList(usuarios);
        return ResponseEntity.ok(usuarioDTOs);
    }
}
