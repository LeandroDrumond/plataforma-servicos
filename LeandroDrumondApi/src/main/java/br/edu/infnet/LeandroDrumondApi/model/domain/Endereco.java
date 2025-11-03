package br.edu.infnet.LeandroDrumondApi.model.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Embeddable
public class Endereco {

    @NotBlank(message = "O CEP é obrigatório.")
    @Pattern(regexp = "\\d{8}", message = "O CEP deve conter exatamente 8 dígitos numéricos.")
    @Column(name = "cep", length = 8, nullable = false)
    private String cep;

    @NotBlank(message = "O logradouro é obrigatório.")
    @Size(max = 120, message = "O logradouro deve ter no máximo 120 caracteres.")
    @Column(name = "logradouro", length = 120, nullable = false)
    private String logradouro;

    @NotBlank(message = "O número é obrigatório.")
    @Size(max = 20, message = "O número deve ter no máximo 20 caracteres.")
    @Column(name = "numero", length = 20, nullable = false)
    private String numero;

    @NotBlank(message = "O bairro é obrigatório.")
    @Size(max = 80, message = "O bairro deve ter no máximo 80 caracteres.")
    @Column(name = "bairro", length = 80, nullable = false)
    private String bairro;

    @NotBlank(message = "A cidade é obrigatória.")
    @Size(max = 80, message = "A cidade deve ter no máximo 80 caracteres.")
    @Column(name = "cidade", length = 80, nullable = false)
    private String cidade;

    @NotBlank(message = "A UF é obrigatória.")
    @Pattern(regexp = "^[A-Z]{2}$", message = "A UF deve conter exatamente duas letras maiúsculas.")
    @Column(name = "uf", length = 2, nullable = false)
    private String uf;

    @NotBlank(message = "O país é obrigatório.")
    @Size(max = 80, message = "O país deve ter no máximo 80 caracteres.")
    @Column(name = "pais", length = 80, nullable = false)
    private String pais;

    @Size(max = 80, message = "O complemento deve ter no máximo 80 caracteres.")
    @Column(name = "complemento", length = 80)
    private String complemento;

    public String getCep() { return cep; }
    public void setCep(String cep) { this.cep = cep; }

    public String getLogradouro() { return logradouro; }
    public void setLogradouro(String logradouro) { this.logradouro = logradouro; }

    public String getNumero() { return numero; }
    public void setNumero(String numero) { this.numero = numero; }

    public String getBairro() { return bairro; }
    public void setBairro(String bairro) { this.bairro = bairro; }

    public String getCidade() { return cidade; }
    public void setCidade(String cidade) { this.cidade = cidade; }

    public String getUf() { return uf; }
    public void setUf(String uf) { this.uf = uf; }

    public String getPais() { return pais; }
    public void setPais(String pais) { this.pais = pais; }

    public String getComplemento() { return complemento; }
    public void setComplemento(String complemento) { this.complemento = complemento; }

    @Override
    public String toString() {
        String comp = (complemento == null || complemento.isBlank()) ? "" : " | Compl: " + complemento;
        return String.format("%s, %s - %s | %s/%s | CEP: %s | %s%s",
                nz(logradouro), nz(numero), nz(bairro),
                nz(cidade), nz(uf), nz(cep), nz(pais), comp);
    }

    private String nz(String v) { return v == null ? "" : v; }
}
