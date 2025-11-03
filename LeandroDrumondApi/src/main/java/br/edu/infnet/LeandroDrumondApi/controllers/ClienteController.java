package br.edu.infnet.LeandroDrumondApi.controllers;

import br.edu.infnet.LeandroDrumondApi.model.domain.Cliente;
import br.edu.infnet.LeandroDrumondApi.model.domain.service.ClienteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/clientes")
@Tag(name = "Clientes", description = "Gerenciamento de clientes e seus dados cadastrais")
public class ClienteController {

    private final ClienteService clienteService;
    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    @Operation(summary = "Cadastrar cliente", description = "Cria um novo cliente com nome, CPF e dados de contato.")
    @PostMapping
    public ResponseEntity<Cliente> incluir(@Valid @RequestBody Cliente cliente) {
        Cliente salvo = clienteService.incluir(cliente);
        return ResponseEntity.status(HttpStatus.CREATED).body(salvo);
    }

    @Operation(summary = "Atualizar cliente", description = "Atualiza os dados de um cliente existente pelo ID.")
    @PutMapping("/{id}")
    public ResponseEntity<Cliente> alterar(@PathVariable Integer id, @Valid @RequestBody Cliente cliente) {
        Cliente alterado = clienteService.alterar(id, cliente);
        return ResponseEntity.ok(alterado);
    }

    @Operation(summary = "Excluir cliente", description = "Remove um cliente do sistema pelo ID.")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Integer id) {
        clienteService.excluir(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Listar clientes", description = "Retorna a lista completa de clientes cadastrados.")
    @GetMapping
    public ResponseEntity<List<Cliente>> obterLista() {
        List<Cliente> lista = clienteService.obterLista();
        if (lista == null || lista.isEmpty()) return ResponseEntity.noContent().build();
        return ResponseEntity.ok(lista);
    }

    @Operation(summary = "Buscar cliente por ID", description = "Retorna os dados de um cliente específico pelo ID.")
    @GetMapping("/{id}")
    public ResponseEntity<Cliente> obterPorId(@PathVariable Integer id) {
        Cliente cliente = clienteService.obterPorId(id);
        return ResponseEntity.ok(cliente);
    }

    @Operation(summary = "Buscar cliente por CPF", description = "Retorna um cliente com base no CPF informado.")
    @GetMapping("/buscar/cpf/{cpf}")
    public ResponseEntity<Cliente> buscarPorCpf(@PathVariable String cpf) {
        Cliente cliente = clienteService.buscarPorCpf(cpf);
        return ResponseEntity.ok(cliente);
    }

    @Operation(summary = "Buscar clientes por nome", description = "Retorna clientes cujo nome contenha o valor informado (case-insensitive).")
    @GetMapping("/buscar/nome")
    public ResponseEntity<List<Cliente>> buscarPorNome(@RequestParam("nome") String nome) {
        List<Cliente> lista = clienteService.buscarPorNome(nome);
        if (lista == null || lista.isEmpty()) return ResponseEntity.noContent().build();
        return ResponseEntity.ok(lista);
    }

    @Operation(summary = "Buscar clientes por nome e/ou CPF", description = "Filtra clientes por nome e/ou CPF. Pelo menos um parâmetro deve ser informado.")
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
