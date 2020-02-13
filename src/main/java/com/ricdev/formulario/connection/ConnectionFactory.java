package com.ricdev.formulario.connection;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class ConnectionFactory {	
	
	private static EntityManagerFactory factory = Persistence.createEntityManagerFactory("dev-up");
	
	public EntityManager getConnection() {		
		return factory.createEntityManager();
		
	}
	
	public void closeConnection(EntityManager entityManager, EntityManagerFactory factory) {
		if (entityManager != null) {
			entityManager.close();
		}
		if (factory != null) {
			factory.close();
		}		
	}

}
