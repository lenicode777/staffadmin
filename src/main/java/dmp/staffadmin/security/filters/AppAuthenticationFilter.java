package dmp.staffadmin.security.filters;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import dmp.staffadmin.config.AppLoginSuccessHandler;

//@Component
public class AppAuthenticationFilter extends OncePerRequestFilter 
{
	//@Autowired private AppLoginSuccessHandler loginSuccessHandler;
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException 
	{
		System.out.println("Servelet Path 2 = " + request.getServletPath());
		System.out.println("Referer = " + request.getHeader("referer"));
		
		filterChain.doFilter(request, response);
		
	}

	@Override
	protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
		System.out.println("Servelet Path 1 = " + request.getServletPath());
		//System.out.println();
		return !request.getServletPath().equals("/login");
		//return false;
	}

}
