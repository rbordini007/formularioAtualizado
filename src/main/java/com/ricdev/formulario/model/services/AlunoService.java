package com.ricdev.formulario.model.services;

import java.util.List;

import com.ricdev.formulario.model.dao.DaoFactory;
import com.ricdev.formulario.model.dao.AlunoDAO;
import com.ricdev.formulario.model.entities.Aluno;

public class AlunoService {
	
	private AlunoDAO dao = DaoFactory.createAlunoDAO();
	
	public List<Aluno> findAll(){		
		return dao.findAll();		
	}
	
	public void saveOrUpdate(Aluno obj) {
		dao.updateOrInsert(obj);
	}
	
	public void remove(Aluno obj) {
		dao.deleteById(obj.getId());
	}

}
