package org.cqipc.edu.dao;

import java.math.BigInteger;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.cqipc.edu.bean.T_role;
import org.cqipc.edu.bean.T_u_r;
import org.cqipc.edu.bean.T_user;
import org.cqipc.edu.bean.T_user_role;

public interface T_roleDao {
	public T_role findRoleByRid(BigInteger roleId);
	public List<T_role> findRole();
	public List<T_user> selectnotGZ();
	public int addgongzhi(T_user t_user);
	public int addUserRole(T_user_role t_user_role);

	public List<T_u_r> selectUserRole(@Param("pageCount")int pageCount,
									  @Param("pageSize")int pageSize);
	public int selectUserRoleCount();
}
