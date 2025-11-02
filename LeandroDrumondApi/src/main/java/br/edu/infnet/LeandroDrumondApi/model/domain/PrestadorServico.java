package br.edu.infnet.LeandroDrumondApi.model.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "prestador_servico",
        uniqueConstraints = @UniqueConstraint(columnNames = "codigo_prestador"))
public class PrestadorServico extends Pessoa {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Column(name = "codigo_prestador", unique = true, nullable = true)
    private Long codigoPrestador;

    @ElementCollection
    @CollectionTable(name = "prestador_especialidade", joinColumns = @JoinColumn(name = "prestador_id"))
    @Column(name = "especialidade")
    @NotEmpty(message = "É necessário informar ao menos uma especialidade.")
    private List<@NotBlank(message = "A especialidade não pode estar em branco.") String> especialidades;

    @Size(max = 1000, message = "As observações devem ter no máximo 1000 caracteres.")
    private String observacoes;

    @PastOrPresent(message = "A data de atualização não pode estar no futuro.")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataAtualizacao;

    @PastOrPresent(message = "A data de inclusão não pode estar no futuro.")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataInclusao;

    @DecimalMin(value = "0.00", message = "A remuneração não pode ser negativa.")
    private BigDecimal remuneracao;

    @NotNull(message = "O campo ativo é obrigatório.")
    private Boolean ativo;

    public Long getCodigoPrestador() { return codigoPrestador; }
    public void setCodigoPrestador(Long codigoPrestador) { this.codigoPrestador = codigoPrestador; }
    public List<String> getEspecialidades() { return especialidades; }
    public void setEspecialidades(List<String> especialidades) { this.especialidades = especialidades; }
    public String getObservacoes() { return observacoes; }
    public void setObservacoes(String observacoes) { this.observacoes = observacoes; }
    public Date getDataAtualizacao() { return dataAtualizacao; }
    public void setDataAtualizacao(Date dataAtualizacao) { this.dataAtualizacao = dataAtualizacao; }
    public Date getDataInclusao() { return dataInclusao; }
    public void setDataInclusao(Date dataInclusao) { this.dataInclusao = dataInclusao; }
    public BigDecimal getRemuneracao() { return remuneracao; }
    public void setRemuneracao(BigDecimal remuneracao) { this.remuneracao = remuneracao; }
    public Boolean getAtivo() { return ativo; }
    public void setAtivo(Boolean ativo) { this.ativo = ativo; }

    @Override
    public String toString() {
        String base = super.toString();
        String remStr = remuneracao != null ? remuneracao.toString() : "0.00";
        String espStr = (especialidades != null && !especialidades.isEmpty()) ? String.join(",", especialidades) : "";
        String incStr = dataInclusao != null ? dataInclusao.toString() : "";
        String atuStr = dataAtualizacao != null ? dataAtualizacao.toString() : "";
        return String.format("%s | Código: %d | Remuneração: %s | Ativo: %s | Inclusão: %s | Atualização: %s | Especialidades: %s",
                base, codigoPrestador, remStr, (ativo != null && ativo ? "Sim" : "Não"), incStr, atuStr, espStr);
    }
}
