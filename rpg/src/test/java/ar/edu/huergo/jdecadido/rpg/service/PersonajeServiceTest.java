package ar.edu.huergo.jdecadido.rpg.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import ar.edu.huergo.jdecadido.rpg.entity.Personaje;
import ar.edu.huergo.jdecadido.rpg.repository.PersonajeRepository;
import jakarta.persistence.EntityNotFoundException;

@ExtendWith(MockitoExtension.class)
@DisplayName("Tests de Unidad - PersonajeService")
class PersonajeServiceTest {

    @Mock
    private PersonajeRepository personajeRepository;

    @InjectMocks
    private PersonajeService personajeService;

    private Personaje personajeEjemplo;

    @BeforeEach
    void setUp() {
        // Preparación de datos de prueba
        HashMap<String, Integer> atributos = new HashMap<>();
        atributos.put("fuerza", 10);

        HashMap<String, String> inventario = new HashMap<>();
        inventario.put("arma", "espada");

        personajeEjemplo = new Personaje();
        personajeEjemplo.setId(1L);
        personajeEjemplo.setNombre("Joaco");
        personajeEjemplo.setNivel(5);
        personajeEjemplo.setXp(50);
        personajeEjemplo.setAtributos(atributos);
        personajeEjemplo.setInventario(inventario);
    }

    @Test
    @DisplayName("Debería obtener todos los personajes correctamente")
    void deberiaObtenerTodosLosPersonajes() {
        List<Personaje> personajesEsperados = Arrays.asList(personajeEjemplo);
        when(personajeRepository.findAll()).thenReturn(personajesEsperados);

        List<Personaje> resultado = personajeService.obtenerTodosLosPersonajes();

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals(personajeEjemplo.getNombre(), resultado.get(0).getNombre());
        verify(personajeRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Debería obtener un personaje por ID cuando existe")
    void deberiaObtenerPersonajePorIdCuandoExiste() {
        Long id = 1L;
        when(personajeRepository.findById(id)).thenReturn(Optional.of(personajeEjemplo));

        Personaje resultado = personajeService.obtenerPersonajePorId(id);

        assertNotNull(resultado);
        assertEquals(personajeEjemplo.getId(), resultado.getId());
        assertEquals(personajeEjemplo.getNombre(), resultado.getNombre());
        verify(personajeRepository, times(1)).findById(id);
    }

    @Test
    @DisplayName("Debería lanzar EntityNotFoundException cuando el personaje no existe")
    void deberiaLanzarExcepcionCuandoPersonajeNoExiste() {
        Long idInexistente = 999L;
        when(personajeRepository.findById(idInexistente)).thenReturn(Optional.empty());

        EntityNotFoundException excepcion = assertThrows(
                EntityNotFoundException.class,
                () -> personajeService.obtenerPersonajePorId(idInexistente));

        assertEquals("Personaje  no encontrado", excepcion.getMessage());
        verify(personajeRepository, times(1)).findById(idInexistente);
    }

    @Test
    @DisplayName("Debería crear un personaje correctamente")
    void deberiaCrearPersonajeCorrectamente() {
        when(personajeRepository.save(any(Personaje.class))).thenReturn(personajeEjemplo);

        Personaje resultado = personajeService.crearPersonaje(personajeEjemplo);

        assertNotNull(resultado);
        assertEquals(personajeEjemplo.getNombre(), resultado.getNombre());
        verify(personajeRepository, times(1)).save(personajeEjemplo);
    }

    @Test
    @DisplayName("Debería actualizar un personaje existente correctamente")
    void deberiaActualizarPersonajeExistente() {
        Long id = 1L;
        Personaje actualizado = new Personaje();
        actualizado.setNombre("Joaco Updated");
        actualizado.setNivel(10);
        actualizado.setXp(80);
        actualizado.setAtributos(new HashMap<>(Map.of("fuerza", 20)));
        actualizado.setInventario(new HashMap<>(Map.of("arma", "hacha")));

        when(personajeRepository.findById(id)).thenReturn(Optional.of(personajeEjemplo));
        when(personajeRepository.save(any(Personaje.class))).thenReturn(personajeEjemplo);

        Personaje resultado = personajeService.actualizarPersonaje(id, actualizado);

        assertNotNull(resultado);
        verify(personajeRepository, times(1)).findById(id);
        verify(personajeRepository, times(1)).save(personajeEjemplo);

        // Verificar que los campos se actualizaron
        assertEquals(actualizado.getNombre(), personajeEjemplo.getNombre());
        assertEquals(actualizado.getNivel(), personajeEjemplo.getNivel());
        assertEquals(actualizado.getXp(), personajeEjemplo.getXp());
        assertEquals(actualizado.getAtributos(), personajeEjemplo.getAtributos());
        assertEquals(actualizado.getInventario(), personajeEjemplo.getInventario());
    }

    @Test
    @DisplayName("Debería eliminar un personaje correctamente")
    void deberiaEliminarPersonaje() {
        Long id = 1L;
        when(personajeRepository.findById(id)).thenReturn(Optional.of(personajeEjemplo));

        personajeService.eliminarPersonaje(id);

        verify(personajeRepository, times(1)).delete(personajeEjemplo);
        verify(personajeRepository, times(1)).findById(id);
    }
}
