package com.ricdev.formulario.model.dao;

import java.util.List;

import com.ricdev.formulario.model.entities.Aluno;


public interface AlunoDAO {
	Aluno insert(Aluno obj);
	Aluno updateOrInsert(Aluno obj);
	Aluno deleteById(Integer id);
	//void deleteById(Integer id);
	
	Aluno findById(Integer id);
	List<Aluno> findAll();
}
