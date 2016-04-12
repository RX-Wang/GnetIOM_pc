package com.gnet.module.MyTest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/myTest")
public class MyTest {
	@RequestMapping("myLogin")
	public void myLogin(HttpServletRequest request,HttpServletResponse response){
		
	}
}
