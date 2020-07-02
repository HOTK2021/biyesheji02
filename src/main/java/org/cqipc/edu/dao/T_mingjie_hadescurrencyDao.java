package org.cqipc.edu.dao;

import org.apache.ibatis.annotations.Param;
import org.cqipc.edu.bean.T_mingjie_had;
import org.cqipc.edu.bean.T_mingjie_hadescurrency;
import org.cqipc.edu.bean.T_mingjie_hadescurrency_log;


import java.util.List;

public interface T_mingjie_hadescurrencyDao {
    public List<T_mingjie_had> selectHad(@Param("pageCount") int pageCount,
                                         @Param("pageSize") int pageSize);
    public int selectHadCount();
    public int addHadLog(T_mingjie_hadescurrency_log t_mingjie_hadescurrency_log);
    public int modifyTotal(T_mingjie_hadescurrency t_mingjie_hadescurrency);
    public T_mingjie_hadescurrency selectHadByid(int userId);

    public List<T_mingjie_hadescurrency_log> selectLog(@Param("pageCount") int pageCount,
                                                       @Param("pageSize") int pageSize);
    public int selectLogCount();

}
