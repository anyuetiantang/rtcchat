package com.rtcchat.daoImpl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.rtcchat.baseDao.BaseDaoImpl;
import com.rtcchat.dao.UserDao;
import com.rtcchat.entity.User;

@Repository("userDao")
public class UserDaoImpl extends BaseDaoImpl implements UserDao {

	@Override
	public List<User> findByUsernameOrPassword(String username,String password) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(User.class);
		criteria.add(Restrictions.eq("username", username));
		if(password != null){
			criteria.add(Restrictions.eq("password", password));
		}
		List<User> userList = criteria.list();
		return userList;
	}

}
