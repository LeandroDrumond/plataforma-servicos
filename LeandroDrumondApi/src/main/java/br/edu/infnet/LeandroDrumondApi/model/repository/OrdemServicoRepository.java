package br.edu.infnet.LeandroDrumondApi.model.repository;

import br.edu.infnet.LeandroDrumondApi.model.domain.OrdemServico;
import br.edu.infnet.LeandroDrumondApi.model.domain.enums.StatusOrdem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OrdemServicoRepository extends JpaRepository<OrdemServico, Integer> {

    @Query("select coalesce(max(o.codigoOrdem), 0) from OrdemServico o")
    Long findMaxCodigoOrdem();

    List<OrdemServico> findByCliente_Id(Integer clienteId);

    List<OrdemServico> findByPrestador_Id(Integer prestadorId);

    List<OrdemServico> findByStatus(StatusOrdem status);
}
