package ar.edu.huergo.jdecadido.rpg.mapper;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import ar.edu.huergo.jdecadido.rpg.dto.MostrarPersonajeDto;
import ar.edu.huergo.jdecadido.rpg.dto.PersonajeDto;
import ar.edu.huergo.jdecadido.rpg.entity.Personaje;

@Component
public class PersonajeMapper {
    
    public MostrarPersonajeDto toDTO(Personaje personaje) {
        if (personaje == null) {
            return null;
        }
        return new MostrarPersonajeDto(personaje.getId() , personaje.getNombre(), personaje.getNivel() , personaje.getXp() , personaje.getAtributos() , personaje.getInventario());
    }

    public Personaje toEntity(PersonajeDto dto) {
        if (dto == null) {
            return null;
        }
        Personaje personaje = new Personaje();
        personaje.setNombre(dto.getNombre());
        personaje.setNivel(dto.getNivel());
        personaje.setXp(dto.getXp());
        personaje.setAtributos(dto.getAtributos());
        personaje.setInventario(dto.getInventario());
        return personaje;
    }

    public List<MostrarPersonajeDto> toDTOList(List<Personaje> personajes) {
        if (personajes == null) {
            return new ArrayList<>();
        }
        return personajes.stream().map(this::toDTO).collect(Collectors.toList());
    }
}
