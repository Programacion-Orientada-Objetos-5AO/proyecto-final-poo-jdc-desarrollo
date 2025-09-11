package ar.edu.huergo.jdecadido.rpg.repository;

import static org.junit.jupiter.api.Assertions.*;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import ar.edu.huergo.jdecadido.rpg.entity.Personaje;

@DataJpaTest
@DisplayName("Tests de Integración - PersonajeRepository")
class PersonajeRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private PersonajeRepository personajeRepository;

    private Personaje personaje1;
    private Personaje personaje2;
    private Personaje personaje3;

    @BeforeEach
    void setUp() {
        HashMap<String, Integer> atributos = new HashMap<>();
        atributos.put("fuerza", 10);

        HashMap<String, String> inventario = new HashMap<>();
        inventario.put("arma", "Espada");

        personaje1 = new Personaje(null, "Joaco", 5, 50, atributos, inventario);
        personaje2 = new Personaje(null, "Dantu", 3, 20, atributos, inventario);
        personaje3 = new Personaje(null, "Capu Joaco", 7, 80, atributos, inventario);

        personaje1 = entityManager.persistAndFlush(personaje1);
        personaje2 = entityManager.persistAndFlush(personaje2);
        personaje3 = entityManager.persistAndFlush(personaje3);

        entityManager.clear();
    }

    @Test
    @DisplayName("Debería encontrar personajes por nombre conteniendo texto (case insensitive)")
    void deberiaEncontrarPersonajesPorNombreContaining() {
        List<Personaje> encontrados = personajeRepository.findByNombreContainingIgnoreCase("joaco");
        assertNotNull(encontrados);
        assertEquals(2, encontrados.size());

        List<String> nombres = encontrados.stream().map(Personaje::getNombre).toList();
        assertTrue(nombres.contains("Joaco"));
        assertTrue(nombres.contains("Capu Joaco"));
    }

    @Test
    @DisplayName("Debería encontrar personajes con búsqueda case insensitive")
    void deberiaEncontrarPersonajesCaseInsensitive() {
        List<Personaje> minuscula = personajeRepository.findByNombreContainingIgnoreCase("JOACO");
        List<Personaje> mayuscula = personajeRepository.findByNombreContainingIgnoreCase("joaco");
        List<Personaje> mixto = personajeRepository.findByNombreContainingIgnoreCase("JoAcO");

        assertEquals(2, minuscula.size());
        assertEquals(2, mayuscula.size());
        assertEquals(2, mixto.size());
    }

    @Test
    @DisplayName("Debería retornar lista vacía cuando no encuentra coincidencias")
    void deberiaRetornarListaVaciaSinCoincidencias() {
        List<Personaje> encontrados = personajeRepository.findByNombreContainingIgnoreCase("inexistente");
        assertNotNull(encontrados);
        assertTrue(encontrados.isEmpty());
    }

    @Test
    @DisplayName("Debería encontrar personajes con búsqueda parcial")
    void deberiaEncontrarPersonajesConBusquedaParcial() {
        List<Personaje> resultado = personajeRepository.findByNombreContainingIgnoreCase("capu");
        assertEquals(1, resultado.size());
        assertEquals("Capu Joaco", resultado.get(0).getNombre());
    }

    @Test
    @DisplayName("Debería guardar y recuperar personaje correctamente")
    void deberiaGuardarYRecuperarPersonaje() {
        HashMap<String, Integer> attrs = new HashMap<>();
        attrs.put("inteligencia", 15);
        HashMap<String, String> invent = new HashMap<>();
        invent.put("varita", "Mágica");

        Personaje nuevo = new Personaje(null, "Facu", 4, 30, attrs, invent);
        Personaje guardado = personajeRepository.save(nuevo);
        entityManager.flush();
        entityManager.clear();

        Optional<Personaje> recuperado = personajeRepository.findById(guardado.getId());
        assertTrue(recuperado.isPresent());
        assertEquals("Facu", recuperado.get().getNombre());
    }

    @Test
    @DisplayName("Debería actualizar personaje existente")
    void deberiaActualizarPersonajeExistente() {
        personaje1.setNombre("Joaco Updated");
        Personaje actualizado = personajeRepository.save(personaje1);
        entityManager.flush();
        entityManager.clear();

        Optional<Personaje> verificacion = personajeRepository.findById(personaje1.getId());
        assertTrue(verificacion.isPresent());
        assertEquals("Joaco Updated", verificacion.get().getNombre());
    }

    @Test
    @DisplayName("Debería eliminar personaje correctamente")
    void deberiaEliminarPersonaje() {
        Long id = personaje2.getId();
        assertTrue(personajeRepository.existsById(id));

        personajeRepository.deleteById(id);
        entityManager.flush();

        assertFalse(personajeRepository.existsById(id));
        Optional<Personaje> eliminado = personajeRepository.findById(id);
        assertFalse(eliminado.isPresent());
    }

    @Test
    @DisplayName("Debería encontrar todos los personajes")
    void deberiaEncontrarTodosLosPersonajes() {
        List<Personaje> todos = personajeRepository.findAll();
        assertNotNull(todos);
        assertEquals(3, todos.size());

        List<String> nombres = todos.stream().map(Personaje::getNombre).toList();
        assertTrue(nombres.contains("Joaco"));
        assertTrue(nombres.contains("Dantu"));
        assertTrue(nombres.contains("Capu Joaco"));
    }

    @Test
    @DisplayName("Debería contar personajes correctamente")
    void deberiaContarPersonajes() {
        long cantidad = personajeRepository.count();
        assertEquals(3, cantidad);

        Personaje extra = new Personaje();
        HashMap<String, Integer> attrs = new HashMap<>();
        attrs.put("suerte", 12);
        extra.setNombre("Extra");
        extra.setNivel(1);
        extra.setXp(10);
        extra.setAtributos(attrs);
        extra.setInventario(new HashMap<>());

        entityManager.persistAndFlush(extra);
        assertEquals(4, personajeRepository.count());
    }

    @Test
    @DisplayName("Debería validar restricciones de la entidad")
    void deberiaValidarRestricciones() {
        Personaje invalido = new Personaje();
        invalido.setNombre(""); // @NotBlank
        invalido.setNivel(1);
        invalido.setXp(10);
        invalido.setAtributos(new HashMap<>());

        assertThrows(Exception.class, () -> entityManager.persistAndFlush(invalido));
    }

    @Test
    @DisplayName("Debería manejar nombres con espacios en la búsqueda")
    void deberiaManejarNombresConEspacios() {
        List<Personaje> resultado = personajeRepository.findByNombreContainingIgnoreCase("joaco");
        assertEquals(2, resultado.size());
        List<String> nombres = resultado.stream().map(Personaje::getNombre).toList();
        assertTrue(nombres.contains("Joaco"));
        assertTrue(nombres.contains("Capu Joaco"));
    }
}
