package com.ricdev.formulario.model.dao;

import java.util.List;

import com.ricdev.formulario.model.entities.Instituicao;

public interface InstituicaoDAO {
	
	Instituicao insert(Instituicao obj);
	Instituicao updateOrInsert(Instituicao obj);	
	void deleteById(Integer id);
	
	Instituicao findById(Integer id);
	List<Instituicao> findAll();
}
