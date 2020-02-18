package com.ricdev.formulario.model.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;

import com.ricdev.formulario.connection.ConnectionFactory;
import com.ricdev.formulario.model.dao.FormularioMatriculaDAO;
import com.ricdev.formulario.model.entities.FormularioMatricula;


public class FormularioMatriculaDaoJDBC implements FormularioMatriculaDAO{

	//=============== insert ========================
	@Override
	public FormularioMatricula insert(FormularioMatricula obj) {
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
	public FormularioMatricula updateOrInsert(FormularioMatricula obj) {
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
	public FormularioMatricula deleteById(Integer id) {
		EntityManager manager = new ConnectionFactory().getConnection();
		FormularioMatricula obj = null;
		try {
			
			obj = manager.find(FormularioMatricula.class, id);
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
	public FormularioMatricula findById(Integer id) {
		EntityManager manager = new ConnectionFactory().getConnection();
		FormularioMatricula obj = null;
		
		try {
			
			obj = manager.find(FormularioMatricula.class, id);
			
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
	public List<FormularioMatricula> findAll() {
		EntityManager manager = new ConnectionFactory().getConnection();
		List<FormularioMatricula>FormularioMatriculas = null;
		try {
			
			FormularioMatriculas = manager.createQuery("from " + FormularioMatricula.class.getName()).getResultList();
			
		} catch (PersistenceException e) {
			System.err.println(e);
		}finally {
			manager.close();
		}
		return FormularioMatriculas;
	}
}
