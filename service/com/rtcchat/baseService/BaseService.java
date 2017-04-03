package com.rtcchat.baseService;

import java.util.List;

import org.hibernate.Criteria;

public interface BaseService {

	//�������
	public <T> void save(T t);
	
	//ɾ������
	public <T> void deleteById(Class<T> clazz, int id);
	
	//��������
	public <T> void update(T t);
	
	//����id��ѯ����
	public <T> T findById(Class<T> clazz, int id);
	
	//����������ѯ����
	public <T> List<T> findByCriteria(Criteria criteria);
	
	//��ѯ����
	public <T> List<T> findByExample(T t);
}
