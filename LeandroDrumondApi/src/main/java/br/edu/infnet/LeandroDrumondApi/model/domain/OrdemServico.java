package br.edu.infnet.LeandroDrumondApi.model.domain;

import br.edu.infnet.LeandroDrumondApi.model.domain.enums.StatusOrdem;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "ordem_servico",
        uniqueConstraints = @UniqueConstraint(columnNames = "codigo_ordem"))
public class OrdemServico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Column(name = "codigo_ordem", unique = true)
    private Long codigoOrdem;

    @ManyToOne(optional = false)
    @JoinColumn(name = "cliente_id", nullable = false)
    private Cliente cliente;

    @ManyToOne
    @JoinColumn(name = "prestador_id")
    private PrestadorServico prestador;

    @NotBlank(message = "A categoria do serviço é obrigatória.")
    @Column(name = "categoria", length = 80, nullable = false)
    private String categoria;

    @NotBlank(message = "A descrição do serviço é obrigatória.")
    @Size(max = 1000, message = "A descrição deve ter no máximo 1000 caracteres.")
    @Column(name = "descricao", length = 1000, nullable = false)
    private String descricao;

    @DecimalMin(value = "0.00", message = "O valor não pode ser negativo.")
    @Column(name = "valor_definido")
    private BigDecimal valorDefinido;

    @DecimalMin(value = "0.00")
    @Column(name = "valor_prestador")
    private BigDecimal valorPrestador;

    @DecimalMin(value = "0.00")
    @Column(name = "valor_empresa")
    private BigDecimal valorEmpresa;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 20)
    private StatusOrdem status = StatusOrdem.CRIADA;

    @PastOrPresent
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "data_criacao", nullable = false)
    private Date dataCriacao;

    @PastOrPresent
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "data_atualizacao", nullable = false)
    private Date dataAtualizacao;

    @Size(max = 500)
    @Column(name = "motivo_cancelamento", length = 500)
    private String motivoCancelamento;

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public Long getCodigoOrdem() { return codigoOrdem; }
    public void setCodigoOrdem(Long codigoOrdem) { this.codigoOrdem = codigoOrdem; }

    public Cliente getCliente() { return cliente; }
    public void setCliente(Cliente cliente) { this.cliente = cliente; }

    public PrestadorServico getPrestador() { return prestador; }
    public void setPrestador(PrestadorServico prestador) { this.prestador = prestador; }

    public String getCategoria() { return categoria; }
    public void setCategoria(String categoria) { this.categoria = categoria; }

    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }

    public BigDecimal getValorDefinido() { return valorDefinido; }
    public void setValorDefinido(BigDecimal valorDefinido) { this.valorDefinido = valorDefinido; }

    public BigDecimal getValorPrestador() { return valorPrestador; }
    public void setValorPrestador(BigDecimal valorPrestador) { this.valorPrestador = valorPrestador; }

    public BigDecimal getValorEmpresa() { return valorEmpresa; }
    public void setValorEmpresa(BigDecimal valorEmpresa) { this.valorEmpresa = valorEmpresa; }

    public StatusOrdem getStatus() { return status; }
    public void setStatus(StatusOrdem status) { this.status = status; }

    public Date getDataCriacao() { return dataCriacao; }
    public void setDataCriacao(Date dataCriacao) { this.dataCriacao = dataCriacao; }

    public Date getDataAtualizacao() { return dataAtualizacao; }
    public void setDataAtualizacao(Date dataAtualizacao) { this.dataAtualizacao = dataAtualizacao; }

    public String getMotivoCancelamento() { return motivoCancelamento; }
    public void setMotivoCancelamento(String motivoCancelamento) { this.motivoCancelamento = motivoCancelamento; }
}
