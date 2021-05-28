package dmp.staffadmin;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import dmp.staffadmin.security.providers.AppAuthenticationProvider;
import dmp.staffadmin.security.services.AppUserDetailsManager;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true, jsr250Enabled = true)
@EnableAspectJAutoProxy
@Component
public class SecurityConfig extends WebSecurityConfigurerAdapter
{
	//@Autowired SimpleUrlAuthenticationSuccessHandler successHandler;
	@Autowired
	private DataSource datasource;
	@Autowired private AppAuthenticationProvider authenticationProvider;
	@Autowired private AppUserDetailsManager userDetailsManager;
	@Autowired private SimpleUrlAuthenticationSuccessHandler AppLoginSuccessHandler;
	//@Autowired private AppAuthenticationFilter appAuthenticationFilter;
	@Override
	protected void configure(HttpSecurity http) throws Exception
	{
		//http.addFilterBefore(appAuthenticationFilter, BasicAuthenticationFilter.class);
		//http.formLogin().successHandler(AppLoginSuccessHandler);
		http.formLogin();
		http.authorizeRequests().anyRequest().authenticated();
		http.csrf();
		http.exceptionHandling().accessDeniedPage("/staffadmin/access-denied");
		AccessDecisionManager accessDecisionManager;
		AccessDeniedHandler accessDeniedHandler;
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception
	{
		auth.authenticationProvider(daoAuthenticationProvider());
	}

	@Bean
	public PasswordEncoder passwordEncoder()
	{
		return new BCryptPasswordEncoder();
	}
	
	@Override
	@Bean
	public AuthenticationManager authenticationManagerBean() throws Exception 
	{
		return super.authenticationManagerBean();
	}
	
	@Bean
	DaoAuthenticationProvider daoAuthenticationProvider()
	{
		DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
		daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
		daoAuthenticationProvider.setUserDetailsService(userDetailsManager);
		return daoAuthenticationProvider;
	}

//	@Bean
//	SimpleUrlAuthenticationSuccessHandler simpleUrlAuthenticationSuccessHandler()
//	{
//		return new SimpleUrlAuthenticationSuccessHandler();
//	}
}
