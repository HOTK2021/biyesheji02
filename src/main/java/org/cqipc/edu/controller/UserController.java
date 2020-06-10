package org.cqipc.edu.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.cqipc.edu.bean.T_user;
import org.cqipc.edu.service.T_userService;
import org.cqipc.edu.util.MD5;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Controller
@SessionAttributes("LoginParams")
public class UserController {
	@Autowired
	T_userService ts;
	
	
	@RequestMapping("userLogin")
	@ResponseBody
	public Object[] userLogin(@RequestParam("username")String username,
			@RequestParam("password")String password,Model model,HttpServletRequest request) {
		/*
		 * username和password是正常的参数
		 * 由于使用的是ajax提交，因此必须返回到回调函数，但如果成功登录需要将登录信息保存在session中
		 * 因此需要使用model对象来使springMVC的session所识别，这里的name名称即key值必须一致
		 * 
		 * 又由于此操作是一个response操作，在ajax重新定向到下一个URL之后sessionID已经找不到了，所以要在返回
		 * 值之前通过request get出session
		 * */
		System.out.println(username);
		Object[] param=ts.Login(username, password);
		Object[] par=new Object[1];
		request.getSession();
		if(param[0]== "nopermission"){
			par[0] = "对不起，你不是公职人员，没有登录权限！";
			return par;
		}else {
			if (param[0] != "error") {
				model.addAttribute("LoginParams", param);
				par[0] = "ok";
				return par;
			} else {
				par[0] = "用户名或者密码错误";
				return par;
			}
		}
	}
	@RequestMapping("loginSuccess")
	@ResponseBody
	public Object[] loginSuccess(HttpSession session) {
		//由于在上一个URL中已经保存了登录信息在session中，所以此时直接从session中获取值，由此可见，SpringMVC的
		//session和servletAPI中的session是同一个
		Object[] param=(Object[])session.getAttribute("LoginParams");
		System.out.println(param);
		return param;
	}

	@RequestMapping("/userAll")
	@ResponseBody
	public Map<String,Object> userAll(){
		System.out.println("进入工作空间");
		List<T_user> list=ts.selectUserAll();
		System.out.println(list);
		Map<String,Object> map=new HashMap<String, Object>();
		map.put("code", 0);
		map.put("msg", "");
		map.put("data", list);
		System.out.println(map);
		return map;
	}

	@RequestMapping("/addUser")
	@ResponseBody
	public int addUser(T_user atu){
		atu.setCreate_time(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
		atu.setModify_time(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
		atu.setLast_login_time(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
		String date=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
		T_user cs=new T_user("xiaogui",MD5.getMd5("123456"), BigInteger.valueOf(5),"111@qq.com","145623987",1,date,date,date,"男","大鬼好惹，小鬼难缠","jZUIxmJycoymBprLOUbT.png",120);
		return ts.addUser(cs);
	}
}
