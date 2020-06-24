package org.cqipc.edu.service.impl;

import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.cqipc.edu.bean.*;
import org.cqipc.edu.dao.*;
import org.cqipc.edu.service.T_userService;
import org.cqipc.edu.util.MD5;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service("t_userService")
public class T_userServiceImpl implements T_userService {
	@Autowired
	T_userDao tu;
	@Autowired
	T_user_sDao tus;
	@Autowired
	T_user_configDao tc;
	@Autowired
	T_user_roleDao to;
	@Autowired
	T_roleDao rd;
	@Autowired
	T_role_menuDao trm;
	@Autowired
	T_menuDao tm;
	@Autowired
	T_mingjie_lifeanddieDao tmlad;
	@Autowired
	T_mingjie_trialDao tmtd;
	@Override
	//用户登录的方法
	public Object[] Login(String username, String password) {
		//根据用户名和密码查询用户对象
		T_user user=tu.userLogin(username, MD5.getMd5(password));
		if (user.getStatus()==1) {
			System.out.println(user.getUsername()+"!!!!!!!"+user.getStatus());
			if (user!=null) {
				System.out.println("sssss");
				//根据用户ID查询用户配置信息对象
				T_user_config userConfig = tc.findUserConfigByUid(user.getUser_id());
				//根据用户ID查询用户权限ref对象
				T_user_role userRole = to.findRoleByUid(user.getUser_id());
				//根据用户的权限ID查询权限对象
				T_role role = rd.findRoleByRid(userRole.getRole_id());
				//根据权限id查询权限下所有对应的权限ID
				List<BigInteger> ids = trm.findRoleMenuByRid(role.getRole_id());
				//根据所有的权限ID查询所有的权限对象
				List<T_menu> list = tm.findMenuByMid(ids);
				return new Object[]{user, userConfig, list};
			} else {
				return new Object[]{"error"};
			}
		}else {
			return new Object[]{"nopermission"};
		}
		}

	@Override
	public List<T_user> selectUserAll(int pageCount,int pageSize,String keyWord,int user_id) {
		int p=(pageCount-1)*pageSize;
		return tu.selectUserAll(p,pageSize,keyWord,user_id);
	}

	@Override
	public List<T_user> selectUserDieAll(int pageCount,int pageSize) {
		int p=(pageCount-1)*pageSize;
		return tu.selectUserDieAll(p,pageSize);
	}

	@Override
	public int selectUserCount() {
		return tu.selectUserCount();
	}

	@Override
	public int selectUserDieCount() {
		return tu.selectUserDieCount();
	}

	@Override
	public int addUser(T_user atu) {
		return tu.addUser(atu);
	}

	@Override
	public int modifyUserInfo(String username, String description, int age, BigInteger user_id) {
		return tu.modifyUserInfo(username,description,age,user_id);
	}

	@Override
	public int addTMLAD(T_mingjie_lifeanddie tmld) {
		return tmlad.addTMLAD(tmld);
	}
//查询声明周期表所有数据
	@Override
	public List<T_mingjie_lifeanddie> selectOver() {
		List list=tmlad.selectOver();
		int count=tmlad.selectLifeAndDieCount();
		List<T_mingjie_lifeanddie> list1=new ArrayList();
		T_mingjie_lifeanddie pro=new T_mingjie_lifeanddie();
		//根据查询出的条数，对数据做出判断选择
		for (int i=0;i<count;i++){
			pro= (T_mingjie_lifeanddie) list.get(i);
			if (pro.getAge()==pro.getTotalAge()&&pro.getOverAge()==0&&pro.getStatu()==1){
				System.out.println(pro);
				list1.add(pro);
			}
		}
		System.out.println(list1);
		return list1;
	}

	@Override
	public int selectLifeAndDieCount() {
		return tmlad.selectLifeAndDieCount();
	}

	@Override
	public T_user selectToBeProcessed(BigInteger user_id) {
		return tu.selectToBeProcessed(user_id);
	}

	@Override
	public int deleteProcessed(int user_id) {
		return tu.deleteProcessed(user_id);
	}

	//删除生表用户时，修改生命周期表中的状态值
	@Override
	public int modifyPS(int userId) {
		return tmlad.modifyPS(userId);
	}

	@Override
	public int addTMTD(int userId) {
		return tmtd.addTMTD(userId);
	}

	@Override
	public List<T_user_s> selectNotpproved() {
		return tus.selectNotpproved();
	}

	@Override
	public int dieTime(String create_time) {

		return tus.dieTime(create_time);
	}

	@Override
	public int notTrial(String info) {
		return tmtd.notTrial(info);
	}
}

