package org.cqipc.edu.dao;

import org.apache.ibatis.annotations.Param;
import org.cqipc.edu.bean.T_mingjie_lifeanddie;

import java.math.BigInteger;
import java.util.List;

public interface T_mingjie_lifeanddieDao {
    public int addTMLAD(T_mingjie_lifeanddie tmld);
    public List<T_mingjie_lifeanddie> selectOver();
    public int selectLifeAndDieCount();
    public int modifyPS(@Param("userId")int userId);
}
