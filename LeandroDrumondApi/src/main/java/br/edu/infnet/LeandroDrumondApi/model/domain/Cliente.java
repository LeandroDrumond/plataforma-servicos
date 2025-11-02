package br.edu.infnet.LeandroDrumondApi.model.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import java.util.Date;

@Entity
@Table(name = "cliente",
        uniqueConstraints = @UniqueConstraint(columnNames = "codigo_cliente"))
public class Cliente extends Pessoa {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Column(name = "codigo_cliente", unique = true, nullable = true)
    private Long codigoCliente;

    @NotNull(message = "O campo ativo é obrigatório.")
    private Boolean ativo;

    @Size(max = 1000, message = "As observações devem ter no máximo 1000 caracteres.")
    private String observacoes;

    @PastOrPresent(message = "A data de atualização não pode estar no futuro.")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataAtualizacao;

    @PastOrPresent(message = "A data de inclusão não pode estar no futuro.")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataInclusao;

    public Long getCodigoCliente() { return codigoCliente; }
    public void setCodigoCliente(Long codigoCliente) { this.codigoCliente = codigoCliente; }
    public Boolean getAtivo() { return ativo; }
    public void setAtivo(Boolean ativo) { this.ativo = ativo; }
    public String getObservacoes() { return observacoes; }
    public void setObservacoes(String observacoes) { this.observacoes = observacoes; }
    public Date getDataAtualizacao() { return dataAtualizacao; }
    public void setDataAtualizacao(Date dataAtualizacao) { this.dataAtualizacao = dataAtualizacao; }
    public Date getDataInclusao() { return dataInclusao; }
    public void setDataInclusao(Date dataInclusao) { this.dataInclusao = dataInclusao; }

    @Override
    public String toString() {
        String base = super.toString();
        String incStr = dataInclusao != null ? dataInclusao.toString() : "";
        String atuStr = dataAtualizacao != null ? dataAtualizacao.toString() : "";
        return String.format("%s | CódigoCliente: %s | Ativo: %s | Inclusão: %s | Atualização: %s",
                base,
                codigoCliente,
                (ativo != null && ativo ? "Sim" : "Não"),
                incStr, atuStr);
    }
}
