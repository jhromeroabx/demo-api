package com.ejemplo.demo_api.cliente;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/clientes")
public class ClienteController {
  private final ClienteRepository repository;
  public ClienteController(ClienteRepository repository) {
      this.repository = repository;
  }

  @GetMapping
  public List<Cliente> listar() {
      return repository.findAll();
  }

  @GetMapping("/{id}")
  public ResponseEntity<Cliente> obtenerPorId(@PathVariable Long id) {
      return repository.findById(id)
              .map(ResponseEntity::ok)
              .orElse(ResponseEntity.notFound().build());
  }

  @PostMapping
  public Cliente crear(@RequestBody Cliente cliente) {
      return repository.save(cliente);
  }

  @PutMapping("/{id}")
  public ResponseEntity<Cliente> actualizar(@PathVariable Long id,
                                            @RequestBody Cliente datos) {
      return repository.findById(id)
              .map(c -> {
                  c.setNombre(datos.getNombre());
                  c.setEmail(datos.getEmail());
                  return ResponseEntity.ok(repository.save(c));
              })
              .orElse(ResponseEntity.notFound().build());
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> eliminar(@PathVariable Long id) {
      if (!repository.existsById(id)) {
          return ResponseEntity.notFound().build();
      }
      repository.deleteById(id);
      return ResponseEntity.noContent().build();
  }
}
