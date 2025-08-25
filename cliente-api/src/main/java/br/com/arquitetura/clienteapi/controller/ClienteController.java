package br.com.arquitetura.clienteapi.controller;

import br.com.arquitetura.clienteapi.model.Cliente;
import br.com.arquitetura.clienteapi.repository.ClienteRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/clientes")
public class ClienteController {

    @Autowired
    private ClienteRepository clienteRepository;

    // Endpoint para LISTAR TODOS os clientes
    @GetMapping
    public List<Cliente> listarTodos() {
        return clienteRepository.findAll();
    }

    // Endpoint para BUSCAR um cliente por ID
    @GetMapping("/{id}")
    public ResponseEntity<Cliente> buscarPorId(@PathVariable Long id) {
        Optional<Cliente> cliente = clienteRepository.findById(id);
        return cliente.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Endpoint para CRIAR um novo cliente
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Cliente criar(@Valid @RequestBody Cliente cliente) {
        return clienteRepository.save(cliente);
    }

    // Endpoint para ATUALIZAR um cliente existente
    @PutMapping("/{id}")
    public ResponseEntity<Cliente> atualizar(@PathVariable Long id, @Valid @RequestBody Cliente clienteDetalhes) {
        return clienteRepository.findById(id)
                .map(cliente -> {
                    cliente.setNome(clienteDetalhes.getNome());
                    cliente.setEmail(clienteDetalhes.getEmail());
                    cliente.setTelefone(clienteDetalhes.getTelefone());
                    Cliente clienteAtualizado = clienteRepository.save(cliente);
                    return ResponseEntity.ok(clienteAtualizado);
                }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Endpoint para DELETAR um cliente
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        return clienteRepository.findById(id)
                .map(cliente -> {
                    clienteRepository.delete(cliente);
                    return ResponseEntity.noContent().<Void>build();
                }).orElseGet(() -> ResponseEntity.notFound().build());
    }
}