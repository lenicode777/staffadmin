package dmp.staffadmin.security.authentication;

import java.util.Collection;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

public class AppAuthentication extends UsernamePasswordAuthenticationToken {

	public AppAuthentication(Object principal, Object credentials) 
	{
		super(principal, credentials);
	}
	
	public AppAuthentication(Object principal, Object credentials, Collection<? extends GrantedAuthority> authorities)
	{
		super(principal, credentials, authorities);
	}
	

}
