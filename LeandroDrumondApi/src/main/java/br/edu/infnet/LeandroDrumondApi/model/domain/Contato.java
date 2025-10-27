package br.edu.infnet.LeandroDrumondApi.model.domain;

import jakarta.persistence.*;

@Embeddable
public class Contato {

    @Column(name = "email", length = 120)
    private String email;

    @Column(name = "telefone", length = 25)
    private String telefone;

    @Embedded
    private Endereco endereco;

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getTelefone() { return telefone; }
    public void setTelefone(String telefone) { this.telefone = telefone; }

    public Endereco getEndereco() { return endereco; }
    public void setEndereco(Endereco endereco) { this.endereco = endereco; }

    @Override
    public String toString() {
        String endStr = endereco != null ? endereco.toString() : "";
        return String.format("Email: %-50s | Telefone: %-20s | Endereço: %s",
                email == null ? "" : email, telefone == null ? "" : telefone, endStr);
    }
}
