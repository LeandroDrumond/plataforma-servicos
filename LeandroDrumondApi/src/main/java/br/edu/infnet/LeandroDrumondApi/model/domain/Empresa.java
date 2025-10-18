package br.edu.infnet.LeandroDrumondApi.model.domain;

import java.math.BigDecimal;

public class Empresa {

	    private String id;
	    private String nome;
	    private String email;
	    private Contato contato;
	    private BigDecimal taxaIntermediacaoPercentual = new BigDecimal("10.00");
	    
		public String getId() {
			return id;
		}
		public void setId(String id) {
			this.id = id;
		}
		public String getNome() {
			return nome;
		}
		public void setNome(String nome) {
			this.nome = nome;
		}
		public String getEmail() {
			return email;
		}
		public void setEmail(String email) {
			this.email = email;
		}
		public Contato getContato() {
			return contato;
		}
		public void setContato(Contato contato) {
			this.contato = contato;
		}
		public BigDecimal getTaxaIntermediacaoPercentual() {
			return taxaIntermediacaoPercentual;
		}
		public void setTaxaIntermediacaoPercentual(BigDecimal taxaIntermediacaoPercentual) {
			this.taxaIntermediacaoPercentual = taxaIntermediacaoPercentual;
		}
		
		 @Override
		    public String toString() {
		        String idStr   = id != null ? id.toString() : "";
		        String nomeStr = nome != null ? nome : "";
		        String emailStr = (contato != null && contato.getEmail() != null) ? contato.getEmail() : "";
		        String telStr   = (contato != null && contato.getTelefone() != null) ? contato.getTelefone() : "";

		        return String.format(
		            "%-36s | Nome: %-50s | Email: %-50s | CPF: %s | Telefone: %s | Taxa: %s%%",
		            idStr, nomeStr, emailStr, "", telStr,
		            taxaIntermediacaoPercentual != null ? taxaIntermediacaoPercentual.toString() : "0.00"
		        );
		    }
}
