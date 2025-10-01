package ar.edu.huergo.jdecadido.rpg.mapper;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import ar.edu.huergo.jdecadido.rpg.dto.AtributoDto;
import ar.edu.huergo.jdecadido.rpg.dto.CrearEnemigoDto;
import ar.edu.huergo.jdecadido.rpg.dto.MostrarEnemigoDto;
import ar.edu.huergo.jdecadido.rpg.entity.Atributo;
import ar.edu.huergo.jdecadido.rpg.entity.Enemigo;

@Component
public class EnemigoMapper {

    /**
     * Convierte una entidad Enemigo a DTO para mostrar
     */
    public MostrarEnemigoDto toDTO(Enemigo enemigo) {
        if (enemigo == null) return null;

        List<AtributoDto> atributos = enemigo.getAtributos().stream()
                .map(a -> new AtributoDto(a.getNombre(), a.getValor()))
                .collect(Collectors.toList());

        return new MostrarEnemigoDto(
                enemigo.getId(),
                enemigo.getNombre(),
                enemigo.getDescripcion(),
                enemigo.getVidaMax(),
                enemigo.getVidaActual(),
                enemigo.getNivel(),
                enemigo.estaVivo(),
                atributos
        );
    }

    /**
     * Convierte un DTO de creación a entidad Enemigo
     * La vida actual se inicializa igual a la vida máxima
     */
    public Enemigo toEntity(CrearEnemigoDto dto) {
        if (dto == null) return null;

        Enemigo enemigo = new Enemigo();
        enemigo.setNombre(dto.getNombre());
        enemigo.setDescripcion(dto.getDescripcion());
        enemigo.setVidaMax(dto.getVidaMax());
        enemigo.setVidaActual(dto.getVidaMax()); 
        enemigo.setNivel(dto.getNivel());
        
        // Mapear atributos si existen - REUTILIZANDO la entidad Atributo
        if (dto.getAtributos() != null && !dto.getAtributos().isEmpty()) {
            List<Atributo> atributos = dto.getAtributos().stream()
                .map(aDto -> {
                    Atributo atributo = new Atributo();
                    atributo.setNombre(aDto.getNombre());
                    atributo.setValor(aDto.getValor());
                    atributo.setEnemigo(enemigo);
                    atributo.setPersonaje(null);  
                    return atributo;
                })
                .collect(Collectors.toList());
            enemigo.setAtributos(atributos);
        }
        
        return enemigo;
    }

    /**
     * Convierte una lista de entidades a lista de DTOs
     */
    public List<MostrarEnemigoDto> toDTOList(List<Enemigo> enemigos) {
        if (enemigos == null) {
            return new ArrayList<>();
        }
        return enemigos.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }
}