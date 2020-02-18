package com.ricdev.formulario.model.services;

import java.util.List;

import com.ricdev.formulario.model.dao.DaoFactory;
import com.ricdev.formulario.model.dao.FormularioMatriculaDAO;
import com.ricdev.formulario.model.entities.FormularioMatricula;

public class FormularioMatriculaService {
	
	private FormularioMatriculaDAO dao = DaoFactory.createFormularioMatriculaDAO();
	
	public List<FormularioMatricula> findAll(){		
		return dao.findAll();		
	}
	
	public void saveOrUpdate(FormularioMatricula obj) {
		dao.updateOrInsert(obj);
	}
	
	public void remove(FormularioMatricula obj) {
		dao.deleteById(obj.getId());
	}

}
