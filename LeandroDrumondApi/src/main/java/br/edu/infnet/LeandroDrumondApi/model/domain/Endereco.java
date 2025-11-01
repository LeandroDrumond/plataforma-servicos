package br.edu.infnet.LeandroDrumondApi.model.domain;

import jakarta.persistence.*;

@Embeddable
public class Endereco {

    @Column(name = "cep", length = 12)
    private String cep;

    @Column(name = "logradouro", length = 120)
    private String logradouro;

    @Column(name = "numero", length = 20)
    private String numero;

    @Column(name = "bairro", length = 80)
    private String bairro;

    @Column(name = "cidade", length = 80)
    private String cidade;

    @Column(name = "uf", length = 2)
    private String uf;

    @Column(name = "pais", length = 80)
    private String pais;

    @Column(name = "complemento", length = 80)
    private String complemento;

    public String getCep() { return cep; }
    public void setCep(String cep) { this.cep = cep; }

    public String getLogradouro() { return logradouro; }
    public void setLogradouro(String logradouro) { this.logradouro = logradouro; }

    public String getNumero() { return numero; }
    public void setNumero(String numero) { this.numero = numero; }

    public String getComplemento() { return complemento; }
    public void setComplemento(String complemento) { this.complemento = complemento; }

    public String getBairro() { return bairro; }
    public void setBairro(String bairro) { this.bairro = bairro; }

    public String getCidade() { return cidade; }
    public void setCidade(String cidade) { this.cidade = cidade; }

    public String getUf() { return uf; }
    public void setUf(String uf) { this.uf = uf; }

    public String getPais() { return pais; }
    public void setPais(String pais) { this.pais = pais; }

    @Override
    public String toString() {
        String comp = (complemento == null || complemento.isBlank()) ? "" : " | Compl: " + complemento;
        return String.format("%s, %s - %s | %s/%s | CEP: %s | %s%s",
                nz(logradouro), nz(numero), nz(bairro), nz(cidade), nz(uf), nz(cep), nz(pais), comp);
    }

    private String nz(String v){ return v == null ? "" : v; }
}
