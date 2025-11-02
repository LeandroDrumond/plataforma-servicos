package br.edu.infnet.LeandroDrumondApi;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;
import java.util.Arrays;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import br.edu.infnet.LeandroDrumondApi.model.domain.Contato;
import br.edu.infnet.LeandroDrumondApi.model.domain.Endereco;
import br.edu.infnet.LeandroDrumondApi.model.domain.PrestadorServico;

@Component
public class PrestadorServicoLoader implements ApplicationRunner {

    private final List<PrestadorServico> prestadores = new ArrayList<>();

    @Override
    public void run(ApplicationArguments args) throws Exception {
        System.out.println("🔹 Iniciando carregamento de prestadores..");

        try (BufferedReader leitura = new BufferedReader(
                new InputStreamReader(
                        Objects.requireNonNull(
                                getClass().getResourceAsStream("/data/prestadores-listagem.csv"),
                                "Arquivo CSV não encontrado em /data/prestadores-listagem.csv"),
                        StandardCharsets.UTF_8))) {


            leitura.readLine();

            String linha;
            int linhaNum = 1;
            while ((linha = leitura.readLine()) != null) {

                if (linha.isBlank()) continue;

                String[] campos = linha.split(";", -1);

                Endereco endereco = new Endereco();
                endereco.setCep(campos[7]);
                endereco.setLogradouro(campos[8] + ", " + campos[9]);

                Contato contato = new Contato();
                contato.setEmail(campos[1]);
                contato.setTelefone(campos[3]);
                contato.setEndereco(endereco);

                List<String> especialidades = null;
                if (campos.length > 10 && campos[10] != null && !campos[10].isBlank()) {
                    especialidades = Arrays.stream(campos[10].split(","))
                            .map(String::trim)
                            .filter(s -> !s.isEmpty())
                            .collect(Collectors.toList());
                }

                PrestadorServico prestador = new PrestadorServico();
                prestador.setNome(campos[0]);
                prestador.setCpf(campos[2]);
                prestador.setContato(contato);
                prestador.setCodigoPrestador(Long.parseLong(campos[4]));
                prestador.setRemuneracao(new BigDecimal(campos[5]));
                prestador.setAtivo(Boolean.parseBoolean(campos[6]));
                prestador.setDataInclusao(new Date());
                prestador.setDataAtualizacao(new Date());
                prestador.setEspecialidades(especialidades);

                prestadores.add(prestador);

                System.out.println("  Nome: " + campos[0]);
                System.out.println("  Email: " + campos[1]);
                System.out.println("  CPF: " + campos[2]);
                System.out.println("  Telefone: " + campos[3]);
                System.out.println("  Código Prestador: " + campos[4]);
                System.out.println("  Remuneração: " + campos[5]);
                System.out.println("  Ativo: " + campos[6]);
                System.out.println("  CEP: " + campos[7]);
                System.out.println("  Logradouro: " + campos[8]);
                System.out.println("  Número: " + campos[9]);
                System.out.println("  Especialidades: " + (especialidades != null ? especialidades : "Nenhuma"));
            }
        }

        System.out.printf("\n✅ Total de %d prestadores carregados.%n", prestadores.size());
    }

}
