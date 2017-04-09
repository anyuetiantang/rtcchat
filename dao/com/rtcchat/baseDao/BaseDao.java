package com.rtcchat.baseDao;

import java.util.List;

import org.hibernate.Criteria;

public interface BaseDao {
	//清除缓存
	public void clear();
	
	//根据传入的对象查找符合的对象（使用hibernateTemplate实现）
	public <T> List<T> findByExample(T t);
	
	//根据criteria条件查询
	public List findByCriteria(Criteria criteria);
	
	//根据id查询
	public <T> T findById(Class<T> clazz,int id);
	
	//根据id查询
	public <T> List<T> findByHql(Class<T> clazz,String hql);
	
	//添加
	public <T> void save(T t);
	
	//删除，注意此处传入的id所属的类和T应该是同一个类
	public <T> void deleteById(Class<T> clazz,int id);
	
	//修改
	public <T> void update(T t);
	
	//修改
	public <T> void merge(T t);
}
