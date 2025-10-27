package br.edu.infnet.LeandroDrumondApi.model.domain;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "ordem_servico")
public class OrdemServico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Gerar Codigo futuramento como faço com codigo prestador.
    @Column(name = "codigo_os", unique = true)
    private Long codigoOs;

    @Column(name = "tipo_servico", nullable = false, length = 80)
    private String tipoServico;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "data_atendimento", nullable = false)
    private Date dataAtendimento;

    @Embedded
    private Endereco enderecoAtendimento;

    // Enquanto não mapeamos Cliente como entidade, mantenho como id simples
    @Column(name = "cliente_id", nullable = false)
    private Long clienteId;

    @ManyToOne
    @JoinColumn(name = "prestador_id")
    private PrestadorServico prestador;

    @Column(name = "valor_total", precision = 14, scale = 2)
    private BigDecimal valorTotal;

    @Column(name = "valor_prestador", precision = 14, scale = 2)
    private BigDecimal valorPrestador; // 90%

    @Column(name = "valor_empresa", precision = 14, scale = 2)
    private BigDecimal valorEmpresa;   // 10%

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 20)
    private StatusOrdem status;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "data_criacao", nullable = false)
    private Date dataCriacao;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "data_atualizacao", nullable = false)
    private Date dataAtualizacao;

    // Getters/Setters
    public Long getId() { return id; }

    public Long getCodigoOs() { return codigoOs; }
    public void setCodigoOs(Long codigoOs) { this.codigoOs = codigoOs; }

    public String getTipoServico() { return tipoServico; }
    public void setTipoServico(String tipoServico) { this.tipoServico = tipoServico; }

    public Date getDataAtendimento() { return dataAtendimento; }
    public void setDataAtendimento(Date dataAtendimento) { this.dataAtendimento = dataAtendimento; }

    public Endereco getEnderecoAtendimento() { return enderecoAtendimento; }
    public void setEnderecoAtendimento(Endereco enderecoAtendimento) { this.enderecoAtendimento = enderecoAtendimento; }

    public Long getClienteId() { return clienteId; }
    public void setClienteId(Long clienteId) { this.clienteId = clienteId; }

    public PrestadorServico getPrestador() { return prestador; }
    public void setPrestador(PrestadorServico prestador) { this.prestador = prestador; }

    public BigDecimal getValorTotal() { return valorTotal; }
    public void setValorTotal(BigDecimal valorTotal) { this.valorTotal = valorTotal; }

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
}
