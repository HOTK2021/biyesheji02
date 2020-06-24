package org.cqipc.edu.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.cqipc.edu.bean.T_mingjie_lifeanddie;
import org.cqipc.edu.bean.T_mingjie_trial;
import org.cqipc.edu.bean.T_user;
import org.cqipc.edu.bean.T_user_s;
import org.cqipc.edu.dao.T_mingjie_trialDao;
import org.cqipc.edu.service.T_userService;
import org.cqipc.edu.util.MD5;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.*;


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
//查询生表用户
	@RequestMapping("/userAll")
	@ResponseBody
	public Map<String,Object> userAll(@RequestParam(value = "page",required = false,
			defaultValue ="1")int page,@RequestParam(value = "limit",required = false,
			defaultValue ="10")int limit,@RequestParam(value = "keyWord",required = false,
	defaultValue = "")String keyWord,@RequestParam(value = "user_id",required = false,
			defaultValue = "0")int user_id){
		int count=0;
		count=ts.selectUserCount();
		List<T_user> list=ts.selectUserAll(page,limit,keyWord,user_id);
		Map<String,Object> map=new HashMap<String, Object>();
		map.put("code", 0);
		map.put("msg", "");
		map.put("count",count);
		map.put("data", list);
		System.out.println("结束生表查询");
		return map;
	}
//查询死表用户
	@RequestMapping("/userDieAll")
	@ResponseBody
	public Map<String,Object> userDieAll(@RequestParam(value = "page",required = false,
			defaultValue ="1")int page,@RequestParam(value = "limit",required = false,defaultValue = "10")int limit){
		int count;
		count=ts.selectUserDieCount();
		List<T_user> list=ts.selectUserDieAll(page,limit);
		Map<String,Object> map=new HashMap<String, Object>();
		map.put("code", 0);
		map.put("msg", "");
		map.put("count", count);
		map.put("data", list);
		System.out.println("结束死表查询！");
		return map;
	}
//添加用户
	@RequestMapping("/addUser")
	@ResponseBody
	public int addUser(T_user atu){
		System.out.println("进入添加");
		atu.setPassword(MD5.getMd5(atu.getPassword()));
		atu.setAvatar("sssss");
		atu.setStatus(0);
//		atu.setCreate_time(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
		atu.setModify_time(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
		atu.setLast_login_time(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
		String date=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
//		T_user cs=new T_user("xiaogui",MD5.getMd5("123456"), BigInteger.valueOf(5),"111@qq.com","145623987",1,date,date,date,"男","大鬼好惹，小鬼难缠","jZUIxmJycoymBprLOUbT.png",120);
		int yes=ts.addUser(atu);
		int ok=ts.addTMLAD(new T_mingjie_lifeanddie(null,atu.getUser_id(),new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()),0,atu.getAge(),atu.getAge(),1));
		System.out.println(atu.getUser_id());
		if(yes==1&&ok==1){
			System.out.println("成功");
			return 1;
		}else {
			System.out.println("失败");
			return 0;
		}

	}
//修改生表用户信息
	@RequestMapping("/modifyUser")
	@ResponseBody
	public int modifyUser(T_user atu){
		System.out.println(atu);
		return ts.modifyUserInfo(atu.getUsername(),atu.getDescription(),atu.getAge(),atu.getUser_id());
	}
//查询待审批
	@RequestMapping("/selectToBeProcessed")
	@ResponseBody
	public Map<String,Object> selectToBeProcessed(){
		List<T_mingjie_lifeanddie> list=ts.selectOver();
		List<T_user> list1=new ArrayList();
		for(T_mingjie_lifeanddie t:list){
			BigInteger id=t.getUserId();
			list1.add(ts.selectToBeProcessed(id));
		}
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("code",0);
		map.put("msg","");
		map.put("count",ts.selectUserDieCount());
		map.put("data",list1);
		System.out.println(list1);
		return map;
	}

//待处理
	@RequestMapping("/process")
	@ResponseBody
	public int process(@RequestParam("user_id")int user_id){
		String date=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
		if (ts.deleteProcessed(user_id)==1&&ts.modifyPS(user_id)==1){
			ts.dieTime(date);
			ts.addTMTD(user_id);
			return 1;
		}else {
			return 0;
		}
	}

	//待审判
	@RequestMapping("/notpproved")
	@ResponseBody
	public Map<String,Object> notpproved(){
		List<T_user_s> list=ts.selectNotpproved();
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("code",0);
		map.put("msg","");
		map.put("count",ts.selectUserDieCount());
		map.put("data",list);
		System.out.println(ts.selectNotpproved());
		return map;
	}
	//审判处理过程
	@RequestMapping("/notTrial")
	@ResponseBody
	public int notTrial(T_mingjie_trial tmtd){
		ts.notTrial(tmtd.getInfo());
		System.out.println(tmtd.getInfo());
		return 1;
	}
}
