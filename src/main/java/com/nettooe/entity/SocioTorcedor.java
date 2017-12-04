package com.nettooe.entity;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.swagger.annotations.ApiModel;

@Entity
@ApiModel(value = "SocioTorcedor", description = "Sócio Torcedor.")
public class SocioTorcedor implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	private Long id;

	@NotNull
	@NotEmpty
	private String email;

	@NotNull
	@NotEmpty
	private String nomeCompleto;

	@NotNull
	private LocalDate dataDeNascimento;

	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
	private TimeDoCoracao timeDoCoracao;

	@JsonIgnore
	@OneToMany(mappedBy = "socioTorcedor", cascade = CascadeType.ALL)
	private List<Associacao> associacoes = new ArrayList<Associacao>();

	public SocioTorcedor() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public TimeDoCoracao getTimeDoCoracao() {
		return timeDoCoracao;
	}

	public void setTimeDoCoracao(TimeDoCoracao timeDoCoracao) {
		this.timeDoCoracao = timeDoCoracao;
	}

	public List<Associacao> getAssociacoes() {
		if (associacoes == null) {
			this.associacoes = new ArrayList<Associacao>();
		}
		return associacoes;
	}

	public void setAssociacoes(List<Associacao> associacoes) {
		this.associacoes = associacoes;
	}

}