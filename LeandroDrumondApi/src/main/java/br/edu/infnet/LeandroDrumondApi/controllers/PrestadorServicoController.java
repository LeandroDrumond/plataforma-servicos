package br.edu.infnet.LeandroDrumondApi.controllers;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import br.edu.infnet.LeandroDrumondApi.model.domain.PrestadorServico;
import br.edu.infnet.LeandroDrumondApi.model.domain.service.PrestadorServicoService;


@RestController
@RequestMapping("/api/prestadoreservico")
public class PrestadorServicoController {
	
	private final PrestadorServicoService prestadorService;
	
	public PrestadorServicoController(PrestadorServicoService prestadorService ) {
		
		this.prestadorService = prestadorService;
	}
	
	
	@PostMapping
	public PrestadorServico incluir (@RequestBody PrestadorServico prestadorServico ) {
		
		PrestadorServico prestadorIncluido = prestadorService.incluir(prestadorServico);
		
		return prestadorIncluido;
		
		
	}

    @PatchMapping("/{id}")
    public PrestadorServico patch(@PathVariable Integer id,
                                  @RequestBody PrestadorServico prestadorServico) {
        return prestadorService.alterar(id, prestadorServico);
    }

    @DeleteMapping("/{id}")
    public void excluir(@PathVariable Integer id) {
    	
    	prestadorService.excluir(id);
    	
    }
	
    @GetMapping
    public List<PrestadorServico> obterLista() {
    	
    	return prestadorService.obterLista();
    }
    
    
    @GetMapping("/{id}")
    public PrestadorServico obterPorId (@PathVariable Integer id) {
    		
    	return prestadorService.obterPorId(id);
    	
    }

}
