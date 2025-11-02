package br.edu.infnet.LeandroDrumondApi.model.domain;

import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@MappedSuperclass
public abstract class Pessoa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    protected Integer id;

    @NotBlank(message = "O nome é obrigatório.")
    @Size(min = 3, max = 120, message = "O nome deve ter entre 3 e 120 caracteres.")
    protected String nome;

    @NotBlank(message = "O CPF é obrigatório.")
    @Pattern(regexp = "\\d{11}", message = "O CPF deve conter exatamente 11 dígitos numéricos.")
    @Column(unique = true, nullable = false, length = 14)
    protected String cpf;

    @Embedded
    @Valid
    protected Contato contato;

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public String getCpf() { return cpf; }
    public void setCpf(String cpf) { this.cpf = cpf; }
    public Contato getContato() { return contato; }
    public void setContato(Contato contato) { this.contato = contato; }

    @Override
    public String toString() {
        String idStr = id != null ? id.toString() : "";
        String nomeStr = nome != null ? nome : "";
        String cpfStr = cpf != null ? cpf : "";
        String emailStr = (contato != null && contato.getEmail() != null) ? contato.getEmail() : "";
        String telStr = (contato != null && contato.getTelefone() != null) ? contato.getTelefone() : "";
        return String.format("%-6s | Nome: %-40s | Email: %-30s | CPF: %s | Telefone: %s",
                idStr, nomeStr, emailStr, cpfStr, telStr);
    }
}
