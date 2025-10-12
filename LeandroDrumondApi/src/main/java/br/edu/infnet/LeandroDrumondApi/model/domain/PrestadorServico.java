package br.edu.infnet.LeandroDrumondApi.model.domain;

import java.sql.Date;
import java.util.List;

public class PrestadorServico {
	
	private String nome;
	private String documento;
	private List<String> especialidades;
	private String email;
	private String telefone;
	private String endereco;
	private String observacoes;
	private Date dataAtualizacao;
	private Date dataInclusao;
	private long codigoPrestador;
	private double remuneracao;
	private boolean ativo;
	
	
	@Override
	public String toString() {
		
		return "";
	}
	
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getDocumento() {
		return documento;
	}
	public void setDocumento(String documento) {
		this.documento = documento;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getTelefone() {
		return telefone;
	}
	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}
	public String getEndereco() {
		return endereco;
	}
	public void setEndereco(String endereco) {
		this.endereco = endereco;
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
	public double getRemuneracao() {
		return remuneracao;
	}
	public void setRemuneracao(double remuneracao) {
		this.remuneracao = remuneracao;
	}
	public boolean isAtivo() {
		return ativo;
	}
	public void setAtivo(boolean ativo) {
		this.ativo = ativo;
	}


}
