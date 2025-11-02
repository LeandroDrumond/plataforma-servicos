package br.edu.infnet.LeandroDrumondApi.controllers;

import br.edu.infnet.LeandroDrumondApi.model.domain.Cliente;
import br.edu.infnet.LeandroDrumondApi.model.domain.service.ClienteService;
import jakarta.validation.Valid;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/clientes")
public class ClienteController {

    private final ClienteService clienteService;
    public ClienteController(ClienteService clienteService) {

        this.clienteService = clienteService;
    }

    @PostMapping
    public ResponseEntity<Cliente> incluir(@Valid @RequestBody Cliente cliente) {
        Cliente salvo = clienteService.incluir(cliente);
        return ResponseEntity.status(HttpStatus.CREATED).body(salvo);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Cliente> alterar(@PathVariable Integer id, @Valid @RequestBody Cliente cliente) {
        Cliente alterado = clienteService.alterar(id, cliente);
        return ResponseEntity.ok(alterado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Integer id) {
        clienteService.excluir(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<Cliente>> obterLista() {
        List<Cliente> lista = clienteService.obterLista();
        if (lista == null || lista.isEmpty()) return ResponseEntity.noContent().build();
        return ResponseEntity.ok(lista);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Cliente> obterPorId(@PathVariable Integer id) {
        Cliente cliente = clienteService.obterPorId(id);
        return ResponseEntity.ok(cliente);
    }

    @GetMapping("/buscar/cpf/{cpf}")
    public ResponseEntity<Cliente> buscarPorCpf(@PathVariable String cpf) {

        Cliente cliente = clienteService.buscarPorCpf(cpf);

        return ResponseEntity.ok(cliente);
    }

    @GetMapping("/buscar/nome")
    public ResponseEntity<List<Cliente>> buscarPorNome(@RequestParam("nome") String nome) {

        List<Cliente> lista = clienteService.buscarPorNome(nome);

        if (lista == null || lista.isEmpty()) return ResponseEntity.noContent().build();

        return ResponseEntity.ok(lista);
    }

    @GetMapping("/buscar")
    public ResponseEntity<List<Cliente>> buscarPorNomeECpf(
            @RequestParam(value = "nome", required = false) String nome,
            @RequestParam(value = "cpf", required = false) String cpf) {

        if ((nome == null || nome.isBlank()) && (cpf == null || cpf.isBlank())) {
            return ResponseEntity.badRequest().build();
        }

        List<Cliente> lista = clienteService.buscarPorNomeECpf(nome, cpf);

        if (lista == null || lista.isEmpty()) return ResponseEntity.noContent().build();

        return ResponseEntity.ok(lista);
    }
}
