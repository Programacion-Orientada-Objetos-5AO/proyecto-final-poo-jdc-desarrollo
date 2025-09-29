package ar.edu.huergo.jdecadido.rpg.mapper;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import ar.edu.huergo.jdecadido.rpg.dto.*;
import ar.edu.huergo.jdecadido.rpg.entity.*;

@Component
public class PersonajeMapper {

    public MostrarPersonajeDto toDTO(Personaje personaje) {
        if (personaje == null) return null;

        List<AtributoDto> atributos = personaje.getAtributos().stream()
                .map(a -> new AtributoDto(a.getNombre(), a.getValor()))
                .collect(Collectors.toList());

        List<InventarioDto> inventario = personaje.getInventario().stream()
                .map(i -> new InventarioDto(
                        i.getItem().getId(),
                        i.getItem().getNombre(),
                        i.getCantidad(),
                        i.isEquipado()
                ))
                .collect(Collectors.toList());

        return new MostrarPersonajeDto(
                personaje.getId(),
                personaje.getNombre(),
                personaje.getNivel(),
                personaje.getXp(),
                atributos,
                inventario
        );
    }

    public Personaje toEntity(CrearPersonajeDto dto) {
        if (dto == null) return null;

        Personaje personaje = new Personaje();
        personaje.setNombre(dto.getNombre());
        personaje.setNivel(dto.getNivel());
        personaje.setXp(dto.getXp());
        return personaje;
    }

    public List<MostrarPersonajeDto> toDTOList(List<Personaje> personajes) {
        if (personajes == null) {
            return new ArrayList<>();
        }
        return personajes.stream().map(this::toDTO).collect(Collectors.toList());
    }
}
