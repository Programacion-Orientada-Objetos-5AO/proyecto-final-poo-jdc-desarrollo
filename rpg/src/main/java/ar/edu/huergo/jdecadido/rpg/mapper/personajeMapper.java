package ar.edu.huergo.jdecadido.rpg.mapper;

import org.springframework.stereotype.Component;

import ar.edu.huergo.jdecadido.rpg.dto.PersonajeDto;
import ar.edu.huergo.jdecadido.rpg.entity.Personaje;

@Component
public class personajeMapper {
    
    public PersonajeDto toDTO(Personaje personaje) {
        if (personaje == null) {
            return null;
        }
        return new PersonajeDto(personaje.getId() , personaje.getNombre(), personaje.getNivel() , personaje.getXp() , personaje.getAtributos() , personaje.getInventario());
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
}
