package dmp.staffadmin.security;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration @EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter
{
	@Autowired private DataSource datasource;
	@Override
	protected void configure(HttpSecurity http) throws Exception
	{
		// TODO Auto-generated method stub
		//super.configure(http);
		//http.formLogin().loginPage("/login");
		http.formLogin();
		//http.authorizeRequests().antMatchers("/save**/**", "/delete**/**", "/frm-**/**").hasRole("ADMIN");
		//http.authorizeRequests().antMatchers("/patients**/**").hasRole("USER");
		//http.authorizeRequests().antMatchers("/login**/**", "/connect", "/ressources/**", "//maxcdn.bootstrapcdn.com/bootstrap/**").permitAll();
		//http.authorizeRequests().anyRequest().authenticated();
		http.authorizeRequests().anyRequest().permitAll();
		//http.csrf().disable();
		http.csrf();
		//http.exceptionHandling().accessDeniedPage("/access-denied-page");
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception 
	{
		// TODO Auto-generated method stub
		//super.configure(auth);
		PasswordEncoder passwordEncoder = passwordEncoder();
		auth.jdbcAuthentication()
		.dataSource(datasource)
		.usersByUsernameQuery("select username as principal, password as credential, active from users where username=?")
		.authoritiesByUsernameQuery("select username as principal, role as role from users_role where username=?")
		.rolePrefix("ROLE_")
		.passwordEncoder(passwordEncoder);
	}
	
	@Bean
	public PasswordEncoder passwordEncoder()
	{
		return new BCryptPasswordEncoder();
	}
}
