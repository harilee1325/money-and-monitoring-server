package com.example.social_bank.demo.util;

import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

@Component
public class JpaConn {
	private EntityManager em;

	public EntityManager getEntityManager() {
		if (em == null) {
			EntityManagerFactory emf = Persistence.createEntityManagerFactory("mysql");
			em = emf.createEntityManager();
		}
		return em;
	}

}
