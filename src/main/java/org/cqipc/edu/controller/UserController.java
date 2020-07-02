package org.cqipc.edu.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.cqipc.edu.bean.*;
import org.cqipc.edu.dao.T_mingjie_trialDao;
import org.cqipc.edu.service.T_userService;
import org.cqipc.edu.util.MD5;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;
import java.sql.ClientInfoStatus;
import java.text.SimpleDateFormat;
import java.util.*;


@Controller
@SessionAttributes("LoginParams")
public class UserController {
	@Autowired
	T_userService ts;

	@SystemControllerLog(description = "用户登录")
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
		System.out.println(MD5.getMd5(password));
		Object[] param=ts.Login(username, password);
		Object[] par=new Object[1];
		request.getSession();
		if(param[0]=="error") {
			par[0]="用户名或者密码错误";
			return par;
		}else if(param[0]=="not"){
			par[0]="您不是公职人员，不能登录！";
			return par;
		}else if(param[0]=="error1"){
			par[0]="您在系统中的权限未完善，不能登录！如要登录，请联系管理员!";
			return par;
		} else  {
			model.addAttribute("LoginParams", param);
			System.out.println("*****测试model");
			Object[] p= (Object[]) model.getAttribute("LoginParams");
			System.out.println(p[0]);
			par[0]="ok";
			return par;
		}
	}
	@SystemControllerLog(description = "登录成功")
	@RequestMapping("/loginSuccess")
	@ResponseBody
	public Object[] loginSuccess(HttpSession session) {
		//由于在上一个URL中已经保存了登录信息在session中，所以此时直接从session中获取值，由此可见，SpringMVC的
		//session和servletAPI中的session是同一个
		Object[] param=(Object[])session.getAttribute("LoginParams");
		System.out.println("打印param");

		System.out.println("打印结束");
		return param;
	}
//	@RequestMapping("userLogin")
//	@ResponseBody
//	public Object[] userLogin(@RequestParam("username")String username,
//			@RequestParam("password")String password,Model model,HttpServletRequest request) {
//		/*
//		 * username和password是正常的参数
//		 * 由于使用的是ajax提交，因此必须返回到回调函数，但如果成功登录需要将登录信息保存在session中
//		 * 因此需要使用model对象来使springMVC的session所识别，这里的name名称即key值必须一致
//		 *
//		 * 又由于此操作是一个response操作，在ajax重新定向到下一个URL之后sessionID已经找不到了，所以要在返回
//		 * 值之前通过request get出session
//		 * */
//		System.out.println(username);
//		Object[] param=ts.Login(username, password);
//		Object[] par=new Object[1];
//		request.getSession();
//		if(param[0]== "nopermission"){
//			par[0] = "对不起，你不是公职人员，没有登录权限！";
//			return par;
//		}else {
//			if (param[0] != "error") {
//				model.addAttribute("LoginParams", param);
//				par[0] = "ok";
//				return par;
//			} else {
//				par[0] = "用户名或者密码错误";
//				return par;
//			}
//		}
//	}
//	@RequestMapping("loginSuccess")
//	@ResponseBody
//	public Object[] loginSuccess(HttpSession session) {
//		//由于在上一个URL中已经保存了登录信息在session中，所以此时直接从session中获取值，由此可见，SpringMVC的
//		//session和servletAPI中的session是同一个
//		Object[] param=(Object[])session.getAttribute("LoginParams");
//		System.out.println(param);
//		return param;
//	}
//查询生表用户
@SystemControllerLog(description = "select")
	@RequestMapping("/userAll")
	@ResponseBody
	public Map<String,Object> userAll(@RequestParam(value = "page",required = false,
			defaultValue ="1")int page,@RequestParam(value = "limit",required = false,
			defaultValue ="10")int limit,@RequestParam(value = "keyWord",required = false,
	defaultValue = "")String keyWord,@RequestParam(value = "user_id",required = false,
			defaultValue = "0")int user_id){
		int count=0;
		count=ts.selectUserCount();
		List<T_user> list=ts.selectUserAll1(page,limit,keyWord,user_id);
		Map<String,Object> map=new HashMap<String, Object>();
		map.put("code", 0);
		map.put("msg", "");
		map.put("count",count);
		map.put("data", list);
		System.out.println("结束生表查询");
		return map;
	}
//查询死表用户
	@SystemControllerLog(description = "select")
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
    @SystemControllerLog(description = "添加")
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
	@SystemControllerLog(description = "修改")
	@RequestMapping("/modifyUser")
	@ResponseBody
	public int modifyUser(T_user atu){
		System.out.println(atu);
		return ts.modifyUserInfo(atu.getUsername(),atu.getDescription(),atu.getAge(),atu.getUser_id());
	}
//查询待审批
	@SystemControllerLog(description = "select")
	@RequestMapping("/selectToBeProcessed")
	@ResponseBody
	public Map<String,Object> selectToBeProcessed(){
		List<T_mingjie_lifeanddie> list=ts.selectOver();
		System.out.println(list+"结束");
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
	@SystemControllerLog(description = "处理")
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
	@SystemControllerLog(description = "select")
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
	@SystemControllerLog(description = "审判处理")
	@RequestMapping("/notTrial")
	@ResponseBody
	public int notTrial(T_mingjie_trial tmtd){
		String date=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
		if(tmtd.getTypes()==0){
			if (ts.notTrial(tmtd.getInfo(),date,tmtd.getTypes(),tmtd.getUserId())==1&&ts.inserWaitSoul(tmtd.getUserId(),"轮回，等待分配孟婆汤"
			,date,0)==1){
				System.out.println(tmtd);
				return 1;
			}else {
				return 0;
			}
		}else {
			if (ts.notTrial(tmtd.getInfo(),date,tmtd.getTypes(),tmtd.getUserId())==1){
				System.out.println(tmtd);
				return 1;
			}else {
				return 0;
			}

		}
	}


	//已审批信息
	@SystemControllerLog(description = "select")
	@RequestMapping("/tried")
	@ResponseBody
	public Map<String,Object> tried(){
		List<T_mingjie_trial> list=ts.selectTried();
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("code",0);
		map.put("msg","");
		map.put("data",list);
		return map;
	}

	@SystemControllerLog(description = "select")
	@RequestMapping("/selectTriedName")
	@ResponseBody
	public String selectTriedName(@RequestParam("user_id")int user_id){
		System.out.println(ts.selectDieUserTried(user_id).getUsername());
		return ts.selectDieUserTried(user_id).getUsername();
	}

	//查询没有分配孟婆汤
	@SystemControllerLog(description = "select")
	@RequestMapping("/selectSoulDispensed")
	@ResponseBody
	public Map<String,Object> selectSoulDispensed(){
		Map<String,Object> map=new HashMap<String,Object>();
		List<T_mingjie_soul> list=ts.selectSoulDispensed();
		map.put("code",0);
		map.put("msg","");
		map.put("data",list);
		System.out.println(map);
		return map;
	}
	//查询已经分配孟婆汤
	@SystemControllerLog(description = "select")
	@RequestMapping("/selectunallocatedMPS")
	@ResponseBody
	public Map<String,Object> selectunallocatedMPS(){
		Map<String,Object> map=new HashMap<String,Object>();
		List<T_mingjie_soul> list=ts.selectunallocatedMPS();
		map.put("code",0);
		map.put("msg","");
		map.put("data",list);
		System.out.println(map);
		return map;
	}
	//分配孟婆汤
	@SystemControllerLog(description = "分配")
	@RequestMapping("/dispensed")
	@ResponseBody
	public int dispensed(@RequestParam("userId")int userId){
		String date=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
		if (ts.distributeMPS(date,userId)==1){
			return 1;
		}else {
			return 0;
		}
	}

	//撤回孟婆汤
	@SystemControllerLog(description = "撤回")
	@RequestMapping("/withdraw")
	@ResponseBody
	public int withdraw(@RequestParam("userId")int userId){
		String date=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
		if (ts.withdrawMPS(date,userId)==1){
			return 1;
		}else {
			return 0;
		}
	}

	//查询18层地狱
	@SystemControllerLog(description = "select")
	@RequestMapping("/selectMingJieEighteen")
	@ResponseBody
	public List<T_mingjie_eighteen> selectMingJieEighteen(){
		List<T_mingjie_eighteen> list=ts.selectMingJieEighteen();
//		Map<String,Object> map=new HashMap<String, Object>();
//		map.put("code",0);
//		map.put("msg","");
//		map.put("data",list);
//		System.out.println(list);
		return list;
	}

	//等待分配地狱
	@SystemControllerLog(description = "select")
	@RequestMapping("/waitingToAllocateHell")
	@ResponseBody
	public Map<String,Object> waitingToAllocateHell(){
		List<T_mingjie_trial> list=ts.waitingToAllocateHell();
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("code",0);
		map.put("msg","");
		map.put("data",list);
		return map;
	}

	//分配地狱处理结果
	@SystemControllerLog(description = "分配地狱处理结果")
	@RequestMapping("/ToAllocateHellResult")
	@ResponseBody
	public int ToAllocateHellResult(T_mingjie_eighteen_log tmel){
		tmel.setInOrOutTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
		tmel.setStatus(1);
		System.out.println(tmel);
		if(ts.addToAllocateHellResult(tmel)==1&&ts.hellAssigned(tmel.getUserId())==1){
			return 1;
		}
		else {
			return 0;
		}


	}

	//查询正在受刑中的人
	@SystemControllerLog(description = "select")
	@RequestMapping("/selectTortured")
	@ResponseBody
	public Map<String,Object> selectTortured(){
		List<T_mingjie_eighteen_log> list=ts.selectTortured();
		System.out.println(list);
		Map<String,Object> map=new HashMap<String, Object>();
		map.put("code",0);
		map.put("msg","");
		map.put("data",list);
		return map;
	}

	//延迟出狱时间
	@SystemControllerLog(description = "添加")
	@RequestMapping("/addImprisonmentTime")
	@ResponseBody
	public int addImprisonmentTime(T_mingjie_eighteen_log tmel,@RequestParam("addImprisonmentTime")int addImprisonmentTime){
		int new1=tmel.getImprisonmentTime()+addImprisonmentTime;
		tmel.setImprisonmentTime(new1);
		if(ts.modifyLog(tmel.getInfo(),tmel.getImprisonmentTime(),tmel.getUserId())==1){
			return 1;
		}else {
			return 0;
		}
	}

	//地狱记录
	@SystemControllerLog(description = "select")
	@RequestMapping("/hellRecord")
	@ResponseBody
	public Map<String,Object> hellRecord(){
		List<T_mingjie_eighteen_log> list=ts.selectHellRecord();
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("code",0);
		map.put("msg","");
		map.put("data",list);
		return map;
	}

	//提前释放
	@SystemControllerLog(description = "提前释放")
	@RequestMapping("/earlyRelease")
	@ResponseBody
	public int earlyRelease(T_mingjie_eighteen_log tmel,@RequestParam("addImprisonmentTime")int addImprisonmentTime){
		if(tmel.getImprisonmentTime()<addImprisonmentTime){
			return 2;
		}else {
			System.out.println(tmel.getImprisonmentTime()+"提前释放");
			int new1=tmel.getImprisonmentTime()-addImprisonmentTime;
			System.out.println(new1+"提前释放");
			tmel.setImprisonmentTime(new1);
			System.out.println(tmel+"提前释放");
			if(ts.modifyearlyLog(tmel.getInfo(),tmel.getImprisonmentTime(),tmel.getUserId())==1){
				return 1;
			}else {
				return 0;
			}
		}

	}
	//释放
	@SystemControllerLog(description = "释放")
	@RequestMapping("/freed")
	@ResponseBody
	public int freed(@RequestParam("userId")int userId){
		if (ts.freed(userId)==1){
			return 1;
		}else {
			return 2;
		}

	}
	//查询生表用户
	@SystemControllerLog(description = "select")
	@RequestMapping("/findUserAll")
	@ResponseBody
	public Map<String,Object> findUserAll(@RequestParam(value = "page",required = false,
			defaultValue ="1")int page,@RequestParam(value = "limit",required = false,
			defaultValue ="10")int limit,@RequestParam(value = "keyWord",required = false,
			defaultValue = "")String keyWord,@RequestParam(value = "user_id",required = false,
			defaultValue = "0")int user_id){
		int count=0;
		count=ts.selectUserCount();
		List<T_user> list=ts.selectUserAll1(page,limit,keyWord,user_id);
		Map<String,Object> map=new HashMap<String, Object>();
		map.put("code", 0);
		map.put("msg", "");
		map.put("count",count);
		map.put("data", list);
		System.out.println("结束生表查询");
		return map;
	}

//	//查询所有瘟疫
//	@RequestMapping("/findPlagueAll")
//	@ResponseBody
//	public List<T_plague> findPlagueAll(){
//		return ts.findPlagueAll();
//	}
}
