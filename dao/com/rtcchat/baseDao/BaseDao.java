package com.rtcchat.baseDao;

import java.util.List;

import org.hibernate.Criteria;

public interface BaseDao {
	
	//���ݴ���Ķ�����ҷ��ϵĶ���ʹ��hibernateTemplateʵ�֣�
	public <T> List<T> findByExample(T t);
	
	//����criteria������ѯ
	public List findByCriteria(Criteria criteria);
	
	//����id��ѯ
	public <T> T findById(Class<T> clazz,int id);
	
	//���
	public <T> void save(T t);
	
	//ɾ����ע��˴������id���������TӦ����ͬһ����
	public <T> void deleteById(Class<T> clazz,int id);
	
	//�޸�,ע��˴������id���������TӦ����ͬһ����
	public <T> void update(T t);
}
