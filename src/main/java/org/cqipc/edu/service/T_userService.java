package org.cqipc.edu.service;

import org.apache.ibatis.annotations.Param;
import org.cqipc.edu.bean.T_mingjie_lifeanddie;
import org.cqipc.edu.bean.T_mingjie_trial;
import org.cqipc.edu.bean.T_user;
import org.cqipc.edu.bean.T_user_s;

import java.math.BigInteger;
import java.util.List;

public interface T_userService {
	public Object[] Login(String username,String password);
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
	public int addTMLAD(T_mingjie_lifeanddie tmld);
	public List<T_mingjie_lifeanddie> selectOver();
	public int selectLifeAndDieCount();
	public T_user selectToBeProcessed(@Param("user_id")BigInteger user_id);
	public int deleteProcessed(@Param("user_id") int user_id);
	public int modifyPS(@Param("userId")int userId);
	public int addTMTD(@Param("userId")int userId);
	public List<T_user_s> selectNotpproved();
	//加入死表时，修改死亡时间
	public int dieTime(@Param("create_time")String create_time);
	//添加审判描述
	public int notTrial(@Param("info")String info);

}
