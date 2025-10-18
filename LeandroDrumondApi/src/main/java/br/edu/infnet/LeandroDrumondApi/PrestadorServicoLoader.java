package br.edu.infnet.LeandroDrumondApi;

import java.io.BufferedReader;
import java.io.FileReader;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import br.edu.infnet.LeandroDrumondApi.model.domain.Contato;
import br.edu.infnet.LeandroDrumondApi.model.domain.Endereco;
import br.edu.infnet.LeandroDrumondApi.model.domain.PrestadorServico;
import br.edu.infnet.LeandroDrumondApi.model.domain.service.PrestadorServicoService;


@Component
public class PrestadorServicoLoader implements ApplicationRunner  {

	
	private final PrestadorServicoService prestadorServicoService;
	
	public PrestadorServicoLoader(PrestadorServicoService prestadorServicoService) {
        
		this.prestadorServicoService = prestadorServicoService;
    }

	@Override
	public void run(ApplicationArguments args) throws Exception {
		
		FileReader arquivo = new FileReader("prestadores-listagem.csv");
        BufferedReader leitura = new BufferedReader(arquivo);
        
        leitura.readLine();
        
        String linha = leitura.readLine();
        String[] campos = null;
        
        
        while (linha != null) {
        	
        	 campos = linha.split(";");
        	 
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
               
               prestadorServicoService.incluir(prestador);

               linha = leitura.readLine();
        }
        
        Collection<PrestadorServico> prestadores = prestadorServicoService.obterLista();
        
        prestadores.forEach(System.out::println);

        leitura.close();
		
	}

	
}
