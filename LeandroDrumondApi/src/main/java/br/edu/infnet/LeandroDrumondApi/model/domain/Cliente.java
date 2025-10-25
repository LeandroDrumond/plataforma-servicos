package br.edu.infnet.LeandroDrumondApi.model.domain;

public class Cliente extends Pessoa {
	
	  private String observacoes;

	public String getObservacoes() {
		return observacoes;
	}

	public void setObservacoes(String observacoes) {
		this.observacoes = observacoes;
	}
	
	 @Override
	    public String toString() {
	        String base = super.toString();
	        String obsStr = observacoes != null ? observacoes : "";

	        return String.format(
	            "%s | Observações: %s",
	            base, obsStr
	        );
	    }

}
