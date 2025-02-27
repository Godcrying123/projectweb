package com.project.webapp.controller;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.project.webapp.dao.Datadao;
import com.project.webapp.domain.User;

public class UserDao implements UserService {

	private Datadao datadao;
	private EntityManager em;

	public UserDao() {
		this.em = datadao.getEm();
	}

	public UserDao(EntityManager em) {
		this.em = em;
	}

	public void save(Object object) {
		em.getTransaction().begin();
		em.persist(object);
		em.getTransaction().commit();
	}

	public void remove(Object object) {
		em.getTransaction().begin();
		em.remove(object);
		em.getTransaction().commit();
	}

	public List<Object> getAllUsers() {
		List<Object> objectList = new ArrayList<Object>();
		Query query = this.em.createQuery("from User");
		objectList = query.getResultList();
		return objectList;
	}

	public void update(Object object) {
		this.em.getTransaction().begin();
		this.em.persist(object);
		this.em.getTransaction().commit();
	}

	public Object search(String email) {
		Query query = this.em.createQuery("from User as user where user.emailaddress = ?");
		query.setParameter(0, email);
		try {
			Object object = (Object) query.getSingleResult();
			return object;
		} catch (javax.persistence.NoResultException e) {
			return null;
		}
	}

	public User search(int id) {
		Query query = this.em.createQuery("from User as user where user.user_id = ?");
		query.setParameter(0, id);
		try {
			User object = (User) query.getSingleResult();
			return object;
		} catch (javax.persistence.NoResultException e) {
			return null;
		}
	}

	public boolean validate(String username, String emailaddress, String password) {
		Query query = this.em
				.createQuery("from User as user where user.username=? and user.emailaddress=? and user.password=?");
		query.setParameter(0, username);
		query.setParameter(1, emailaddress);
		query.setParameter(2, password);
		try {
			Object object = (Object) query.getSingleResult();
			return true;
		} catch (javax.persistence.NoResultException e) {
			System.out.println("false");
			return false;
		}
	}

}
