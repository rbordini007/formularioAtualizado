package com.ricdev.formulario.model.dao;

import java.util.List;

import com.ricdev.formulario.model.entities.Funcionario;


public interface FuncionarioDAO {
	Funcionario insert(Funcionario obj);
	Funcionario updateOrInsert(Funcionario obj);
	Funcionario deleteById(Integer id);
	//void deleteById(Integer id);
	
	Funcionario findById(Integer id);
	List<Funcionario> findAll();
}
