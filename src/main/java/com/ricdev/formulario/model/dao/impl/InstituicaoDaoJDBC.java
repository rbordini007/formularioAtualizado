package com.ricdev.formulario.model.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;

import com.ricdev.formulario.connection.ConnectionFactory;
import com.ricdev.formulario.connection.DbIntegrityException;
import com.ricdev.formulario.model.dao.InstituicaoDAO;
import com.ricdev.formulario.model.entities.Instituicao;

public class InstituicaoDaoJDBC implements InstituicaoDAO{

	@Override
	public Instituicao insert(Instituicao obj) {
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

	@Override
	public Instituicao updateOrInsert(Instituicao obj) {
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

	@Override
	public void deleteById(Integer id)  {
		EntityManager manager = new ConnectionFactory().getConnection();
		Instituicao obj = null;
		try {
			
			obj = manager.find(Instituicao.class, id);
			manager.getTransaction().begin();
			manager.remove(obj);
			manager.getTransaction().commit();
		
		} catch (PersistenceException e) {	
			//manager.getTransaction().rollback();
			//System.out.println(e);
			throw new DbIntegrityException(e.getMessage());			
			
		}finally {
			manager.close();
		}
		//return obj;
	}

	@Override
	public Instituicao findById(Integer id) {
		EntityManager manager = new ConnectionFactory().getConnection();
		Instituicao obj = null;
		
		try {
			
			obj = manager.find(Instituicao.class, id);
			
		} catch (PersistenceException e) {
			System.err.println(e);
		}finally {
			manager.close();
		}
		
		return obj;
	}

	@SuppressWarnings("unchecked")
	@Override	
	public List<Instituicao> findAll() {
		EntityManager manager = new ConnectionFactory().getConnection();
		List<Instituicao>instituicaos = null;
		try {
			
			instituicaos = manager.createQuery("from " + Instituicao.class.getName()).getResultList();
			
		} catch (PersistenceException e) {
			System.err.println(e);
		}finally {
			manager.close();
		}
		return instituicaos;
	}

}
