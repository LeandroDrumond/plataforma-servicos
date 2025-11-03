package br.edu.infnet.LeandroDrumondApi.model.domain.service;

import br.edu.infnet.LeandroDrumondApi.exceptions.ClienteNaoEncontradoException;
import br.edu.infnet.LeandroDrumondApi.exceptions.OrdemNaoEncontradaException;
import br.edu.infnet.LeandroDrumondApi.exceptions.PrestadorNaoEncontradoException;
import br.edu.infnet.LeandroDrumondApi.model.domain.Cliente;
import br.edu.infnet.LeandroDrumondApi.model.domain.OrdemServico;
import br.edu.infnet.LeandroDrumondApi.model.domain.PrestadorServico;
import br.edu.infnet.LeandroDrumondApi.model.domain.enums.StatusOrdem;
import br.edu.infnet.LeandroDrumondApi.model.repository.ClienteRepository;
import br.edu.infnet.LeandroDrumondApi.model.repository.OrdemServicoRepository;
import br.edu.infnet.LeandroDrumondApi.model.repository.PrestadorRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Service
public class OrdemServicoService {

    private final OrdemServicoRepository ordemRepo;
    private final ClienteRepository clienteRepo;
    private final PrestadorRepository prestadorRepo;

    public OrdemServicoService(OrdemServicoRepository ordemRepo,
                               ClienteRepository clienteRepo,
                               PrestadorRepository prestadorRepo) {
        this.ordemRepo = ordemRepo;
        this.clienteRepo = clienteRepo;
        this.prestadorRepo = prestadorRepo;
    }

    @Transactional
    public OrdemServico criar(Integer clienteId, String categoria, String descricao) {
        Cliente cliente = clienteRepo.findById(clienteId)
                .orElseThrow(() -> new ClienteNaoEncontradoException("Cliente não encontrado com id: " + clienteId));

        if (categoria == null || categoria.isBlank()) {
            throw new IllegalArgumentException("A categoria do serviço é obrigatória.");
        }
        if (descricao == null || descricao.isBlank()) {
            throw new IllegalArgumentException("A descrição do serviço é obrigatória.");
        }

        OrdemServico ordemServico = new OrdemServico();
        Date agora = new Date();
        ordemServico.setCliente(cliente);
        ordemServico.setCategoria(categoria);
        ordemServico.setDescricao(descricao);
        ordemServico.setStatus(StatusOrdem.CRIADA);
        ordemServico.setDataCriacao(agora);
        ordemServico.setDataAtualizacao(agora);

        OrdemServico ordemServicoRepo = ordemRepo.save(ordemServico);

        if (ordemServicoRepo.getCodigoOrdem() == null) {
            long proximo = ordemRepo.findMaxCodigoOrdem() + 1;
            ordemServicoRepo.setCodigoOrdem(proximo);
            ordemServicoRepo = ordemRepo.save(ordemServicoRepo);
        }
        return ordemServicoRepo;
    }

    @Transactional
    public OrdemServico definirValorEAceitar(Integer ordemId, Integer prestadorId, BigDecimal valor) {

        OrdemServico ordemServico = obterOrdem(ordemId);

        PrestadorServico prestador = prestadorRepo.findById(prestadorId)
                .orElseThrow(() -> new PrestadorNaoEncontradoException("Prestador não encontrado com id: " + prestadorId));

        if (ordemServico.getStatus() != StatusOrdem.CRIADA) {
            throw new IllegalStateException("A ordem deve estar em estado CRIADA para ser aceita. Status atual: " + ordemServico.getStatus());
        }
        if (valor == null || valor.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("O valor do serviço é obrigatório e não pode ser negativo.");
        }

        ordemServico.setPrestador(prestador);
        ordemServico.setValorDefinido(valor);

        BigDecimal valorPrestador = valor.multiply(new BigDecimal("0.90"));
        BigDecimal valorEmpresa   = valor.multiply(new BigDecimal("0.10"));
        ordemServico.setValorPrestador(valorPrestador);
        ordemServico.setValorEmpresa(valorEmpresa);

        ordemServico.setStatus(StatusOrdem.ACEITA);
        ordemServico.setDataAtualizacao(new Date());

        return ordemRepo.save(ordemServico);
    }

    @Transactional
    public OrdemServico iniciar(Integer ordemId) {
        OrdemServico os = obterOrdem(ordemId);
        if (os.getStatus() != StatusOrdem.ACEITA) {
            throw new IllegalStateException("A ordem deve estar ACEITA para iniciar. Status atual: " + os.getStatus());
        }
        os.setStatus(StatusOrdem.EM_ANDAMENTO);
        os.setDataAtualizacao(new Date());
        return ordemRepo.save(os);
    }

    @Transactional
    public OrdemServico concluir(Integer ordemId) {
        OrdemServico os = obterOrdem(ordemId);
        if (os.getStatus() != StatusOrdem.EM_ANDAMENTO) {
            throw new IllegalStateException("A ordem deve estar EM_ANDAMENTO para concluir. Status atual: " + os.getStatus());
        }
        os.setStatus(StatusOrdem.CONCLUIDA);
        os.setDataAtualizacao(new Date());
        return ordemRepo.save(os);
    }

    @Transactional
    public OrdemServico cancelar(Integer ordemId, String motivo) {
        OrdemServico os = obterOrdem(ordemId);
        if (os.getStatus() == StatusOrdem.CONCLUIDA) {
            throw new IllegalStateException("Não é possível cancelar uma ordem já concluída.");
        }
        if (motivo == null || motivo.isBlank()) {
            throw new IllegalArgumentException("O motivo do cancelamento é obrigatório.");
        }
        os.setStatus(StatusOrdem.CANCELADA);
        os.setMotivoCancelamento(motivo);
        os.setDataAtualizacao(new Date());
        return ordemRepo.save(os);
    }

    public OrdemServico obterOrdem(Integer id) {
        return ordemRepo.findById(id)
                .orElseThrow(() -> new OrdemNaoEncontradaException("Ordem de serviço não encontrada com id: " + id));
    }

    public List<OrdemServico> listarPorCliente(Integer clienteId) {
        return ordemRepo.findByCliente_Id(clienteId);
    }

    public List<OrdemServico> listarPorPrestador(Integer prestadorId) {
        return ordemRepo.findByPrestador_Id(prestadorId);
    }

    public List<OrdemServico> listarPorStatus(StatusOrdem status) {
        return ordemRepo.findByStatus(status);
    }
}
