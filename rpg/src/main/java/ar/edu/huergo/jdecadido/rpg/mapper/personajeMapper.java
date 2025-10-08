package ar.edu.huergo.jdecadido.rpg.mapper;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import ar.edu.huergo.jdecadido.rpg.dto.*;
import ar.edu.huergo.jdecadido.rpg.entity.*;

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

        List<ItemInventarioDto> inventario = personaje.getInventario().stream()
                .map(i -> new ItemInventarioDto(
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
                personaje.getVidaMax(),
                personaje.getVidaActual(),
                personaje.estaVivo(),
                atributos,
                inventario
        );
    }

    /**
     * Convierte un DTO de creación a entidad Personaje
     * La vida actual se inicializa igual a la vida máxima
     */
    public Personaje toEntity(CrearPersonajeDto dto) {
        if (dto == null) return null;

        Personaje personaje = new Personaje();
        personaje.setNombre(dto.getNombre());
        personaje.setNivel(dto.getNivel());
        personaje.setXp(dto.getXp());
        personaje.setVidaMax(dto.getVidaMax());
        personaje.setVidaActual(dto.getVidaMax()); // Inicia con vida completa
        
        // Mapear atributos si existen
        if (dto.getAtributos() != null && !dto.getAtributos().isEmpty()) {
            List<Atributo> atributos = dto.getAtributos().stream()
                .map(aDto -> {
                    Atributo atributo = new Atributo();
                    atributo.setNombre(aDto.getNombre());
                    atributo.setValor(aDto.getValor());
                    atributo.setPersonaje(personaje);  // Establecer relación bidireccional
                    atributo.setEnemigo(null);         // No es de enemigo
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