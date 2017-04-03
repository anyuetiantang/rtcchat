package com.rtcchat.baseService;

import java.util.List;

import org.hibernate.Criteria;

public interface BaseService {

	//添加数据
	public <T> void save(T t);
	
	//删除数据
	public <T> void deleteById(Class<T> clazz, int id);
	
	//更新数据
	public <T> void update(T t);
	
	//根据id查询数据
	public <T> T findById(Class<T> clazz, int id);
	
	//根据条件查询数据
	public <T> List<T> findByCriteria(Criteria criteria);
	
	//查询数据
	public <T> List<T> findByExample(T t);
}
