package dmp.staffadmin.security.utilities;

import java.util.Collection;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import dmp.staffadmin.security.authentication.AppAuthentication;
import dmp.staffadmin.security.model.AppUser;
import dmp.staffadmin.security.services.SecurityUser;

public interface ISecurityContextManager 
{
	public void refreshSecurityContext(AppUser user, SecurityContextHolder securityContextHolder );
}
