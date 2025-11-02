package br.edu.infnet.LeandroDrumondApi.controllers;

import java.util.List;

import br.edu.infnet.LeandroDrumondApi.model.domain.PrestadorServico;
import br.edu.infnet.LeandroDrumondApi.model.domain.service.PrestadorServicoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/prestadores")
@Tag(name = "Prestadores de Serviço", description = "Cadastro, consulta e atualização de prestadores (encanadores, eletricistas, montadores, etc.)")
public class PrestadorServicoController {

    private final PrestadorServicoService prestadorServicoService;

    public PrestadorServicoController(PrestadorServicoService prestadorService) {
        this.prestadorServicoService = prestadorService;
    }

    @Operation(summary = "Cadastrar prestador", description = "Registra um novo prestador com CPF, contato e especialidades.")
    @PostMapping
    public ResponseEntity<PrestadorServico> incluir(@Valid @RequestBody PrestadorServico prestador) {
        PrestadorServico prestadorIncluido = prestadorServicoService.incluir(prestador);
        return ResponseEntity.status(HttpStatus.CREATED).body(prestadorIncluido);
    }

    @Operation(summary = "Atualizar prestador (PATCH)", description = "Atualiza parcialmente os dados do prestador pelo ID.")
    @PatchMapping("/{id}")
    public ResponseEntity<PrestadorServico> alterar(@PathVariable Integer id,
                                                    @RequestBody PrestadorServico prestador) {
        PrestadorServico prestadorAlterado = prestadorServicoService.alterar(id, prestador);
        return ResponseEntity.ok(prestadorAlterado);
    }

    @Operation(summary = "Excluir prestador", description = "Remove o prestador do sistema pelo ID.")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Integer id) {
        prestadorServicoService.excluir(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Listar prestadores", description = "Retorna todos os prestadores cadastrados.")
    @GetMapping
    public ResponseEntity<List<PrestadorServico>> obterLista() {
        List<PrestadorServico> lista = prestadorServicoService.obterLista();
        if (lista == null || lista.isEmpty()) return ResponseEntity.noContent().build(); // 204
        return ResponseEntity.ok(lista);
    }

    @Operation(summary = "Buscar prestador por ID", description = "Retorna os dados de um prestador específico pelo ID.")
    @GetMapping("/{id}")
    public ResponseEntity<PrestadorServico> obterPorId(@PathVariable Integer id) {
        PrestadorServico prestador = prestadorServicoService.obterPorId(id);
        return ResponseEntity.ok(prestador);
    }

    @Operation(summary = "Buscar prestadores por nome (apenas ativos)", description = "Filtra prestadores ativos cujo nome contenha o valor informado (case-insensitive).")
    @GetMapping("/buscar/nome")
    public ResponseEntity<List<PrestadorServico>> buscarPorNome(@RequestParam("nome") String nome) {
        var lista = prestadorServicoService.buscarAtivosPorNome(nome);
        if (lista == null || lista.isEmpty()) return ResponseEntity.noContent().build();
        return ResponseEntity.ok(lista);
    }

    @Operation(summary = "Buscar prestadores por especialidade", description = "Filtra prestadores por especialidade (case-insensitive).")
    @GetMapping("/buscar/especialidade")
    public ResponseEntity<List<PrestadorServico>> buscarPorEspecialidade(@RequestParam("especialidade") String especialidade) {
        var lista = prestadorServicoService.buscarPorEspecialidade(especialidade);
        if (lista == null || lista.isEmpty()) return ResponseEntity.noContent().build();
        return ResponseEntity.ok(lista);
    }

    @Operation(summary = "Buscar prestador por CPF", description = "Retorna um prestador com base no CPF informado.")
    @GetMapping("/buscar/cpf/{cpf}")
    public ResponseEntity<PrestadorServico> buscarPorCpf(@PathVariable String cpf) {
        PrestadorServico prestador = prestadorServicoService.buscarPorCpf(cpf);
        return ResponseEntity.ok(prestador);
    }
}
