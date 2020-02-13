package com.ricdev.formulario.model.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;

import com.ricdev.formulario.connection.ConnectionFactory;
import com.ricdev.formulario.model.dao.FuncionarioDAO;
import com.ricdev.formulario.model.entities.Funcionario;


public class FuncionarioDaoJDBC implements FuncionarioDAO{

	//=============== insert ========================
	@Override
	public Funcionario insert(Funcionario obj) {
		EntityManager manager = new ConnectionFactory().getConnection();
		try {
			manager.getTransaction().begin();
			manager.persist(obj);
			manager.getTransaction().commit();
			
		} catch (PersistenceException e) {
			manager.getTransaction().rollback();
			System.err.println(e);
		}finally {
			manager.close();
		}
		return obj;
	}
	
	//================= updateOrInsert ========================
	@Override
	public Funcionario updateOrInsert(Funcionario obj) {
		EntityManager manager = new ConnectionFactory().getConnection();
		try {
			manager.getTransaction().begin();
			if (obj.getId() == null) {
				manager.persist(obj);
			}else {
				manager.merge(obj);
			}			
			manager.getTransaction().commit();
			
		} catch (PersistenceException e) {
			manager.getTransaction().rollback();
			System.err.println(e);
		}finally {
			manager.close();
		}
		return obj;
	}

	//=================== deletById =========================
	@Override
	public Funcionario deleteById(Integer id) {
		EntityManager manager = new ConnectionFactory().getConnection();
		Funcionario obj = null;
		try {
			
			obj = manager.find(Funcionario.class, id);
			manager.getTransaction().begin();
			manager.remove(obj);
			manager.getTransaction().commit();
		
		} catch (PersistenceException e) {
			manager.getTransaction().rollback();
			System.err.println(e);
		}finally {
			manager.close();
		}
		return obj;
	}
	
	//====================== findById =========================
	@Override
	public Funcionario findById(Integer id) {
		EntityManager manager = new ConnectionFactory().getConnection();
		Funcionario obj = null;
		
		try {
			
			obj = manager.find(Funcionario.class, id);
			
		} catch (PersistenceException e) {
			System.err.println(e);
		}finally {
			manager.close();
		}		
		return obj;
	}
	
	//================== findAll ===========================
	@SuppressWarnings("unchecked")
	@Override	
	public List<Funcionario> findAll() {
		EntityManager manager = new ConnectionFactory().getConnection();
		List<Funcionario>Funcionarios = null;
		try {
			
			Funcionarios = manager.createQuery("from " + Funcionario.class.getName()).getResultList();
			
		} catch (PersistenceException e) {
			System.err.println(e);
		}finally {
			manager.close();
		}
		return Funcionarios;
	}
}
