package ar.edu.huergo.jdecadido.rpg.mapper;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import ar.edu.huergo.jdecadido.rpg.dto.AtributoDto;
import ar.edu.huergo.jdecadido.rpg.dto.CrearPersonajeDto;
import ar.edu.huergo.jdecadido.rpg.dto.InventarioDto;
import ar.edu.huergo.jdecadido.rpg.dto.MostrarPersonajeDto;
import ar.edu.huergo.jdecadido.rpg.entity.Atributo;
import ar.edu.huergo.jdecadido.rpg.entity.Personaje;

@Component
public class PersonajeMapper {

    /**
     * Convierte una entidad Personaje a DTO para mostrar
     */
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

    /**
     * Convierte un DTO de creación a entidad Personaje
     * Incluye el mapeo de atributos si están presentes
     */
    public Personaje toEntity(CrearPersonajeDto dto) {
        if (dto == null) return null;

        Personaje personaje = new Personaje();
        personaje.setNombre(dto.getNombre());
        personaje.setNivel(dto.getNivel());
        personaje.setXp(dto.getXp());
        
        // Mapear atributos si existen
        if (dto.getAtributos() != null && !dto.getAtributos().isEmpty()) {
            List<Atributo> atributos = dto.getAtributos().stream()
                .map(aDto -> {
                    Atributo atributo = new Atributo();
                    atributo.setNombre(aDto.getNombre());
                    atributo.setValor(aDto.getValor());
                    atributo.setPersonaje(personaje);  // IMPORTANTE: establecer la relación bidireccional
                    return atributo;
                })
                .collect(Collectors.toList());
            personaje.setAtributos(atributos);
        }
        
        return personaje;
    }

    /**
     * Convierte una lista de entidades a lista de DTOs
     */
    public List<MostrarPersonajeDto> toDTOList(List<Personaje> personajes) {
        if (personajes == null) {
            return new ArrayList<>();
        }
        return personajes.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }
}