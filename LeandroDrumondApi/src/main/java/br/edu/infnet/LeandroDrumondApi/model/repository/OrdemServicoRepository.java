package br.edu.infnet.LeandroDrumondApi.model.repository;

import br.edu.infnet.LeandroDrumondApi.model.domain.OrdemServico;
import br.edu.infnet.LeandroDrumondApi.model.domain.StatusOrdem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrdemServicoRepository extends JpaRepository<OrdemServico, Long> {
    List<OrdemServico> findByStatus(StatusOrdem status);
    List<OrdemServico> findByClienteId(Long clienteId);
    List<OrdemServico> findByPrestador_Id(Long prestadorId);
}
