package com.ricdev.formulario.model.dao;

import com.ricdev.formulario.model.dao.impl.AlunoDaoJDBC;
import com.ricdev.formulario.model.dao.impl.FormularioMatriculaDaoJDBC;
import com.ricdev.formulario.model.dao.impl.FuncionarioDaoJDBC;
import com.ricdev.formulario.model.dao.impl.InstituicaoDaoJDBC;

public class DaoFactory {
	
	public static InstituicaoDAO createInstituicaoDAO() {
		return new InstituicaoDaoJDBC(); 
			
	}
	public static FuncionarioDAO createFuncionarioDAO() {
		return new FuncionarioDaoJDBC(); 
		
	}
	public static AlunoDAO createAlunoDAO() {
		// TODO Auto-generated method stub
		return new AlunoDaoJDBC();
	}
	public static FormularioMatriculaDAO createFormularioMatriculaDAO() {
		// TODO Auto-generated method stub
		return new FormularioMatriculaDaoJDBC();
	}	

}
