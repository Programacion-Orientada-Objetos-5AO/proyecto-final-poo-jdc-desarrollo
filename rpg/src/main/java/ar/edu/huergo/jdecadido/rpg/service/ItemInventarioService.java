package ar.edu.huergo.jdecadido.rpg.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.edu.huergo.jdecadido.rpg.entity.*;
import ar.edu.huergo.jdecadido.rpg.repository.*;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

@Service
public class ItemInventarioService {

    @Autowired
    private PersonajeRepository personajeRepository;

    @Autowired
    private ItemRepository itemRepository;

    /**
     * Agrega un item al inventario de un personaje
     */
    @Transactional
    public ItemInventario agregarItem(Long personajeId, Long itemId, int cantidad) {
        Personaje personaje = personajeRepository.findById(personajeId)
                .orElseThrow(() -> new EntityNotFoundException("Personaje no encontrado con id: " + personajeId));

        Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> new EntityNotFoundException("Item no encontrado con id: " + itemId));

        
        ItemInventario invExistente = personaje.getInventario().stream()
                .filter(inv -> inv.getItem().getId().equals(itemId))
                .findFirst()
                .orElse(null);

        if (invExistente != null) {
            
            invExistente.setCantidad(invExistente.getCantidad() + cantidad);
            personajeRepository.save(personaje);
            return invExistente;
        } else {
            
            ItemInventario inv = new ItemInventario();
            inv.setPersonaje(personaje);
            inv.setItem(item);
            inv.setCantidad(cantidad);
            inv.setEquipado(false);

            personaje.getInventario().add(inv);
            personajeRepository.save(personaje);
            return inv;
        }
    }

    /**
     * Quita un item del inventario de un personaje
     */
    @Transactional
    public void quitarItem(Long personajeId, Long itemId) {
        Personaje personaje = personajeRepository.findById(personajeId)
                .orElseThrow(() -> new EntityNotFoundException("Personaje no encontrado con id: " + personajeId));

        boolean removed = personaje.getInventario().removeIf(inv -> inv.getItem().getId().equals(itemId));
        
        if (!removed) {
            throw new EntityNotFoundException("Item no encontrado en el inventario");
        }
        
        personajeRepository.save(personaje);
    }

    /**
     * Busca un item específico en el inventario de un personaje
     */
    public ItemInventario buscarItem(Long personajeId, Long itemId) {
        Personaje personaje = personajeRepository.findById(personajeId)
                .orElseThrow(() -> new EntityNotFoundException("Personaje no encontrado con id: " + personajeId));

        return personaje.getInventario().stream()
                .filter(inv -> inv.getItem().getId().equals(itemId))
                .findFirst()
                .orElseThrow(() -> new EntityNotFoundException("Item no encontrado en el inventario"));
    }

    /**
     * Equipa o desequipa un item del inventario
     */
    @Transactional
    public ItemInventario equiparItem(Long personajeId, Long itemId) {
        Personaje personaje = personajeRepository.findById(personajeId)
                .orElseThrow(() -> new EntityNotFoundException("Personaje no encontrado con id: " + personajeId));

        ItemInventario inv = personaje.getInventario().stream()
                .filter(i -> i.getItem().getId().equals(itemId))
                .findFirst()
                .orElseThrow(() -> new EntityNotFoundException("Item no encontrado en el inventario"));

        
        inv.setEquipado(!inv.isEquipado());
        personajeRepository.save(personaje);
        
        return inv;
    }

    /**
     * Borra todo el inventario de un personaje
     */
    @Transactional
    public void borrarInventario(Long personajeId) {
        Personaje personaje = personajeRepository.findById(personajeId)
                .orElseThrow(() -> new EntityNotFoundException("Personaje no encontrado con id: " + personajeId));

        personaje.getInventario().clear();
        personajeRepository.save(personaje);
    }

    /**
     * Lista todo el inventario de un personaje
     */
    public List<ItemInventario> listarInventario(Long personajeId) {
        Personaje personaje = personajeRepository.findById(personajeId)
                .orElseThrow(() -> new EntityNotFoundException("Personaje no encontrado con id: " + personajeId));

        return personaje.getInventario();
    }
}