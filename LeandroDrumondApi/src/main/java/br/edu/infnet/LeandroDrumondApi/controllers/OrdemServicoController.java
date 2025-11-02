package br.edu.infnet.LeandroDrumondApi.controllers;

import br.edu.infnet.LeandroDrumondApi.model.domain.OrdemServico;
import br.edu.infnet.LeandroDrumondApi.model.domain.enums.StatusOrdem;
import br.edu.infnet.LeandroDrumondApi.model.domain.service.OrdemServicoService;
import br.edu.infnet.LeandroDrumondApi.model.dto.CancelarOrdemDTO;
import br.edu.infnet.LeandroDrumondApi.model.dto.CriarOrdemServicoDTO;
import br.edu.infnet.LeandroDrumondApi.model.dto.DefinirValorEAceitarDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ordens")
@Tag(name = "Ordens de Serviço", description = "Criação, transição de status, consulta e cancelamento de ordens de serviço.")
public class OrdemServicoController {

    private final OrdemServicoService service;

    public OrdemServicoController(OrdemServicoService service) {
        this.service = service;
    }

    @Operation(summary = "Criar ordem de serviço", description = "Cliente cria uma nova ordem informando categoria e descrição.")
    @PostMapping
    public ResponseEntity<OrdemServico> criar(@Valid @RequestBody CriarOrdemServicoDTO dto) {
        OrdemServico os = service.criar(dto.getClienteId(), dto.getCategoria(), dto.getDescricao());
        return ResponseEntity.status(HttpStatus.CREATED).body(os);
    }

    @Operation(summary = "Prestador aceita e define valor", description = "Prestador aceita a ordem e define o valor do serviço (90% prestador, 10% empresa).")
    @PostMapping("/{id}/aceitar")
    public ResponseEntity<OrdemServico> aceitar(@PathVariable Integer id,
                                                @Valid @RequestBody DefinirValorEAceitarDTO dto) {
        OrdemServico os = service.definirValorEAceitar(id, dto.getPrestadorId(), dto.getValor());
        return ResponseEntity.ok(os);
    }

    @Operation(summary = "Iniciar execução", description = "Altera o status para EM_ANDAMENTO.")
    @PostMapping("/{id}/iniciar")
    public ResponseEntity<OrdemServico> iniciar(@PathVariable Integer id) {
        OrdemServico os = service.iniciar(id);
        return ResponseEntity.ok(os);
    }

    @Operation(summary = "Concluir ordem", description = "Altera o status para CONCLUIDA.")
    @PostMapping("/{id}/concluir")
    public ResponseEntity<OrdemServico> concluir(@PathVariable Integer id) {
        OrdemServico os = service.concluir(id);
        return ResponseEntity.ok(os);
    }

    @Operation(summary = "Cancelar ordem", description = "Altera o status para CANCELADA, exigindo um motivo de cancelamento.")
    @PostMapping("/{id}/cancelar")
    public ResponseEntity<OrdemServico> cancelar(@PathVariable Integer id,
                                                 @Valid @RequestBody CancelarOrdemDTO dto) {
        OrdemServico ordemServico = service.cancelar(id, dto.getMotivo());
        return ResponseEntity.ok(ordemServico);
    }

    @Operation(summary = "Buscar ordem por ID", description = "Retorna os detalhes de uma ordem específica.")
    @GetMapping("/{id}")
    public ResponseEntity<OrdemServico> obter(@PathVariable Integer id) {
        return ResponseEntity.ok(service.obterOrdem(id));
    }

    @Operation(summary = "Listar ordens por cliente", description = "Lista todas as ordens relacionadas a um cliente.")
    @GetMapping("/cliente/{clienteId}")
    public ResponseEntity<List<OrdemServico>> listarPorCliente(@PathVariable Integer clienteId) {
        List<OrdemServico> lista = service.listarPorCliente(clienteId);
        if (lista == null || lista.isEmpty()) return ResponseEntity.noContent().build();
        return ResponseEntity.ok(lista);
    }

    @Operation(summary = "Listar ordens por prestador", description = "Lista todas as ordens relacionadas a um prestador.")
    @GetMapping("/prestador/{prestadorId}")
    public ResponseEntity<List<OrdemServico>> listarPorPrestador(@PathVariable Integer prestadorId) {
        List<OrdemServico> lista = service.listarPorPrestador(prestadorId);
        if (lista == null || lista.isEmpty()) return ResponseEntity.noContent().build();
        return ResponseEntity.ok(lista);
    }

    @Operation(summary = "Listar ordens por status", description = "Filtra ordens pelo status atual (CRIADA, ACEITA, EM_ANDAMENTO, CONCLUIDA, CANCELADA).")
    @GetMapping("/status/{status}")
    public ResponseEntity<List<OrdemServico>> listarPorStatus(@PathVariable StatusOrdem status) {
        List<OrdemServico> lista = service.listarPorStatus(status);
        if (lista == null || lista.isEmpty()) return ResponseEntity.noContent().build();
        return ResponseEntity.ok(lista);
    }
}
