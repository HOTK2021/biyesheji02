package org.cqipc.edu.dao;

import org.apache.ibatis.annotations.Param;
import org.cqipc.edu.bean.T_user_s;

import java.util.List;

public interface T_user_sDao {
	public List<T_user_s> selectNotpproved();
	public int dieTime(@Param("create_time")String create_time);
}
