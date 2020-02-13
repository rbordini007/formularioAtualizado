package com.ricdev.formulario.model.services;

import java.util.List;

import com.ricdev.formulario.model.dao.DaoFactory;
import com.ricdev.formulario.model.dao.InstituicaoDAO;
import com.ricdev.formulario.model.entities.Instituicao;

public class InstituicaoService {
	
	private InstituicaoDAO dao = DaoFactory.createInstituicaoDAO();
	
	public List<Instituicao> findAll(){		
		return dao.findAll();		
	}
	
	public void saveOrUpdate(Instituicao obj) {
		dao.updateOrInsert(obj);
	}
	
	public void remove(Instituicao obj) {
		dao.deleteById(obj.getId());
	}

}
