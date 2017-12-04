package com.nettooe.vo;

import java.io.Serializable;
import java.time.LocalDate;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

public class CadastroSocioTorcedor implements Serializable {
	private static final long serialVersionUID = 1L;

	@NotNull
	@NotEmpty
	private String email;

	@NotNull
	@NotEmpty
	private String nomeCompleto;

	@NotNull
	private LocalDate dataDeNascimento;

	@NotNull
	@NotEmpty
	private String meuTimeDoCoracao;

	public CadastroSocioTorcedor() {
	}

	public CadastroSocioTorcedor(Long id, String nomeCompleto, String email, LocalDate dataDeNascimento,
			String meuTimeDoCoracao) {
		super();
		this.nomeCompleto = nomeCompleto;
		this.email = email;
		this.dataDeNascimento = dataDeNascimento;
		this.meuTimeDoCoracao = meuTimeDoCoracao;
	}

	public String getNomeCompleto() {
		return nomeCompleto;
	}

	public void setNomeCompleto(String nomeCompleto) {
		this.nomeCompleto = nomeCompleto;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public LocalDate getDataDeNascimento() {
		return dataDeNascimento;
	}

	public void setDataDeNascimento(LocalDate dataDeNascimento) {
		this.dataDeNascimento = dataDeNascimento;
	}

	public String getMeuTimeDoCoracao() {
		return meuTimeDoCoracao;
	}

	public void setMeuTimeDoCoracao(String meuTimeDoCoracao) {
		this.meuTimeDoCoracao = meuTimeDoCoracao;
	}

}