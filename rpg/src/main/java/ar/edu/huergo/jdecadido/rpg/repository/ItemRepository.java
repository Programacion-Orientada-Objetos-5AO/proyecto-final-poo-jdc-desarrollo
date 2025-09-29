package ar.edu.huergo.jdecadido.rpg.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ar.edu.huergo.jdecadido.rpg.entity.Item;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {
    List<Item> findByNombreContainingIgnoreCase(String nombre);
}
