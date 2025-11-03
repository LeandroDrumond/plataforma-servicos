package br.edu.infnet.LeandroDrumondApi.model.repository;

import br.edu.infnet.LeandroDrumondApi.model.domain.PrestadorServico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface PrestadorRepository extends JpaRepository<PrestadorServico, Integer> {

    // --- CPF ---
    Optional<PrestadorServico> findByCpf(String cpf);

    boolean existsByCpf(String cpf); // Útil para validação explícita antes do save()

    List<PrestadorServico> findByNomeContainingIgnoreCaseAndAtivoTrue(String nome);


    @Query("""
           select distinct p
           from PrestadorServico p
           join p.especialidades e
           where lower(e) like lower(concat('%', :especialidade, '%'))
             and (p.ativo = true or p.ativo is null)
           """)
    List<PrestadorServico> searchByEspecialidade(String especialidade);


    @Query("select coalesce(max(p.codigoPrestador), 0) from PrestadorServico p")

    Long findMaxCodigoPrestador();

    boolean existsByCodigoPrestador(Long codigoPrestador);
}
