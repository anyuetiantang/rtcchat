package com.rtcchat.daoImpl;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;

import com.rtcchat.baseDao.BaseDao;

@Repository("baseDao")
public class BaseDaoImpl implements BaseDao {
	protected HibernateTemplate hibernateTemplate = null;
	protected SessionFactory sessionFactory = null;
	
	@Override
	public void clear() {
		hibernateTemplate.clear();
	}
	
	@Override
	public <T> List<T> findByExample(T t) {
		return (List<T>)hibernateTemplate.findByExample(t);
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
	public <T> List<T> findByHql(Class<T> clazz, String hql) {
		List<T> list = sessionFactory.getCurrentSession().createQuery(hql).list();
		return list;
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
	
	@Override
	public <T> void merge(T t) {
		hibernateTemplate.merge(t);
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
