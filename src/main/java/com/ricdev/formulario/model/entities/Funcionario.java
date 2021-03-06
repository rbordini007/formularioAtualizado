package com.ricdev.formulario.model.entities;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Funcionario implements Serializable{	
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	private String nome;
	
	private String telefone;	
	
	private LocalDate aniversario;
		
	private Double salario;
	
	@ManyToOne
	@JoinColumn(name = "instituicao_id")
	private Instituicao instituicao;

	public Funcionario() {
		// TODO Auto-generated constructor stub
	}
	
	public Funcionario(Integer id, String nome, String telefone, LocalDate aniversario, Double salario,
			Instituicao instituicao) {
		this.id = id;
		this.nome = nome;
		this.telefone = telefone;
		this.aniversario = aniversario;
		this.salario = salario;
		this.instituicao = instituicao;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}		
	
	public LocalDate getAniversario() {	
		return aniversario;
	}
	
	public void setAniversario(LocalDate aniversario) {
		
		this.aniversario = aniversario;
	}
	
	public Double getSalario() {
		return salario;
	}
	
	public void setSalario(Double salario) {
		this.salario = salario;
	}
	
	public Instituicao getInstituicao() {
		return instituicao;
	}

	public void setInstituicao(Instituicao instituicao) {
		this.instituicao = instituicao;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		Funcionario other = (Funcionario) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	
	
	@Override
	public String toString() {		
		return "Funcionario [id=" + id + ", nome=" + nome + ", telefone=" + telefone + ", aniversario=" + aniversario
				+ ", salario=" + salario + ", instituicao=" + instituicao + "]";
	}	
}
