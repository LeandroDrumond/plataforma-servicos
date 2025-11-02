package br.edu.infnet.LeandroDrumondApi.model.dto;

import jakarta.validation.constraints.*;

public class CriarOrdemServicoDTO {

    @NotNull(message = "O id do cliente é obrigatório.")
    private Integer clienteId;

    @NotBlank(message = "A categoria é obrigatória (ex.: Encanador).")
    private String categoria;

    @NotBlank(message = "A descrição do serviço é obrigatória.")
    @Size(max = 1000)
    private String descricao;

    public Integer getClienteId() { return clienteId; }
    public void setClienteId(Integer clienteId) { this.clienteId = clienteId; }

    public String getCategoria() { return categoria; }
    public void setCategoria(String categoria) { this.categoria = categoria; }

    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }
}
