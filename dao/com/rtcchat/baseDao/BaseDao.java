package com.rtcchat.baseDao;

import java.util.List;

import org.hibernate.Criteria;

public interface BaseDao {
	//�������
	public void clear();
	
	//���ݴ���Ķ�����ҷ��ϵĶ���ʹ��hibernateTemplateʵ�֣�
	public <T> List<T> findByExample(T t);
	
	//���ݴ���Ķ�����ҷ��ϵĶ���ʹ��hibernateTemplateʵ�֣�
	public <T> List<T> findByExample(T t,int maxNumber);
	
	//����criteria������ѯ
	public List findByCriteria(Criteria criteria);
	
	//����id��ѯ
	public <T> T findById(Class<T> clazz,int id);
	
	//����hql��ѯ
	public <T> List<T> findByHql(Class<T> clazz,String hql);
	
	//����hql��ѯ
	public <T> List<T> findByHql(Class<T> clazz,int maxNumber,String hql);
	
	//���
	public <T> void save(T t);
	
	//ɾ����ע��˴������id���������TӦ����ͬһ����
	public <T> void deleteById(Class<T> clazz,int id);
	
	//�޸�
	public <T> void update(T t);
	
	//�޸�
	public <T> void merge(T t);
}
