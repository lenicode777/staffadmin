package dmp.staffadmin.config;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.convert.DataSizeUnit;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import dmp.staffadmin.security.authentication.AppAuthentication;
import dmp.staffadmin.security.dao.AppPrivilegeDao;
import dmp.staffadmin.security.dao.AppRoleDao;
import dmp.staffadmin.security.dao.AppUserDao;
import dmp.staffadmin.security.model.AppRole;
import dmp.staffadmin.security.model.AppUser;
import dmp.staffadmin.security.services.AppUserDetailsManager;
import dmp.staffadmin.security.services.SecurityUser;
import lombok.Data;
import lombok.Setter;

@Configuration

//@Component
//@Scope(value = WebApplicationContext.SCOPE_SESSION , proxyMode = ScopedProxyMode.TARGET_CLASS)
public class AppLoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler
{
	@Autowired private AppUserDetailsManager userDetailsManager;
	@Autowired private AppRoleDao roleDao;
	@Autowired private AppPrivilegeDao privilegeDao;

	

	@Override
	protected String determineTargetUrl(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) {
		// TODO Auto-generated method stub
		return super.determineTargetUrl(request, response, authentication);
	}



	@Override
	public void setDefaultTargetUrl(String defaultTargetUrl) {
		// TODO Auto-generated method stub
		super.setDefaultTargetUrl(defaultTargetUrl);
	}



	@Override
	public void setAlwaysUseDefaultTargetUrl(boolean alwaysUseDefaultTargetUrl) {
		// TODO Auto-generated method stub
		super.setAlwaysUseDefaultTargetUrl(alwaysUseDefaultTargetUrl);
	}



	@Override
	protected RedirectStrategy getRedirectStrategy() {
		// TODO Auto-generated method stub
		return super.getRedirectStrategy();
	}



	@Override
	public void setUseReferer(boolean useReferer) {
		// TODO Auto-generated method stub
		super.setUseReferer(useReferer);
		
	}



	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException 
	{
		// TODO Auto-generated method stub
		//super.onAuthenticationSuccess(request, response, authentication);
		String username = authentication.getName();
		
		SecurityUser securityUser = (SecurityUser) userDetailsManager.loadUserByUsername(username);
		if (response.isCommitted())
		{
			return;
		}
		String urlTarget;
		List<String> roles = authentication.getAuthorities().stream().map(ga -> ga.getAuthority())
				.collect(Collectors.toList());
		if (securityUser.getUser().getDefaultRoleId()==null)
		{
			urlTarget = "/staffadmin/administration/select-role-form";		
			//System.out.println("Plusieurs Roles");
		} else
		{
			urlTarget = "/";
		}
		RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
		redirectStrategy.sendRedirect(request, response, urlTarget);
	}

}
