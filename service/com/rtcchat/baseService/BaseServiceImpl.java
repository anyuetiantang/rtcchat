package com.rtcchat.baseService;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Criteria;
import org.springframework.stereotype.Service;

import com.rtcchat.baseDao.BaseDao;

@Service("baseService")
public class BaseServiceImpl implements BaseService{
	private BaseDao baseDao = null;
	
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

	
	@Resource(name="baseDao")
	public void setBaseDao(BaseDao baseDao) {
		this.baseDao = baseDao;
	}
}
