package org.cqipc.edu.service;

import org.apache.ibatis.annotations.Param;
import org.cqipc.edu.bean.*;
//import org.cqipc.edu.bean.T_plague_user;

import java.math.BigInteger;
import java.util.List;

public interface SystemService {
    public List<T_plague> selectPlauge();
    public List<T_plague_info> selectPlaugeInfo(@Param("pageCount") int pageCount,
                                                @Param("pageSize") int pageSize);
    public int selectPlaugeInfoCount();
    //查询生簿中非公职人员用于瘟疫中随机死去
    public List<T_user> selectUserToPlauge();
    public int selectUserToPlaugeCount();
    //发布瘟疫后向瘟疫信息表中添加
    public int addPlague(T_plague_info t_plague_info);
    public int addPlagueUser(List<T_plague_user> list);
    public List<T_plague_user> selectPlagueUser(int plague_info_id);
    //----------------
    public List<T_user>selectUser1(List<BigInteger> list);


    public List<T_mingjie_had> selectHad(@Param("pageCount") int pageCount,
                                         @Param("pageSize") int pageSize);
    public int selectHadCount();
    public int addHadLog(T_mingjie_hadescurrency_log t_mingjie_hadescurrency_log);
    public int modifyTotal(T_mingjie_hadescurrency t_mingjie_hadescurrency);
    public T_mingjie_hadescurrency selectHadByid(int userId);

    public List<T_mingjie_hadescurrency_log> selectLog(@Param("pageCount") int pageCount,
                                                       @Param("pageSize") int pageSize);
    public int selectLogCount();
    //-----------------------------------------------------------------
    public T_role findRoleByRid(BigInteger roleId);
    public List<T_role> findRole();
    public List<T_user> selectnotGZ();
    public int addgongzhi(T_user t_user);
    public int addUserRole(T_user_role t_user_role);

    public List<T_u_r> selectUserRole(@Param("pageCount")int pageCount,
                                      @Param("pageSize")int pageSize);
    public int selectUserRoleCount();

//    public T_user_role findRoleByUid(BigInteger userID);
    public int updateRole(T_user_role t_user_role);
}
