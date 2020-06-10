package org.cqipc.edu.service;

import org.cqipc.edu.bean.T_user;

import java.util.List;

public interface T_userService {
	public Object[] Login(String username,String password);
	public List<T_user> selectUserAll();
	public int addUser(T_user atu);
}
