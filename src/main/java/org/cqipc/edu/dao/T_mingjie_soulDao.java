package org.cqipc.edu.dao;


import org.apache.ibatis.annotations.Param;
import org.cqipc.edu.bean.T_mingjie_soul;

import java.math.BigInteger;
import java.util.List;

public interface T_mingjie_soulDao {
    public int inserWaitSoul(@Param("userId")BigInteger userId, @Param("executorInfo")String executorInfo,
                             @Param("executorTime")String executorTime, @Param("executorStatus")int executorStatus);
    public List<T_mingjie_soul> selectSoulDispensed();
    public int distributeMPS(@Param("executorTime")String executorTime,@Param("userId")int userId);
    public List<T_mingjie_soul> selectunallocatedMPS();
    public int withdrawMPS(@Param("executorTime")String executorTime,@Param("userId")int userId);
}
