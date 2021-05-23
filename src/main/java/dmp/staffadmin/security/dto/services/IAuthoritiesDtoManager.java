package dmp.staffadmin.security.dto.services;

import javax.servlet.http.HttpServletRequest;

import dmp.staffadmin.security.dto.AuthoritiesDto;

public interface IAuthoritiesDtoManager 
{
	public AuthoritiesDto loadAuthorities(HttpServletRequest request);
}
