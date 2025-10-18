package br.edu.infnet.LeandroDrumondApi.model.domain;

public class Endereco {

	private String cep;
	private String logradouro;
	private String numero;
	private String complemento;
	private String bairro;
	private String cidade;
	private String uf;
	private String estado;
	private String pais;
	
	public String getCep() {
		return cep;
	}
	public void setCep(String cep) {
		this.cep = cep;
	}
	public String getComlemento() {
		return complemento;
	}
	public void setComplemento(String complemento) {
		this.complemento = complemento;
	}
	
	public String getNumero() {
		return numero;
	}
	public void setNumero(String numero) {
		this.numero = numero;
	}
	
	public String getLogradouro() {
		return logradouro;
	}
	public void setLogradouro(String logradouro) {
		this.logradouro = logradouro;
	}
	public String getBairro() {
		return bairro;
	}
	public void setBairro(String bairro) {
		this.bairro = bairro;
	}
	public String getLocalidade() {
		return cidade;
	}
	public void setLocalidade(String cidade) {
		this.cidade = cidade;
	}
	public String getUf() {
		return uf;
	}
	public void setUf(String uf) {
		this.uf = uf;
	}
	public String getEstado() {
		return estado;
	}
	public void setEstado(String estado) {
		this.estado = estado;
	}
	public String getPais() {
		return pais;
	}
	public void setPais(String pais) {
		this.pais = pais;
	}
	
	@Override
    public String toString() {
        String cepStr = cep != null ? cep : "";
        String logStr = logradouro != null ? logradouro : "";
        String numStr = numero != null ? numero : "";
        String baiStr = bairro != null ? bairro : "";
        String cidStr = cidade != null ? cidade : "";
        String ufStr  = uf != null ? uf : "";
        String compStr = complemento != null ? complemento : "";
        String paisStr = pais != null ? pais : "";

       
        return String.format("%s, %s - %s | %s/%s | CEP: %s | %s%s",
                logStr, numStr, baiStr, cidStr, ufStr, cepStr, paisStr,
                compStr.isEmpty() ? "" : " | Compl: " + compStr);
    }

}
