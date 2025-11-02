package br.edu.infnet.LeandroDrumondApi.model.domain.service;

import br.edu.infnet.LeandroDrumondApi.exceptions.ClienteNaoEncontradoException;
import br.edu.infnet.LeandroDrumondApi.interfaces.CrudService;
import br.edu.infnet.LeandroDrumondApi.model.domain.Cliente;
import br.edu.infnet.LeandroDrumondApi.model.domain.Contato;
import br.edu.infnet.LeandroDrumondApi.model.domain.Endereco;
import br.edu.infnet.LeandroDrumondApi.model.repository.ClienteRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

import static br.edu.infnet.LeandroDrumondApi.utils.ValidacaoHelper.aplicarSeNaoNulo;

@Service
public class ClienteService implements CrudService<Cliente, Integer> {

    private final ClienteRepository clienteRepository;

    public ClienteService(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    @Override
    @Transactional
    public Cliente incluir(Cliente cliente) {

        Date agora = new Date();
        cliente.setDataInclusao(agora);
        cliente.setDataAtualizacao(agora);

        Cliente salvo = clienteRepository.save(cliente);

        if (salvo.getCodigoCliente() == null) {
            salvo.setCodigoCliente(salvo.getId().longValue());
            salvo = clienteRepository.save(salvo);
        }

        return salvo;
    }

    @Override
    public List<Cliente> obterLista() {
        return clienteRepository.findAll();
    }

    @Override
    @Transactional
    public Cliente alterar(Integer id, Cliente cliente) {

        Cliente clienteRepository = this.clienteRepository.findById(id)
                .orElseThrow(() -> new ClienteNaoEncontradoException("Cliente não encontrado com id: " + id));

        aplicarSeNaoNulo(cliente.getNome(), clienteRepository::setNome);
        aplicarSeNaoNulo(cliente.getCpf(), clienteRepository::setCpf);
        aplicarSeNaoNulo(cliente.getObservacoes(), clienteRepository::setObservacoes);
        aplicarSeNaoNulo(cliente.getAtivo(), clienteRepository::setAtivo);

        if (cliente.getContato() != null) {
            if (clienteRepository.getContato() == null) clienteRepository.setContato(new Contato());
            var contato = cliente.getContato();
            var clienteRepositoryContato = clienteRepository.getContato();

            aplicarSeNaoNulo(contato.getEmail(), clienteRepositoryContato::setEmail);
            aplicarSeNaoNulo(contato.getTelefone(), clienteRepositoryContato::setTelefone);

            if (contato.getEndereco() != null) {
                if (clienteRepositoryContato.getEndereco() == null) clienteRepositoryContato.setEndereco(new Endereco());
                var endereco = contato.getEndereco();
                var repositoryContatoEndereco = clienteRepositoryContato.getEndereco();

                aplicarSeNaoNulo(endereco.getCep(), repositoryContatoEndereco::setCep);
                aplicarSeNaoNulo(endereco.getLogradouro(), repositoryContatoEndereco::setLogradouro);
                aplicarSeNaoNulo(endereco.getNumero(), repositoryContatoEndereco::setNumero);
                aplicarSeNaoNulo(endereco.getBairro(), repositoryContatoEndereco::setBairro);
                aplicarSeNaoNulo(endereco.getCidade(), repositoryContatoEndereco::setCidade);
                aplicarSeNaoNulo(endereco.getUf(), repositoryContatoEndereco::setUf);
                aplicarSeNaoNulo(endereco.getPais(), repositoryContatoEndereco::setPais);
                aplicarSeNaoNulo(endereco.getComplemento(), repositoryContatoEndereco::setComplemento);
            }
        }

        clienteRepository.setDataAtualizacao(new Date());
        if (clienteRepository.getDataInclusao() == null) clienteRepository.setDataInclusao(new Date());

        return this.clienteRepository.save(clienteRepository);
    }

    @Override
    @Transactional
    public void excluir(Integer id) {
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new ClienteNaoEncontradoException("Cliente não encontrado com id: " + id));
        clienteRepository.delete(cliente);
    }

    @Override
    public Cliente obterPorId(Integer id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("O ID utilizado na busca do cliente não pode ser nulo, zero ou negativo");
        }
        return clienteRepository.findById(id)
                .orElseThrow(() -> new ClienteNaoEncontradoException("Cliente não encontrado com id: " + id));
    }

    public Cliente buscarPorCpf(String cpf) {
        if (cpf == null || cpf.isBlank()) {
            throw new IllegalArgumentException("O CPF informado não pode estar vazio ou nulo.");
        }
        return clienteRepository.findByCpf(cpf)
                .orElseThrow(() -> new ClienteNaoEncontradoException(
                        "Nenhum cliente foi encontrado com o CPF informado: " + cpf));
    }

    public List<Cliente> buscarPorNome(String nome) {
        return clienteRepository.findByNomeContainingIgnoreCase(nome);
    }

    public List<Cliente> buscarPorNomeECpf(String nome, String cpf) {

        String n = (nome == null) ? "" : nome;
        String c = (cpf == null) ? "" : cpf;

        return clienteRepository.findByNomeContainingIgnoreCaseAndCpfContaining(n, c);
    }

}
