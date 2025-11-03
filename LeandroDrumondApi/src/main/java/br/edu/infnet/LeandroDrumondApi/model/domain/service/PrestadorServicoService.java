package br.edu.infnet.LeandroDrumondApi.model.domain.service;

import br.edu.infnet.LeandroDrumondApi.exceptions.PrestadorNaoEncontradoException;
import br.edu.infnet.LeandroDrumondApi.interfaces.CrudService;
import br.edu.infnet.LeandroDrumondApi.model.domain.Contato;
import br.edu.infnet.LeandroDrumondApi.model.domain.Endereco;
import br.edu.infnet.LeandroDrumondApi.model.domain.PrestadorServico;
import br.edu.infnet.LeandroDrumondApi.model.repository.PrestadorRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

import static br.edu.infnet.LeandroDrumondApi.utils.ValidacaoHelper.aplicarSeNaoNulo;

@Service
public class PrestadorServicoService implements CrudService<PrestadorServico, Integer> {

    private final PrestadorRepository prestadorRepository;

    public PrestadorServicoService(PrestadorRepository prestadorRepository) {
        this.prestadorRepository = prestadorRepository;
    }

    @Override
    public PrestadorServico incluir(PrestadorServico prestador) {

        if (prestador.getCpf() == null || prestador.getCpf().isBlank()) {
            throw new IllegalArgumentException("O CPF do prestador é obrigatório!");
        }
        if (prestadorRepository.existsByCpf(prestador.getCpf())) {
            throw new IllegalArgumentException("Já existe um prestador cadastrado com o CPF informado: " + prestador.getCpf());
        }

        long proximo = Long.valueOf(prestadorRepository.findMaxCodigoPrestador());
        proximo++;
        while (prestadorRepository.existsByCodigoPrestador(proximo)) {
            proximo++;
        }
        prestador.setCodigoPrestador(proximo);

        Date agora = new Date();
        prestador.setDataInclusao(agora);
        prestador.setDataAtualizacao(agora);

        if (prestador.getAtivo() == null) {
            prestador.setAtivo(Boolean.TRUE);
        }

        return prestadorRepository.save(prestador);
    }

    @Override
    public List<PrestadorServico> obterLista() {
        return prestadorRepository.findAll();
    }

    @Override
    @Transactional
    public PrestadorServico alterar(Integer id, PrestadorServico prestador) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("O ID utilizado na alteração não pode ser nulo, zero ou negativo");
        }

        PrestadorServico entity = prestadorRepository.findById(id)
                .orElseThrow(() -> new PrestadorNaoEncontradoException("Prestador não encontrado com id: " + id));

        // Se CPF vier para alterar, validar duplicidade (permitindo manter o mesmo)
        if (prestador.getCpf() != null && !prestador.getCpf().isBlank()
                && !prestador.getCpf().equals(entity.getCpf())) {
            if (prestadorRepository.existsByCpf(prestador.getCpf())) {
                throw new IllegalArgumentException("Já existe um prestador cadastrado com o CPF informado: " + prestador.getCpf());
            }
            entity.setCpf(prestador.getCpf());
        }

        aplicarSeNaoNulo(prestador.getNome(), entity::setNome);
        aplicarSeNaoNulo(prestador.getRemuneracao(), entity::setRemuneracao);
        aplicarSeNaoNulo(prestador.getObservacoes(), entity::setObservacoes);
        aplicarSeNaoNulo(prestador.getEspecialidades(), entity::setEspecialidades);
        aplicarSeNaoNulo(prestador.getAtivo(), entity::setAtivo);

        if (prestador.getContato() != null) {
            if (entity.getContato() == null) {
                entity.setContato(new Contato());
            }
            var prestadorContato = prestador.getContato();
            var entityContato = entity.getContato();

            aplicarSeNaoNulo(prestadorContato.getEmail(), entityContato::setEmail);
            aplicarSeNaoNulo(prestadorContato.getTelefone(), entityContato::setTelefone);

            if (prestadorContato.getEndereco() != null) {
                if (entityContato.getEndereco() == null) {
                    entityContato.setEndereco(new Endereco());
                }
                var endIn = prestadorContato.getEndereco();
                var endEn = entityContato.getEndereco();

                aplicarSeNaoNulo(endIn.getCep(), endEn::setCep);
                aplicarSeNaoNulo(endIn.getLogradouro(), endEn::setLogradouro);
                aplicarSeNaoNulo(endIn.getNumero(), endEn::setNumero);
                aplicarSeNaoNulo(endIn.getBairro(), endEn::setBairro);
                aplicarSeNaoNulo(endIn.getCidade(), endEn::setCidade);
                aplicarSeNaoNulo(endIn.getUf(), endEn::setUf);
                aplicarSeNaoNulo(endIn.getPais(), endEn::setPais);
                aplicarSeNaoNulo(endIn.getComplemento(), endEn::setComplemento);
            }
        }

        entity.setDataAtualizacao(new Date());

        if (entity.getDataInclusao() == null) {
            entity.setDataInclusao(new Date());
        }

        return prestadorRepository.save(entity);
    }

    @Override
    public void excluir(Integer id) {

        if (id == null || id <= 0) {
            throw new IllegalArgumentException("O ID utilizado na exclusão não pode ser nulo, zero ou negativo");
        }
        PrestadorServico entity = prestadorRepository.findById(id)
                .orElseThrow(() -> new PrestadorNaoEncontradoException("Prestador não encontrado com id: " + id));
        prestadorRepository.delete(entity);
    }

    @Override
    public PrestadorServico obterPorId(Integer id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("O ID utilizado na busca do prestador não pode ser nulo, zero ou negativo");
        }
        return prestadorRepository.findById(id)
                .orElseThrow(() -> new PrestadorNaoEncontradoException("Prestador não encontrado com id: " + id));
    }

    // --------- Consultas auxiliares (usando métodos do repository) ---------

    public PrestadorServico buscarPorCpf(String cpf) {
        if (cpf == null || cpf.isBlank()) {
            throw new IllegalArgumentException("O CPF para busca não pode ser nulo ou vazio.");
        }
        return prestadorRepository.findByCpf(cpf)
                .orElseThrow(() -> new PrestadorNaoEncontradoException("Prestador não encontrado para o CPF: " + cpf));
    }

    public List<PrestadorServico> buscarAtivosPorNome(String nome) {
        if (nome == null || nome.isBlank()) {
            throw new IllegalArgumentException("O nome para busca não pode ser nulo ou vazio.");
        }
        return prestadorRepository.findByNomeContainingIgnoreCaseAndAtivoTrue(nome);
    }

    public List<PrestadorServico> buscarPorEspecialidade(String especialidade) {
        if (especialidade == null || especialidade.isBlank()) {
            throw new IllegalArgumentException("A especialidade para busca não pode ser nula ou vazia.");
        }
        return prestadorRepository.searchByEspecialidade(especialidade);
    }



}
