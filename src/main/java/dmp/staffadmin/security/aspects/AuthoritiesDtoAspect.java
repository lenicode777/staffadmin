package dmp.staffadmin.security.aspects;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

import com.sun.xml.bind.v2.runtime.unmarshaller.XsiNilLoader.Array;

import dmp.staffadmin.security.dto.AuthoritiesDto;
import dmp.staffadmin.security.dto.services.AuthoritiesDtoManager;

@Aspect
@Component

public class AuthoritiesDtoAspect 
{
	@Autowired private AuthoritiesDtoManager authoritiesDtoManager;
	@Around(value = "@annotation(AuthoritiesDtoAnnotation)", argNames = "joinPoint")
	public String injectAuthoritiesDtoToViewModel(ProceedingJoinPoint joinPoint) throws Throwable
	{
		HttpServletRequest request = null;
		Model model = null;
		Object [] args= joinPoint.getArgs();
		for(Object arg : args)
		{
			if (arg instanceof HttpServletRequest) request = (HttpServletRequest) arg;
			if(arg instanceof Model) model = (Model) arg;
		}
		
		AuthoritiesDto authoritiesDto = authoritiesDtoManager.loadAuthorities(request);
		model.addAttribute("authoritiesDto", authoritiesDto);
		
		Object pageRoute  = joinPoint.proceed();
		
		return (String) pageRoute;
	}
}
