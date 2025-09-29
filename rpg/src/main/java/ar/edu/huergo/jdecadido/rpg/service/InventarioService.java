package ar.edu.huergo.jdecadido.rpg.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.edu.huergo.jdecadido.rpg.entity.*;
import ar.edu.huergo.jdecadido.rpg.repository.*;

import jakarta.persistence.EntityNotFoundException;

@Service
public class InventarioService {

    @Autowired
    private PersonajeRepository personajeRepository;

    @Autowired
    private ItemRepository itemRepository;


    public Inventario agregarItem(Long personajeId, Long itemId, int cantidad) {
        Personaje personaje = personajeRepository.findById(personajeId)
                .orElseThrow(() -> new EntityNotFoundException("Personaje no encontrado"));

        Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> new EntityNotFoundException("Item no encontrado"));

        Inventario inv = new Inventario();
        inv.setPersonaje(personaje);
        inv.setItem(item);
        inv.setCantidad(cantidad);
        inv.setEquipado(false);

        personaje.getInventario().add(inv);
        personajeRepository.save(personaje);
        return inv;
    }

    public void quitarItem(Long personajeId, Long itemId) {
        Personaje personaje = personajeRepository.findById(personajeId)
                .orElseThrow(() -> new EntityNotFoundException("Personaje no encontrado"));

        personaje.getInventario().removeIf(inv -> inv.getItem().getId().equals(itemId));
        personajeRepository.save(personaje);
    }

    public Inventario buscarItem(Long personajeId, Long itemId) {
        Personaje personaje = personajeRepository.findById(personajeId)
                .orElseThrow(() -> new EntityNotFoundException("Personaje no encontrado"));

        return personaje.getInventario().stream()
                .filter(inv -> inv.getItem().getId().equals(itemId))
                .findFirst()
                .orElseThrow(() -> new EntityNotFoundException("Item no encontrado en el inventario"));
    }

    public void borrarInventario(Long personajeId) {
        Personaje personaje = personajeRepository.findById(personajeId)
                .orElseThrow(() -> new EntityNotFoundException("Personaje no encontrado"));

        personaje.getInventario().clear();
        personajeRepository.save(personaje);
    }

    public List<Inventario> listarInventario(Long personajeId) {
        Personaje personaje = personajeRepository.findById(personajeId)
                .orElseThrow(() -> new EntityNotFoundException("Personaje no encontrado"));

        return personaje.getInventario();
    }
}
