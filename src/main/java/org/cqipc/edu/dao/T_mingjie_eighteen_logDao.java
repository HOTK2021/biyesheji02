package org.cqipc.edu.dao;


import org.apache.ibatis.annotations.Param;
import org.cqipc.edu.bean.T_mingjie_eighteen_log;

import java.math.BigInteger;
import java.util.List;

public interface T_mingjie_eighteen_logDao {
    public List<T_mingjie_eighteen_log> selectTortured();
    public int modifyLog(@Param("info")String info,@Param("imprisonmentTime")int imprisonmentTime,@Param("userId")BigInteger userId);
    public List<T_mingjie_eighteen_log> selectHellRecord();
    public int addToAllocateHellResult(T_mingjie_eighteen_log t_mingjie_eighteen_log);
    public int modifyearlyLog(@Param("info")String info,@Param("imprisonmentTime")int imprisonmentTime,@Param("userId")BigInteger userId);
    public int freed(@Param("userId")int userId);
}
