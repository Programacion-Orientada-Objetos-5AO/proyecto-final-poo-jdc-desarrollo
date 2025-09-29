package ar.edu.huergo.jdecadido.rpg.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.edu.huergo.jdecadido.rpg.entity.Item;
import ar.edu.huergo.jdecadido.rpg.repository.ItemRepository;
import jakarta.persistence.EntityNotFoundException;

@Service
public class ItemService {

    @Autowired
    private ItemRepository itemRepository;

    public List<Item> obtenerTodosLosItems() {
        return itemRepository.findAll();
    }

    public Item obtenerItemPorId(Long id) throws EntityNotFoundException {
        return itemRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Item no encontrado"));
    }

    public Item crearItem(Item item) {
        return itemRepository.save(item);
    }

    public Item actualizarItem(Long id, Item item) throws EntityNotFoundException {
        Item itemExistente = obtenerItemPorId(id);

        itemExistente.setNombre(item.getNombre());
        itemExistente.setTipo(item.getTipo());
        itemExistente.setDescripcion(item.getDescripcion());
        itemExistente.setPoder(item.getPoder());

        return itemRepository.save(itemExistente);
    }

    public void eliminarItem(Long id) throws EntityNotFoundException {
        Item item = obtenerItemPorId(id);
        itemRepository.delete(item);
    }
}
