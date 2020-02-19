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
public class Aluno  implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	private String nome;
	
	private Integer numMatricula;
	
	private LocalDate aniversario;
	
	private Integer numNIS;
	
	private String nomeResponsavel;
	
	private String endereco;
	
	private String telefone;
	
	private LocalDate aniversarioResponsavel;
	
	private String rg;
	
	private String cpf;
	
	private String nomeMedico;
	
	private String crm;
	
	@ManyToOne
	@JoinColumn(name = "funcionario_id")
	private Funcionario funcionario;
	
	public Aluno() {
		// TODO Auto-generated constructor stub
	}

	public Aluno(Integer id, String nome, Integer numMatricula, LocalDate aniversario, Integer numNIS,
			String nomeResponsavel, String endereco, String telefone, LocalDate aniversarioResponsavel, String rg,
			String cpf, String nomeMedico, String crm, Funcionario funcionario) {
		super();
		this.id = id;
		this.nome = nome;
		this.numMatricula = numMatricula;
		this.aniversario = aniversario;
		this.numNIS = numNIS;
		this.nomeResponsavel = nomeResponsavel;
		this.endereco = endereco;
		this.telefone = telefone;
		this.aniversarioResponsavel = aniversarioResponsavel;
		this.rg = rg;
		this.cpf = cpf;
		this.nomeMedico = nomeMedico;
		this.crm = crm;
		this.funcionario = funcionario;
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

	public Integer getNumMatricula() {
		return numMatricula;
	}

	public void setNumMatricula(Integer numMatricula) {
		this.numMatricula = numMatricula;
	}

	public LocalDate getAniversario() {
		return aniversario;
	}

	public void setAniversario(LocalDate aniversario) {
		this.aniversario = aniversario;
	}

	public Integer getNumNIS() {
		return numNIS;
	}

	public void setNumNIS(Integer numNIS) {
		this.numNIS = numNIS;
	}

	public String getNomeResponsavel() {
		return nomeResponsavel;
	}

	public void setNomeResponsavel(String nomeResponsavel) {
		this.nomeResponsavel = nomeResponsavel;
	}

	public String getEndereco() {
		return endereco;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public LocalDate getAniversarioResponsavel() {
		return aniversarioResponsavel;
	}

	public void setAniversarioResponsavel(LocalDate aniversarioResponsavel) {
		this.aniversarioResponsavel = aniversarioResponsavel;
	}

	public String getRg() {
		return rg;
	}

	public void setRg(String rg) {
		this.rg = rg;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
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

	public Funcionario getFuncionario() {
		return funcionario;
	}

	public void setFuncionario(Funcionario funcionario) {
		this.funcionario = funcionario;
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
		Aluno other = (Aluno) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Aluno [id=" + id + ", nome=" + nome + ", numMatricula=" + numMatricula + ", aniversario=" + aniversario
				+ ", numNIS=" + numNIS + ", nomeResponsavel=" + nomeResponsavel + ", endereco=" + endereco
				+ ", telefone=" + telefone + ", aniversarioResponsavel=" + aniversarioResponsavel + ", rg=" + rg
				+ ", cpf=" + cpf + ", nomeMedico=" + nomeMedico + ", crm=" + crm + ", funcionario=" + funcionario + "]";
	}	
}
