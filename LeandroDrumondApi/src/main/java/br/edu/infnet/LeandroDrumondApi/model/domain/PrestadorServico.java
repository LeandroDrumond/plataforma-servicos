package br.edu.infnet.LeandroDrumondApi.model.domain;


import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class PrestadorServico extends Pessoa {
	

	private List<String> especialidades;
	private String observacoes;
	private Date dataAtualizacao;
	private Date dataInclusao;
	private long codigoPrestador;
	private BigDecimal remuneracao;
	private boolean ativo;
	
	
	public String toString() {
        
        String base = super.toString();

        String remStr = remuneracao != null ? remuneracao.toString() : "0.00";
        String espStr = (especialidades != null && !especialidades.isEmpty())
                ? String.join(",", especialidades) : "";
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
	public long getCodigoPrestador() {
		return codigoPrestador;
	}
	public void setCodigoPrestador(long codigoPrestador) {
		this.codigoPrestador = codigoPrestador;
	}
	
	public boolean isAtivo() {
		return ativo;
	}
	public void setAtivo(boolean ativo) {
		this.ativo = ativo;
	}

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
