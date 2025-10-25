package br.edu.infnet.LeandroDrumondApi.model.domain;


public class Contato {
	
    private String email;
    private String telefone;
    private Endereco endereco;
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getTelefone() {
		return telefone;
	}
	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}
	public Endereco getEndereco() {
		return endereco;
	}
	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}
	
	 @Override
	    public String toString() {
	        String emailStr = email != null ? email : "";
	        String telStr = telefone != null ? telefone : "";
	        String endStr = endereco != null ? endereco.toString() : "";
	        return String.format("Email: %-50s | Telefone: %-20s | Endereço: %s",
	                emailStr, telStr, endStr);
	    }
}
