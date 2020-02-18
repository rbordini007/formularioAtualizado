package com.ricdev.formulario.model.entities;

import java.io.Serializable;

import javax.persistence.Access;
import javax.persistence.AccessType;


public class FichaMedica implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private String nomeMedico;
	private String crm;
	
	public FichaMedica() {
		// TODO Auto-generated constructor stub
	}

	public FichaMedica(String nomeMedico, String crm) {
		super();
		this.nomeMedico = nomeMedico;
		this.crm = crm;
	}
	

	public String getNomeMedico() {
		return nomeMedico;
	}

	public void setNomeMedico(String nomeMedico) {
		this.nomeMedico = nomeMedico;
	}

	public String getCrm() {
		return crm;
	}

	public void setCrm(String crm) {
		this.crm = crm;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((crm == null) ? 0 : crm.hashCode());
		result = prime * result + ((nomeMedico == null) ? 0 : nomeMedico.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		FichaMedica other = (FichaMedica) obj;
		if (crm == null) {
			if (other.crm != null)
				return false;
		} else if (!crm.equals(other.crm))
			return false;
		if (nomeMedico == null) {
			if (other.nomeMedico != null)
				return false;
		} else if (!nomeMedico.equals(other.nomeMedico))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "FichaMedica [nomeMedico=" + nomeMedico + ", crm=" + crm + "]";
	}

	
	
	

}
