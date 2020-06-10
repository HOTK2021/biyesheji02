package org.cqipc.edu.dao;

import org.apache.ibatis.annotations.Param;
import org.cqipc.edu.bean.T_user;

import java.util.List;

public interface T_userDao {
	public T_user userLogin(@Param("username")String username,@Param("password")String password);
	public List<T_user> selectUserAll();
	public int addUser(T_user atu);
}
