package org.cqipc.edu.dao;

import org.apache.ibatis.annotations.Param;
import org.cqipc.edu.bean.T_mingjie_trial;

import java.util.List;

public interface T_mingjie_trialDao {
    public int addTMTD(@Param("userId")int userId);
    public int notTrial(@Param("info")String info);
}
