package br.edu.infnet.LeandroDrumondApi.model.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

public class DefinirValorEAceitarDTO {

    @NotNull(message = "O id do prestador é obrigatório.")
    private Integer prestadorId;

    @NotNull(message = "O valor do serviço é obrigatório.")
    @DecimalMin(value = "0.00", message = "O valor não pode ser negativo.")
    private BigDecimal valor;

    public Integer getPrestadorId() { return prestadorId; }
    public void setPrestadorId(Integer prestadorId) { this.prestadorId = prestadorId; }

    public BigDecimal getValor() { return valor; }
    public void setValor(BigDecimal valor) { this.valor = valor; }
}
