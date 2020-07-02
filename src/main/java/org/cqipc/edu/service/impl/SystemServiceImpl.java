package org.cqipc.edu.service.impl;

import org.cqipc.edu.bean.*;
import org.cqipc.edu.dao.T_mingjie_hadescurrencyDao;
import org.cqipc.edu.dao.T_plagueDao;
import org.cqipc.edu.dao.T_roleDao;
import org.cqipc.edu.dao.T_user_roleDao;
import org.cqipc.edu.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.List;

@Service("SystemService")
public class SystemServiceImpl implements SystemService {
    @Autowired(required = false)
    T_plagueDao tp;

    @Autowired(required = false)
    T_mingjie_hadescurrencyDao th;

    @Autowired(required = false)
    T_roleDao tr;

    @Autowired(required = false)
    T_user_roleDao t_user_roleDao;
    @Override
    public List<T_plague> selectPlauge() {
        return tp.selectPlauge();
    }

    @Override
    public List<T_plague_info> selectPlaugeInfo(int pageCount,
                                                int pageSize) {
        int p=(pageCount-1)*pageSize;
        return tp.selectPlaugeInfo(p,pageSize);
    }

    @Override
    public int selectPlaugeInfoCount() {
        return tp.selectPlaugeInfoCount();
    }

    @Override
    public List<T_user> selectUserToPlauge() {
        return tp.selectUserToPlauge();
    }

    @Override
    public int selectUserToPlaugeCount(){
        return tp.selectUserToPlaugeCount();
    }

    @Override
    public int addPlague(T_plague_info t_plague_info) {
        return tp.addPlague(t_plague_info);
    }

    @Override
    public int addPlagueUser(List<T_plague_user> list) {
        return tp.addPlagueUser(list);
    }

    @Override
    public List<T_plague_user> selectPlagueUser(int plague_info_id){
        return tp.selectPlagueUser(plague_info_id);
    }

    @Override
    public List<T_user> selectUser1(List<BigInteger> list) {
        return tp.selectUser1(list);
    }

    //----------------------------------------------------------------------------------------

    @Override
    public List<T_mingjie_had> selectHad(int pageCount, int pageSize) {
        int p=(pageCount-1)*pageSize;
        return th.selectHad(p,pageSize);
    }

    @Override
    public int selectHadCount () {
        return th.selectHadCount();
    }

    @Override
    public int addHadLog(T_mingjie_hadescurrency_log t_mingjie_hadescurrency_log) {
        return th.addHadLog(t_mingjie_hadescurrency_log);
    }

    @Override
    public int modifyTotal(T_mingjie_hadescurrency t_mingjie_hadescurrency) {
        return th.modifyTotal(t_mingjie_hadescurrency);
    }

    @Override
    public T_mingjie_hadescurrency selectHadByid(int userId) {
        return th.selectHadByid(userId);
    }

    @Override
    public List<T_mingjie_hadescurrency_log> selectLog(int pageCount, int pageSize) {
        int p=(pageCount-1)*pageSize;
        return th.selectLog(p,pageSize);
    }

    @Override
    public int selectLogCount() {
        return th.selectLogCount();
    }

    @Override
    public T_role findRoleByRid(BigInteger roleId) {
        return tr.findRoleByRid(roleId);
    }

    //------------------------------------------------------------------
    @Override
    public List<T_role> findRole() {
        return tr.findRole();
    }

    @Override
    public List<T_user> selectnotGZ() {
        return tr.selectnotGZ();
    }

    @Override
    public int addgongzhi(T_user t_user) {
        return tr.addgongzhi(t_user);
    }

    @Override
    public int addUserRole(T_user_role t_user_role) {
        return tr.addUserRole(t_user_role);
    }

    @Override
    public List<T_u_r> selectUserRole(int pageCount, int pageSize) {
        int p=(pageCount-1)*pageSize;
        return tr.selectUserRole(p,pageSize);
    }

    @Override
    public int selectUserRoleCount() {
        return tr.selectUserRoleCount();
    }

//    @Override
//    public T_user_role findRoleByUid(BigInteger userID) {
//        return null;
//    }

    @Override
    public int updateRole(T_user_role t_user_role) {
        return t_user_roleDao.updateRole(t_user_role);
    }
}
