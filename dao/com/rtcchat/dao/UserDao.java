package com.rtcchat.dao;

import java.util.List;

import com.rtcchat.baseDao.BaseDao;
import com.rtcchat.entity.User;

public interface UserDao extends BaseDao{
	public List<User> findByUsernameOrPassword(String username,String password);
}
