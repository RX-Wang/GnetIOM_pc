package com.gnet.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.filter.OncePerRequestFilter;

public class SessionFilter extends OncePerRequestFilter{

	/**
	 * 登录拦截过滤器
	 */
	@Override
	protected void doFilterInternal(HttpServletRequest request,
			HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		 // 不过滤的uri  
        String[] notFilter =  
            new String[] {"images", "js", "css","jQuery","less","asset","assets","fonts", "pageController/loginPage","userController/validateLoginUser","pageController/lockPage","userController/checkCompanyId","userController/checkCompanyName","userController/register","pageController/logout"};  
          
        // 请求的uri  
        String uri = request.getRequestURI();  
        // 是否过滤  
        boolean doFilter = true;  
        for (String s : notFilter)  
        {  
            if (uri.contains(s))  
            {  
                // 如果uri中包含不过滤的uri，则不进行过滤  
                doFilter = false;  
                break;  
            }  
        }  
          
        if (doFilter)  
        {  
            // 执行过滤  
            // 从session中获取登录者实体  
            Object obj1 = request.getSession().getAttribute("operatorInfo");  
            Object obj = request.getSession().getAttribute("companyInfo");  
            if (null == obj && obj1 == null)  
            {
                response.sendRedirect("../../web/pageController/loginPage");  
                return;
            }else{  
                // 如果session中存在登录者实体，则继续  
                filterChain.doFilter(request, response);  
            }  
        }else{  
            // 如果不执行过滤，则继续  
            filterChain.doFilter(request, response);  
        }  
	}
}
