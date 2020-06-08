package org.cqipc.edu.service.impl;

import java.math.BigInteger;
import java.util.List;

import org.cqipc.edu.bean.T_menu;
import org.cqipc.edu.bean.T_role;
import org.cqipc.edu.bean.T_user;
import org.cqipc.edu.bean.T_user_config;
import org.cqipc.edu.bean.T_user_role;
import org.cqipc.edu.dao.T_menuDao;
import org.cqipc.edu.dao.T_roleDao;
import org.cqipc.edu.dao.T_role_menuDao;
import org.cqipc.edu.dao.T_userDao;
import org.cqipc.edu.dao.T_user_configDao;
import org.cqipc.edu.dao.T_user_roleDao;
import org.cqipc.edu.service.T_userService;
import org.cqipc.edu.util.MD5;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service("t_userService")
public class T_userServiceImpl implements T_userService {
	@Autowired
	T_userDao tu;
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
	@Override
	//用户登录的方法
	public Object[] Login(String username, String password) {
		//根据用户名和密码查询用户对象
		T_user user=tu.userLogin(username, MD5.getMd5(password));
		if(user!=null) {
			//根据用户ID查询用户配置信息对象
			T_user_config userConfig=tc.findUserConfigByUid(user.getUser_id());
			//根据用户ID查询用户权限ref对象
			T_user_role userRole=to.findRoleByUid(user.getUser_id());
			//根据用户的权限ID查询权限对象
			T_role role=rd.findRoleByRid(userRole.getRole_id());
			//根据权限id查询权限下所有对应的权限ID
			List<BigInteger> ids=trm.findRoleMenuByRid(role.getRole_id());
			//根据所有的权限ID查询所有的权限对象
			List<T_menu> list=tm.findMenuByMid(ids);
			return new Object[] {user,userConfig,list};
		}else {
			return new Object[] {"error"};
		}
	}

}
