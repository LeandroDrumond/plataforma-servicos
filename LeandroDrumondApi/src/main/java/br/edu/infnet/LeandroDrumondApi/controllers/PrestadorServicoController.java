package br.edu.infnet.LeandroDrumondApi.controllers;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
	
    @PutMapping("/{id}")
    public PrestadorServico Alterar (@PathVariable Integer id, @RequestBody PrestadorServico prestadorServico) {
    	
    	PrestadorServico prestadorAlterado = prestadorService.alterar(id, prestadorServico);
    	
    	return prestadorAlterado;
    	
    }
    
    @DeleteMapping
    public void Excluir (@PathVariable Integer id) {
    	
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
