package com.ricdev.formulario.model.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;

import com.ricdev.formulario.connection.ConnectionFactory;
import com.ricdev.formulario.model.dao.AlunoDAO;
import com.ricdev.formulario.model.entities.Aluno;


public class AlunoDaoJDBC implements AlunoDAO{

	//=============== insert ========================
	@Override
	public Aluno insert(Aluno obj) {
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
	public Aluno updateOrInsert(Aluno obj) {
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
	public Aluno deleteById(Integer id) {
		EntityManager manager = new ConnectionFactory().getConnection();
		Aluno obj = null;
		try {
			
			obj = manager.find(Aluno.class, id);
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
	public Aluno findById(Integer id) {
		EntityManager manager = new ConnectionFactory().getConnection();
		Aluno obj = null;
		
		try {
			
			obj = manager.find(Aluno.class, id);
			
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
	public List<Aluno> findAll() {
		EntityManager manager = new ConnectionFactory().getConnection();
		List<Aluno> list = null;
		try {
			
			list = manager.createQuery("from " + Aluno.class.getName()).getResultList();
			
		} catch (PersistenceException e) {
			System.err.println(e);
		}finally {
			manager.close();
		}
		return list;
	}
}
