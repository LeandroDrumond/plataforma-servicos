package br.edu.infnet.LeandroDrumondApi.controllers;

import br.edu.infnet.LeandroDrumondApi.model.domain.OrdemServico;
import br.edu.infnet.LeandroDrumondApi.model.domain.enums.StatusOrdem;
import br.edu.infnet.LeandroDrumondApi.model.domain.service.OrdemServicoService;
import br.edu.infnet.LeandroDrumondApi.model.dto.CancelarOrdemDTO;
import br.edu.infnet.LeandroDrumondApi.model.dto.CriarOrdemServicoDTO;
import br.edu.infnet.LeandroDrumondApi.model.dto.DefinirValorEAceitarDTO;
import jakarta.validation.Valid;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ordens")
public class OrdemServicoController {

    private final OrdemServicoService service;

    public OrdemServicoController(OrdemServicoService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<OrdemServico> criar(@Valid @RequestBody CriarOrdemServicoDTO dto) {
        OrdemServico os = service.criar(dto.getClienteId(), dto.getCategoria(), dto.getDescricao());
        return ResponseEntity.status(HttpStatus.CREATED).body(os);
    }

    @PostMapping("/{id}/aceitar")
    public ResponseEntity<OrdemServico> aceitar(@PathVariable Integer id,
                                                @Valid @RequestBody DefinirValorEAceitarDTO dto) {
        OrdemServico os = service.definirValorEAceitar(id, dto.getPrestadorId(), dto.getValor());
        return ResponseEntity.ok(os);
    }

    @PostMapping("/{id}/iniciar")
    public ResponseEntity<OrdemServico> iniciar(@PathVariable Integer id) {
        OrdemServico os = service.iniciar(id);
        return ResponseEntity.ok(os);
    }

    @PostMapping("/{id}/concluir")
    public ResponseEntity<OrdemServico> concluir(@PathVariable Integer id) {
        OrdemServico os = service.concluir(id);
        return ResponseEntity.ok(os);
    }

    @PostMapping("/{id}/cancelar")
    public ResponseEntity<OrdemServico> cancelar(@PathVariable Integer id,
                                                 @Valid @RequestBody CancelarOrdemDTO dto) {
        OrdemServico ordemServico = service.cancelar(id, dto.getMotivo());

        return ResponseEntity.ok(ordemServico);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrdemServico> obter(@PathVariable Integer id) {
        return ResponseEntity.ok(service.obterOrdem(id));
    }

    @GetMapping("/cliente/{clienteId}")
    public ResponseEntity<List<OrdemServico>> listarPorCliente(@PathVariable Integer clienteId) {

        List<OrdemServico> lista = service.listarPorCliente(clienteId);

        if (lista == null || lista.isEmpty()) return ResponseEntity.noContent().build();

        return ResponseEntity.ok(lista);
    }

    @GetMapping("/prestador/{prestadorId}")
    public ResponseEntity<List<OrdemServico>> listarPorPrestador(@PathVariable Integer prestadorId) {

        List<OrdemServico> lista = service.listarPorPrestador(prestadorId);

        if (lista == null || lista.isEmpty()) return ResponseEntity.noContent().build();

        return ResponseEntity.ok(lista);
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<OrdemServico>> listarPorStatus(@PathVariable StatusOrdem status) {

        List<OrdemServico> lista = service.listarPorStatus(status);

        if (lista == null || lista.isEmpty()) return ResponseEntity.noContent().build();

        return ResponseEntity.ok(lista);
    }
}
