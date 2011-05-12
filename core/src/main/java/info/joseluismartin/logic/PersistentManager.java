/*
 * Copyright 2002-2010 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package info.joseluismartin.logic;

import info.joseluismartin.dao.Page;
import info.joseluismartin.dao.hibernate.HibernateDao;
import info.joseluismartin.service.PersistentService;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;



/**
 * Base class for Services that wrappers persistent operations to DAO.
 * 
 * @author Jose Luis Martin - (jlm@joseluismartin.info)
 */
public  class PersistentManager<T, PK extends Serializable> implements PersistentService<T, PK> {
	
	private final static Log log = LogFactory.getLog(PersistentManager.class);
	
	public static final int DEFAULT_DEPTH = 2;
	protected HibernateDao<T, PK> dao;

	/**
	 * {@inheritDoc}
	 */
	public void delete(T entity) {
		dao.delete(entity);
	}


	/**
	 * {@inheritDoc}
	 */
	public T initialize(T entity, int depth) {
		dao.initialize(entity, depth);
		return entity;
	}
	

	/**
	 * {@inheritDoc}
	 */
	public T initialize(T entity) {
		dao.initialize(entity);
		return entity;
	}
	

	/**
	 * {@inheritDoc}
	 */
	public List<T> getAll() {
		return dao.getAll();
	}



	/**
	 * {@inheritDoc}
	 */
	public T save(T entity) {
		return dao.save(entity);
	}

	/**
	 * {@inheritDoc}
	 */
	public void delete(Collection<T> collection) {
		for (T t : collection) {
			dao.delete(t);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("unchecked")
	public Collection<T> save(Collection<T> collection) {
		Collection<T> saved = null; 
		try {
			saved = collection.getClass().newInstance();
		} 
		catch (Exception e) {
			log.error(e);
			return saved;
		}
		
		for (T t : collection) {
			saved.add(dao.save(t));
		}
		
		return saved;
	}

	/**
	 * {@inheritDoc}
	 */
	public void deleteById(PK id) {
		dao.deleteById(id);
		
	}

	/**
	 * {@inheritDoc}
	 */
	public void deleteById(Collection<PK> ids) {
		for (PK id : ids) 
			dao.deleteById(id);
		
	}

	/**
	 * {@inheritDoc}
	 */
	public List<Serializable> getKeys(Page<T> page) {
		return dao.getKeys(page);
	}

	/**
	 * {@inheritDoc}
	 */
	public Page<T> getPage(Page<T> page) {
		return dao.getPage(page);
	}
	
	public HibernateDao<T, PK> getDao() {
		return dao;
	}

	public void setDao(HibernateDao<T, PK> dao) {
		this.dao = dao;
	}
}