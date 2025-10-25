package br.edu.infnet.LeandroDrumondApi.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.edu.infnet.LeandroDrumondApi.model.domain.PrestadorServico;

public interface PrestadorRepository extends JpaRepository<PrestadorServico, Integer> {

}
