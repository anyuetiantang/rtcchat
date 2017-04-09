package com.rtcchat.serviceImpl;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Service;

import com.rtcchat.baseDao.BaseDao;
import com.rtcchat.baseService.BaseService;

@Service("baseService")
public class BaseServiceImpl implements BaseService{
	private BaseDao baseDao = null;
	protected SessionFactory sessionFactory = null;
	
	@Override
	public void clear() {
		baseDao.clear();
	}
	
	@Override
	public <T> void save(T t) {
		baseDao.save(t);
	}

	@Override
	public <T> void deleteById(Class<T> clazz, int id) {
		baseDao.deleteById(clazz, id);
	}

	@Override
	public <T> void update(T t) {
		baseDao.update(t);
	}

	@Override
	public <T> T findById(Class<T> clazz, int id) {
		T t = (T)baseDao.findById(clazz, id);
		return t;
	}

	@Override
	public <T> List<T> findByCriteria(Criteria criteria) {
		List<T> list = baseDao.findByCriteria(criteria);
		return list;
	}
	
	@Override
	public <T> List<T> findByExample(T t) {
		List<T> list = baseDao.findByExample(t);
		return list;
	}

	
	@Resource(name="baseDao")
	public void setBaseDao(BaseDao baseDao) {
		this.baseDao = baseDao;
	}

	@Resource(name="sessionFactory")
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

}
