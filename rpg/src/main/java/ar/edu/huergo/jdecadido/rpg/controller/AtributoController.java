package ar.edu.huergo.jdecadido.rpg.controller;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import ar.edu.huergo.jdecadido.rpg.entity.Atributo;
import ar.edu.huergo.jdecadido.rpg.service.AtributoService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/atributos")
public class AtributoController {

    @Autowired
    private AtributoService atributoService;

    @GetMapping
    public ResponseEntity<List<Atributo>> obtenerTodosLosAtributos() {
        List<Atributo> atributos = atributoService.obtenerTodos();
        return ResponseEntity.ok(atributos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Atributo> obtenerAtributoPorId(@PathVariable Long id) {
        Atributo atributo = atributoService.obtenerPorId(id);
        return ResponseEntity.ok(atributo);
    }

    @PostMapping
    public ResponseEntity<Atributo> crearAtributo(@Valid @RequestBody Atributo atributo) {
        Atributo atributoCreado = atributoService.crearAtributo(atributo);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(atributoCreado.getId())
                .toUri();
        return ResponseEntity.created(location).body(atributoCreado);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Atributo> actualizarAtributo(
            @PathVariable Long id,
            @Valid @RequestBody Atributo atributo) {
        Atributo atributoActualizado = atributoService.actualizarAtributo(id, atributo);
        return ResponseEntity.ok(atributoActualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarAtributo(@PathVariable Long id) {
        atributoService.eliminarAtributo(id);
        return ResponseEntity.noContent().build();
    }
}
