package br.edu.infnet.LeandroDrumondApi.model.dto;

import jakarta.validation.constraints.NotBlank;

public class CancelarOrdemDTO {

    @NotBlank(message = "O motivo do cancelamento é obrigatório.")
    private String motivo;

    public String getMotivo() { return motivo; }
    public void setMotivo(String motivo) { this.motivo = motivo; }
}
