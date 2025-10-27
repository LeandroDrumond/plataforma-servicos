package br.edu.infnet.LeandroDrumondApi.model.domain;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "prestador_servico")
public class PrestadorServico extends Pessoa {

    @Column(name = "codigo_prestador", nullable = false, unique = true)
    private Long codigoPrestador;

    @ElementCollection
    @CollectionTable(
        name = "prestador_especialidade",
            joinColumns = @JoinColumn(name = "prestador_id")
    )
    @Column(name = "especialidade")
    private List<String> especialidades;

    private String observacoes;

    @Temporal(TemporalType.TIMESTAMP)
    private Date dataAtualizacao;

    @Temporal(TemporalType.TIMESTAMP)
    private Date dataInclusao;

    private BigDecimal remuneracao;

    private Boolean ativo;

    @Override
    public String toString() {
        String base = super.toString();
        String remStr = remuneracao != null ? remuneracao.toString() : "0.00";
        String espStr = (especialidades != null && !especialidades.isEmpty()) ? String.join(",", especialidades) : "";
        String incStr = dataInclusao != null ? dataInclusao.toString() : "";
        String atuStr = dataAtualizacao != null ? dataAtualizacao.toString() : "";

        return String.format(
            "%s | Código: %d | Remuneração: %s | Ativo: %s | Inclusão: %s | Atualização: %s | Especialidades: %s",
            base, codigoPrestador, remStr, (ativo ? "Sim" : "Não"), incStr, atuStr, espStr
        );
    }

    public String getObservacoes() {
        return observacoes;
    }
    public void setObservacoes(String observacoes) {
        this.observacoes = observacoes;
    }
    public Date getDataAtualizacao() {
        return dataAtualizacao;
    }
    public void setDataAtualizacao(Date dataAtualizacao) {
        this.dataAtualizacao = dataAtualizacao;
    }
    public Date getDataInclusao() {
        return dataInclusao;
    }
    public void setDataInclusao(Date dataInclusao) {
        this.dataInclusao = dataInclusao;
    }
    public Long getCodigoPrestador() {
        return codigoPrestador;
    }
    public void setCodigoPrestador(Long codigoPrestador) {
        this.codigoPrestador = codigoPrestador;
    }
    public Boolean isAtivo() {
        return ativo;
    }
    public void setAtivo(Boolean  ativo) {
        this.ativo = ativo;
    }
    public Boolean getAtivo() {return ativo;}
    public List<String> getEspecialidades() {
        return especialidades;
    }
    public void setEspecialidades(List<String> especialidades) {
        this.especialidades = especialidades;
    }
    public BigDecimal getRemuneracao() {
        return remuneracao;
    }
    public void setRemuneracao(BigDecimal remuneracao) {
        this.remuneracao = remuneracao;
    }
}
