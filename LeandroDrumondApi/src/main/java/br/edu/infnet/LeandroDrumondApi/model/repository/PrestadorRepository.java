package br.edu.infnet.LeandroDrumondApi.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.edu.infnet.LeandroDrumondApi.model.domain.PrestadorServico;
import org.springframework.data.jpa.repository.Query;

public interface PrestadorRepository extends JpaRepository<PrestadorServico, Long> {

    boolean existsByCodigoPrestador(Long codigoPrestador);

    @Query("select coalesce(max(p.codigoPrestador), 0) from PrestadorServico p")
    Long findMaxCodigoPrestador();

}

