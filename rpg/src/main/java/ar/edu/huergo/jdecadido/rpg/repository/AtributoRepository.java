package ar.edu.huergo.jdecadido.rpg.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ar.edu.huergo.jdecadido.rpg.entity.Atributo;

@Repository
public interface AtributoRepository extends JpaRepository<Atributo, Long> {
}