package br.edu.infnet.LeandroDrumondApi.model.domain;

import jakarta.persistence.Embedded;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.*;

@MappedSuperclass
public abstract class Pessoa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
	private Integer id;

	private String nome;
	private String cpf;
	private Contato contato;
	

	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getCpf() {
		return cpf;
	}
	public void setCpf(String cpf) {
		this.cpf = cpf;
	}
    @Embedded
	public Contato getContato() {
		return contato;
	}
	public void setContato(Contato contato) {
		this.contato = contato;
	}
	
	@Override
	public String toString() {
		
		 String idStr = id != null ? id.toString() : "";
	        String nomeStr = nome != null ? nome : "";
	        String cpfStr = cpf != null ? cpf : "";
	        String emailStr = (contato != null && contato.getEmail() != null) ? contato.getEmail() : "";
	        String telStr = (contato != null && contato.getTelefone() != null) ? contato.getTelefone() : "";
		
	        return String.format(
	                "%-36s | Nome: %-50s | Email: %-50s | CPF: %s | Telefone: %s",
	                idStr, nomeStr, emailStr, cpfStr, telStr
	            );
	}

}
