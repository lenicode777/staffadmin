package dmp.staffadmin.security.utilities;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Component;

import dmp.staffadmin.security.authentication.AppAuthentication;
import dmp.staffadmin.security.model.AppUser;
import dmp.staffadmin.security.services.SecurityUser;

@Component
public class SecurityContextManager implements  ISecurityContextManager
{
	@Autowired private UserDetailsManager userDetailsManager;
	public void refreshSecurityContext(AppUser user, SecurityContextHolder securityContextHolder )
	{
		SecurityUser securityUser = (SecurityUser) userDetailsManager.loadUserByUsername(user.getUsername());
		Collection<? extends GrantedAuthority> authorities = securityUser.getAuthorities();
		Authentication appAuthentication = new AppAuthentication(securityUser, null, authorities);
		securityContextHolder.getContext().setAuthentication(appAuthentication);
	}
}
