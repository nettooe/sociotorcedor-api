package com.nettooe.entity;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
public class Associacao implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	@JsonIgnore
	private Long idCampanha;

	@JsonIgnore
	@ManyToOne(cascade = CascadeType.ALL)
	private SocioTorcedor socioTorcedor;

	@Transient
	private String nomeDaCampanha;

	public Associacao() {
	}

	public Long getIdCampanha() {
		return idCampanha;
	}

	public void setIdCampanha(Long idCampanha) {
		this.idCampanha = idCampanha;
	}

	public SocioTorcedor getSocioTorcedor() {
		return socioTorcedor;
	}

	public void setSocioTorcedor(SocioTorcedor socioTorcedor) {
		this.socioTorcedor = socioTorcedor;
	}

	public String getNomeDaCampanha() {
		return nomeDaCampanha;
	}

	public void setNomeDaCampanha(String nomeDaCampanha) {
		this.nomeDaCampanha = nomeDaCampanha;
	}

}
