package dmp.staffadmin.security.providers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import dmp.staffadmin.security.authentication.AppAuthentication;
import dmp.staffadmin.security.dao.AppUserDao;
import dmp.staffadmin.security.model.AppUser;
import dmp.staffadmin.security.services.SecurityUser;

@Component
public class AppAuthenticationProvider implements AuthenticationProvider
{
	@Autowired private AppUserDao userDao;
	@Autowired private PasswordEncoder passwordEncoder;
	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException 
	{
		String username = authentication.getName();
		String password = (String) authentication.getCredentials();
		
		AppUser user = userDao.findByUsername(username).orElseThrow(()->new BadCredentialsException("Username ou mot de passe incorrecte"));
		if(passwordEncoder.matches(password, user.getPassword()))
		{
			SecurityUser securityUser = new SecurityUser(user);
			
			return new AppAuthentication(username, null, securityUser.getAuthorities());
		}
		throw new BadCredentialsException("Username ou mot de passe incorrecte");
	}

	@Override
	public boolean supports(Class<?> authenticationClass) 
	{
		return AppAuthentication.class.equals(authenticationClass);
	}

}
