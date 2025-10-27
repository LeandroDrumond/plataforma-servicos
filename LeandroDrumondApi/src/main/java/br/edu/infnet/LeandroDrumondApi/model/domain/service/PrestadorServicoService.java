package br.edu.infnet.LeandroDrumondApi.model.domain.service;

import java.util.Date;
import java.util.List;
import java.util.function.Consumer;

import br.edu.infnet.LeandroDrumondApi.exceptions.PrestadorNaoEncontradoException;
import br.edu.infnet.LeandroDrumondApi.model.domain.Contato;
import br.edu.infnet.LeandroDrumondApi.model.domain.Endereco;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import br.edu.infnet.LeandroDrumondApi.interfaces.CrudService;
import br.edu.infnet.LeandroDrumondApi.model.domain.PrestadorServico;
import br.edu.infnet.LeandroDrumondApi.model.repository.PrestadorRepository;

@Service
public class PrestadorServicoService implements CrudService<PrestadorServico, Integer> {

    private final PrestadorRepository prestadorRepository;

    public PrestadorServicoService(PrestadorRepository prestadorRepository) {
        this.prestadorRepository = prestadorRepository;
    }

    @Override
    public PrestadorServico incluir(PrestadorServico prestador) {

        Validar(prestador);

        prestador.setDataInclusao(new Date());
        prestador.setDataAtualizacao(new Date());
        prestador.setCodigoPrestador(gerarCodigoPrestador());

        return prestadorRepository.save(prestador);
    }

    @Override
    public List<PrestadorServico> obterLista() {
        return prestadorRepository.findAll();
    }


    @Override
    @Transactional
    public PrestadorServico alterar(Integer id, PrestadorServico prestador) {

        Validar(prestador);

        PrestadorServico prestadorServico = prestadorRepository.findById(Long.valueOf(id))
                .orElseThrow(() -> new PrestadorNaoEncontradoException(
                        "Prestador não encontrado com id: " + id));

        validaContratoNull(prestador.getNome(),prestadorServico::setNome);
        validaContratoNull(prestador.getRemuneracao(),prestadorServico::setRemuneracao);
        validaContratoNull(prestador.getObservacoes(),prestadorServico::setObservacoes);
        validaContratoNull(prestador.getEspecialidades(),prestadorServico::setEspecialidades);
        validaContratoNull(prestador.getAtivo(), prestadorServico::setAtivo);

        if (prestador.getContato() != null) {
            if (prestadorServico.getContato() == null) {
                prestadorServico.setContato(new Contato());
            }

            var contato = prestador.getContato();
            var entityContato = prestadorServico.getContato();

            validaContratoNull(contato.getEmail(),entityContato::setEmail);
            validaContratoNull(contato.getTelefone(), entityContato::setTelefone);

            if (contato.getEndereco() != null) {
                if (entityContato.getEndereco() == null) {
                    entityContato.setEndereco(new Endereco());
                }
                var endereco = contato.getEndereco();
                var entityContatoEndereco = entityContato.getEndereco();

                validaContratoNull(endereco.getCep(),entityContatoEndereco::setCep);
                validaContratoNull(endereco.getLogradouro(),entityContatoEndereco::setLogradouro);
                validaContratoNull(endereco.getNumero(),entityContatoEndereco::setNumero);
                validaContratoNull(endereco.getBairro(),entityContatoEndereco::setBairro);
                validaContratoNull(endereco.getCidade(),entityContatoEndereco::setCidade);
                validaContratoNull(endereco.getUf(),entityContatoEndereco::setUf);
                validaContratoNull(endereco.getEstado(),entityContatoEndereco::setEstado);
                validaContratoNull(endereco.getPais(),entityContatoEndereco::setPais);
                validaContratoNull(endereco.getComplemento(), entityContatoEndereco::setComplemento);
            }
        }

        prestadorServico.setDataAtualizacao(new Date());

        if (prestadorServico.getDataInclusao() == null) {
            prestadorServico.setDataInclusao(new Date());
        }

        return prestadorRepository.save(prestadorServico);
    }


    @Override
	public void excluir(Integer id) {

        PrestadorServico prestadorServico = prestadorRepository.findById(Long.valueOf(id))
                .orElseThrow(() -> new PrestadorNaoEncontradoException("Prestador não encontrado com id: " + id));

        prestadorRepository.delete(prestadorServico);
		
	}

    @Override
    public PrestadorServico obterPorId(Integer id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("O ID utilizado na busca do prestador não pode ser nulo, zero ou negativo");
        }

        return prestadorRepository.findById(Long.valueOf(id))
                .orElseThrow(() -> new PrestadorNaoEncontradoException(
                        "Prestador não encontrado com id: " + id));
    }

    private Long gerarCodigoPrestador() {
        long proximo = prestadorRepository.findMaxCodigoPrestador() + 1;

        while (prestadorRepository.existsByCodigoPrestador(proximo)) {
            proximo++;
        }
        return proximo;
    }

    private static <T> void validaContratoNull(T value, Consumer<T> setter) {
        if (value != null) setter.accept(value);
    }

    private void Validar(PrestadorServico prestador) {

        if (prestador == null) {
            throw new IllegalArgumentException("O prestador não pode estar nulo!");
        }
        if (prestador.getNome() == null || prestador.getNome().trim().isEmpty()) {
            throw new IllegalArgumentException("O nome do prestador é obrigatório!");
        }
        if (prestador.getId() != null) {
            throw new IllegalArgumentException("Novo prestador não pode ter ID preenchido!");
        }
    }
	



 
 


}
