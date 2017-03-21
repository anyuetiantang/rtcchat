package com.rtcchat.baseDao;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;

@Repository("baseDao")
public class BaseDaoImpl implements BaseDao {
	private HibernateTemplate hibernateTemplate = null;
	private SessionFactory sessionFactory = null;
	
	@Override
	public <T> List<T> findByExample(T t) {
		return hibernateTemplate.findByExample(t);
	}

	@Override
	public List findByCriteria(Criteria criteria) {
		return criteria.list();
	}

	@Override
	public <T> T findById(Class<T> clazz, int id) {
		Session session = sessionFactory.getCurrentSession();
		T t = (T)session.get(clazz,id);
		return t;
	}

	@Override
	public <T> void save(T t) {
		hibernateTemplate.save(t);
	}

	@Override
	public <T> void deleteById(Class<T> clazz, int id) {
		hibernateTemplate.delete(this.findById(clazz, id));
		
	}

	@Override
	public <T> void update(T t) {
		hibernateTemplate.update(t);
	}

	/******************* Ù–‘get∑Ω∑®********************/
	@Resource
	public void setHibernateTemplate(HibernateTemplate hibernateTemplate) {
		this.hibernateTemplate = hibernateTemplate;
	}

	@Resource(name="sessionFactory")
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
}
