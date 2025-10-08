package ar.edu.huergo.jdecadido.rpg.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ar.edu.huergo.jdecadido.rpg.entity.ItemInventario;

@Repository
public interface ItemInventarioRepository extends JpaRepository<ItemInventario, Long> {
}