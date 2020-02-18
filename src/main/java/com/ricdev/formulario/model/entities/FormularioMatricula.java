package com.ricdev.formulario.model.entities;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class FormularioMatricula implements Serializable {
	
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	private Integer numMatricula;
	private Integer numNIS;
	
	public FormularioMatricula() {
		// TODO Auto-generated constructor stub
	}

	public FormularioMatricula(Integer id, Integer numMatricula, Integer numNIS) {
		super();
		this.id = id;
		this.numMatricula = numMatricula;
		this.numNIS = numNIS;
	}
	
	

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getNumMatricula() {
		return numMatricula;
	}

	public void setNumMatricula(Integer numMatricula) {
		this.numMatricula = numMatricula;
	}

	public Integer getNumNIS() {
		return numNIS;
	}

	public void setNumNIS(Integer numNIS) {
		this.numNIS = numNIS;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((numMatricula == null) ? 0 : numMatricula.hashCode());
		result = prime * result + ((numNIS == null) ? 0 : numNIS.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		FormularioMatricula other = (FormularioMatricula) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (numMatricula == null) {
			if (other.numMatricula != null)
				return false;
		} else if (!numMatricula.equals(other.numMatricula))
			return false;
		if (numNIS == null) {
			if (other.numNIS != null)
				return false;
		} else if (!numNIS.equals(other.numNIS))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "FormularioMatricula [id=" + id + ", numMatricula=" + numMatricula + ", numNIS=" + numNIS + "]";
	}
	
	
	
}
