package br.edu.infnet.LeandroDrumondApi.controllers;

import java.util.List;

import br.edu.infnet.LeandroDrumondApi.model.domain.PrestadorServico;
import br.edu.infnet.LeandroDrumondApi.model.domain.service.PrestadorServicoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/prestadores")

public class PrestadorServicoController {

    private final PrestadorServicoService prestadorServicoService;

    public PrestadorServicoController(PrestadorServicoService prestadorService) {
        this.prestadorServicoService = prestadorService;
    }

    @PostMapping
    public ResponseEntity<PrestadorServico> incluir(@Valid @RequestBody PrestadorServico prestador) {

        PrestadorServico prestadorIncluido = prestadorServicoService.incluir(prestador);

        return ResponseEntity.status(HttpStatus.CREATED).body(prestadorIncluido);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<PrestadorServico> alterar(@PathVariable Integer id,
                                                    @RequestBody PrestadorServico prestador) {

        PrestadorServico prestadorAlterado = prestadorServicoService.alterar(id, prestador);

        return ResponseEntity.ok(prestadorAlterado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Integer id) {

        prestadorServicoService.excluir(id);

        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<PrestadorServico>> obterLista() {

        List<PrestadorServico> lista = prestadorServicoService.obterLista();

        if (lista == null || lista.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(lista);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PrestadorServico> obterPorId(@PathVariable Integer id) {

        PrestadorServico prestador = prestadorServicoService.obterPorId(id);

        return ResponseEntity.ok(prestador);
    }
}
