package org.cqipc.edu.dao;

import org.apache.ibatis.annotations.Param;
import org.cqipc.edu.bean.T_user;

import java.math.BigInteger;
import java.util.List;

public interface T_userDao {
	public T_user userLogin(@Param("username")String username,@Param("password")String password);
	public List<T_user> selectUserAll(@Param("pageCount")int pageCount,
									  @Param("pageSize")int pageSize,
									  @Param("keyWord")String keyWord,
									  @Param("user_id")int user_id);
	public List<T_user> selectUserDieAll(@Param("pageCount")int pageCount,
										 @Param("pageSize")int pageSize);

	public int selectUserCount();
	public int selectUserDieCount();
	public int addUser(T_user atu);
	public int modifyUserInfo(@Param("username")String username,@Param("description")String description,@Param("age")int age,@Param("user_id")BigInteger user_id);
	public T_user selectToBeProcessed(@Param("user_id")BigInteger user_id);
	public int deleteProcessed(@Param("user_id") int user_id);
}
