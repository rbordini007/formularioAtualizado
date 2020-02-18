package com.ricdev.formulario.model.dao;

import java.util.List;

import com.ricdev.formulario.model.entities.FormularioMatricula;


public interface FormularioMatriculaDAO {
	FormularioMatricula insert(FormularioMatricula obj);
	FormularioMatricula updateOrInsert(FormularioMatricula obj);
	FormularioMatricula deleteById(Integer id);
	//void deleteById(Integer id);
	
	FormularioMatricula findById(Integer id);
	List<FormularioMatricula> findAll();
	
}
