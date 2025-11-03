package br.edu.infnet.LeandroDrumondApi.model.repository;

import br.edu.infnet.LeandroDrumondApi.model.domain.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface ClienteRepository extends JpaRepository<Cliente, Integer> {

    Optional<Cliente> findByCpf(String cpf);

    List<Cliente> findByNomeContainingIgnoreCase(String nome);

    List<Cliente> findByNomeContainingIgnoreCaseAndCpfContaining(String nome, String cpf);
}
