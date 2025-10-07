package ar.edu.huergo.jdecadido.rpg.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.edu.huergo.jdecadido.rpg.entity.Atributo;
import ar.edu.huergo.jdecadido.rpg.repository.AtributoRepository;
import jakarta.persistence.EntityNotFoundException;

@Service
public class AtributoService {

    @Autowired
    private AtributoRepository atributoRepository;

    public List<Atributo> obtenerTodos() {
        return atributoRepository.findAll();
    }

    public Atributo obtenerPorId(Long id) throws EntityNotFoundException {
        return atributoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Atributo no encontrado con id: " + id));
    }

    public Atributo crearAtributo(Atributo atributo) {
        return atributoRepository.save(atributo);
    }

    public Atributo actualizarAtributo(Long id, Atributo atributo) throws EntityNotFoundException {
        Atributo atributoExistente = obtenerPorId(id);
        atributoExistente.setNombre(atributo.getNombre());
        atributoExistente.setValor(atributo.getValor());
        return atributoRepository.save(atributoExistente);
    }


    public void eliminarAtributo(Long id) throws EntityNotFoundException {
        Atributo atributo = obtenerPorId(id);
        atributoRepository.delete(atributo);
    }
}
