package br.edu.infnet.LeandroDrumondApi.model.domain.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.stereotype.Service;

import br.edu.infnet.LeandroDrumondApi.interfaces.CrudService;
import br.edu.infnet.LeandroDrumondApi.model.domain.PrestadorServico;

@Service
public class PrestadorServicoService implements CrudService<PrestadorServico, Integer> {

    private final Map<Integer, PrestadorServico> mapa = new ConcurrentHashMap<>();
	private final AtomicInteger nextId = new AtomicInteger(1);

  
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


    @Override
    public PrestadorServico incluir(PrestadorServico prestador) {
    	Validar(prestador);

        prestador.setId(nextId.getAndIncrement());
        mapa.put(prestador.getId(), prestador);

        return prestador;
    }

    @Override
    public List<PrestadorServico> obterLista() {
        return new ArrayList<>(mapa.values());
    }


	@Override
	public PrestadorServico alterar(Integer id, PrestadorServico prestador) {
		
		Validar(prestador);
		mapa.put(prestador.getId(), prestador);
		return prestador;
	}


	@Override
	public void excluir(Integer id) {
		
		PrestadorServico prestadorServico = obterPorId(id);
		mapa.remove(prestadorServico.getId());
		
	}


	@Override
	public PrestadorServico obterPorId(Integer id) {
		
		  if(id == null || id <= 0) {
			  throw new IllegalArgumentException("o ID utilizado na busca do prestador não pode ser nulo/zero/negativo");
		    }
		
		    PrestadorServico prestador = mapa.get(id);
		
		  if(prestador == null) {
			// TODO: Fazer logica
		   }
		
		   return prestador;
	}
	
	public PrestadorServico inativar(Integer id) {
		
		PrestadorServico prestador = obterPorId(id);
		
		if (!prestador.isAtivo()) {
			
			// TODO: Fazer logica
		}
		
		prestador.setAtivo(false);
		
		return prestador;
		
	}
	



 
 


}
