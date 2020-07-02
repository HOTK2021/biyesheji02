package org.cqipc.edu.service;

import org.apache.ibatis.annotations.Param;
import org.cqipc.edu.bean.*;

import java.math.BigInteger;
import java.util.List;

public interface T_userService {
	public Object[] Login(String username,String password);
	public List<T_user> selectUserAll1(@Param("pageCount")int pageCount,
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
	public int notTrial(@Param("info")String info,@Param("trialTime")String trialTime,@Param("types")int types,@Param("userId")BigInteger userId);
	//两个方法，查询已审判的用户和记录
	public T_user_s selectDieUserTried(@Param("user_id")int user_id);
	public List<T_mingjie_trial> selectTried();
	//向孟婆表中添加等待分配的数据
	public int inserWaitSoul(@Param("userId")BigInteger userId, @Param("executorInfo")String executorInfo,
							 @Param("executorTime")String executorTime, @Param("executorStatus")int executorStatus);
	//查询待分配孟婆汤的用户
	public List<T_mingjie_soul> selectSoulDispensed();
	//已经分配孟婆汤
	public int distributeMPS(@Param("executorTime")String executorTime,@Param("userId")int userId);
	//没有分配孟婆汤
	public List<T_mingjie_soul> selectunallocatedMPS();
	//撤回已经分配的孟婆汤
	public int withdrawMPS(@Param("executorTime")String executorTime,@Param("userId")int userId);
	//查询18层地狱
	public List<T_mingjie_eighteen> selectMingJieEighteen();
	//查询等待分配地狱
	public List<T_mingjie_trial> waitingToAllocateHell();
	//查询正在受刑的人
	public List<T_mingjie_eighteen_log> selectTortured();
	//延迟出狱时间
	public int modifyLog(@Param("info")String info,@Param("imprisonmentTime")int imprisonmentTime,@Param("userId")BigInteger userId);
	//地狱记录
	public List<T_mingjie_eighteen_log> selectHellRecord();
	//分配地狱，添加到记录表
	public int addToAllocateHellResult(T_mingjie_eighteen_log t_mingjie_eighteen_log);
	//分配地狱后，修改审判状态，2表示已经分配地狱
	public int hellAssigned(@Param("userId")BigInteger userId);
	//提前释放
	public int modifyearlyLog(@Param("info")String info,@Param("imprisonmentTime")int imprisonmentTime,@Param("userId")BigInteger userId);
	//释放
	public int freed(@Param("userId")int userId);
//	//查询所有瘟疫
//	public List<T_plague> findPlagueAll();

	//新增-------------------------------------------------------------------------
	public int addToUser_c(List<T_user> list);
	public int removeUser(List<BigInteger> list);
	public int addIntoTrial(List<T_mingjie_trial> list);
	public List<T_user> selectUserAll(@Param("user_id") int user_id,
									  @Param("username") String username,
									  @Param("pageCount")int pageCount,
									  @Param("pageSize")int pageSize);
}
