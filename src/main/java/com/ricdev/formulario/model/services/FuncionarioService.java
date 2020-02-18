package com.ricdev.formulario.model.services;

import java.util.List;

import com.ricdev.formulario.model.dao.DaoFactory;
import com.ricdev.formulario.model.dao.FuncionarioDAO;
import com.ricdev.formulario.model.entities.Funcionario;

public class FuncionarioService {
	
	private FuncionarioDAO dao = DaoFactory.createFuncionarioDAO();
	
	public List<Funcionario> findAll(){		
		return dao.findAll();		
	}
	
	public void saveOrUpdate(Funcionario obj) {
		dao.updateOrInsert(obj);
	}
	
	public void remove(Funcionario obj) {
		dao.deleteById(obj.getId());
	}

}
