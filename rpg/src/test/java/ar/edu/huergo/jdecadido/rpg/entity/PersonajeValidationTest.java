package ar.edu.huergo.jdecadido.rpg.entity;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashMap;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

@DisplayName("Tests de Validación - Entidad Personaje")
class PersonajeValidationTest {

    private Validator validator;
    private HashMap<String, Integer> atributosValidos;
    private HashMap<String, String> inventarioValido;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();

        atributosValidos = new HashMap<>();
        atributosValidos.put("fuerza", 15);

        inventarioValido = new HashMap<>();
        inventarioValido.put("arma", "Espada de hierro");
    }

    @Test
    @DisplayName("Debería validar personaje correcto sin errores")
    void deberiaValidarPersonajeCorrectoSinErrores() {
        Personaje personaje = new Personaje();
        personaje.setNombre("Joaco");
        personaje.setNivel(5);
        personaje.setXp(50);
        personaje.setAtributos(atributosValidos);
        personaje.setInventario(inventarioValido);

        Set<ConstraintViolation<Personaje>> violaciones = validator.validate(personaje);
        assertTrue(violaciones.isEmpty(), "No debería haber violaciones de validación para un personaje válido");
    }

    @Test
    @DisplayName("Debería fallar validación con nombre null")
    void deberiaFallarValidacionConNombreNull() {
        Personaje personaje = new Personaje();
        personaje.setNombre(null);
        personaje.setNivel(5);
        personaje.setXp(50);
        personaje.setAtributos(atributosValidos);
        personaje.setInventario(inventarioValido);

        Set<ConstraintViolation<Personaje>> violaciones = validator.validate(personaje);
        assertFalse(violaciones.isEmpty());
        assertTrue(violaciones.stream().anyMatch(v -> v.getPropertyPath().toString().equals("nombre")));
    }

    @Test
    @DisplayName("Debería fallar validación con nombre vacío")
    void deberiaFallarValidacionConNombreVacio() {
        Personaje personaje = new Personaje();
        personaje.setNombre("");
        personaje.setNivel(5);
        personaje.setXp(50);
        personaje.setAtributos(atributosValidos);
        personaje.setInventario(inventarioValido);

        Set<ConstraintViolation<Personaje>> violaciones = validator.validate(personaje);
        assertFalse(violaciones.isEmpty());
        assertTrue(violaciones.stream().anyMatch(v -> v.getPropertyPath().toString().equals("nombre")));
    }

    @ParameterizedTest
    @ValueSource(strings = {"A"})
    @DisplayName("Debería fallar validación con nombres muy cortos")
    void deberiaFallarValidacionConNombresMuyCortos(String nombreCorto) {
        Personaje personaje = new Personaje();
        personaje.setNombre(nombreCorto);
        personaje.setNivel(5);
        personaje.setXp(50);
        personaje.setAtributos(atributosValidos);
        personaje.setInventario(inventarioValido);

        Set<ConstraintViolation<Personaje>> violaciones = validator.validate(personaje);
        assertFalse(violaciones.isEmpty());
        assertTrue(violaciones.stream().anyMatch(v -> v.getPropertyPath().toString().equals("nombre")));
    }

    @Test
    @DisplayName("Debería fallar validación con nivel negativo")
    void deberiaFallarValidacionConNivelNegativo() {
        Personaje personaje = new Personaje();
        personaje.setNombre("Joaco");
        personaje.setNivel(-1);
        personaje.setXp(50);
        personaje.setAtributos(atributosValidos);
        personaje.setInventario(inventarioValido);

        Set<ConstraintViolation<Personaje>> violaciones = validator.validate(personaje);
        assertFalse(violaciones.isEmpty());
        assertTrue(violaciones.stream().anyMatch(v -> v.getPropertyPath().toString().equals("nivel")));
    }

    @ParameterizedTest
    @ValueSource(ints = {-1, 101})
    @DisplayName("Debería fallar validación con xp fuera de rango")
    void deberiaFallarValidacionConXpFueraDeRango(int xpInvalido) {
        Personaje personaje = new Personaje();
        personaje.setNombre("Joaco");
        personaje.setNivel(5);
        personaje.setXp(xpInvalido);
        personaje.setAtributos(atributosValidos);
        personaje.setInventario(inventarioValido);

        Set<ConstraintViolation<Personaje>> violaciones = validator.validate(personaje);
        assertFalse(violaciones.isEmpty());
        assertTrue(violaciones.stream().anyMatch(v -> v.getPropertyPath().toString().equals("xp")));
    }

    @Test
    @DisplayName("Debería fallar validación con atributos null o vacíos")
    void deberiaFallarValidacionConAtributosInvalidos() {
        Personaje personajeNull = new Personaje();
        personajeNull.setNombre("Joaco");
        personajeNull.setNivel(5);
        personajeNull.setXp(50);
        personajeNull.setAtributos(null);
        personajeNull.setInventario(inventarioValido);

        Set<ConstraintViolation<Personaje>> violacionesNull = validator.validate(personajeNull);
        assertFalse(violacionesNull.isEmpty());
        assertTrue(violacionesNull.stream().anyMatch(v -> v.getPropertyPath().toString().equals("atributos")));

        Personaje personajeVacio = new Personaje();
        personajeVacio.setNombre("Joaco");
        personajeVacio.setNivel(5);
        personajeVacio.setXp(50);
        personajeVacio.setAtributos(new HashMap<>());
        personajeVacio.setInventario(inventarioValido);

        Set<ConstraintViolation<Personaje>> violacionesVacio = validator.validate(personajeVacio);
        assertFalse(violacionesVacio.isEmpty());
        assertTrue(violacionesVacio.stream().anyMatch(v -> v.getPropertyPath().toString().equals("atributos")));
    }

    @Test
    @DisplayName("Debería fallar validación con inventario null")
    void deberiaFallarValidacionConInventarioNull() {
        Personaje personaje = new Personaje();
        personaje.setNombre("Joaco");
        personaje.setNivel(5);
        personaje.setXp(50);
        personaje.setAtributos(atributosValidos);
        personaje.setInventario(null);

        Set<ConstraintViolation<Personaje>> violaciones = validator.validate(personaje);
        assertFalse(violaciones.isEmpty());
        assertTrue(violaciones.stream().anyMatch(v -> v.getPropertyPath().toString().equals("inventario")));
    }

    @Test
    @DisplayName("Debería validar múltiples errores simultáneamente")
    void deberiaValidarMultiplesErroresSimultaneamente() {
        Personaje personaje = new Personaje();
        personaje.setNombre(""); // Nombre inválido
        personaje.setNivel(-1); // Nivel inválido
        personaje.setXp(200); // XP inválido
        personaje.setAtributos(null);
        personaje.setInventario(null);

        Set<ConstraintViolation<Personaje>> violaciones = validator.validate(personaje);
        assertFalse(violaciones.isEmpty());
        assertTrue(violaciones.size() >= 5);

        List<String> propiedadesConError =
                violaciones.stream().map(v -> v.getPropertyPath().toString()).toList();

        assertTrue(propiedadesConError.contains("nombre"));
        assertTrue(propiedadesConError.contains("nivel"));
        assertTrue(propiedadesConError.contains("xp"));
        assertTrue(propiedadesConError.contains("atributos"));
        assertTrue(propiedadesConError.contains("inventario"));
    }
}
