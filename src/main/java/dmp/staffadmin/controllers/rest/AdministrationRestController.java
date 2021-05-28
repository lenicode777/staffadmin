package dmp.staffadmin.controllers.rest;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import dmp.staffadmin.security.dao.AppPrivilegeDao;
import dmp.staffadmin.security.dto.PrivilegeAccessDto;
import dmp.staffadmin.security.dto.RolePrivilegeAssignationDto;
import dmp.staffadmin.security.model.AppPrivilege;
import dmp.staffadmin.security.services.IUserAuthoritiesDetailsService;

@RestController
//@RequestMapping(path = "/staffadmin/administration")
public class AdministrationRestController 
{
	@Autowired private IUserAuthoritiesDetailsService userAuthoritiesDetailsService;
	@Autowired private AppPrivilegeDao privilegeDao;
	
	@GetMapping(path = "/staffadmin/administration/getPrivilegesOfRoles")
	public List<AppPrivilege> getPrivilegeOfRoles(@RequestParam String idRoles)
	{
		List<Long> listId = new ArrayList<>() ;
		if(idRoles.equals(""))
		{
			return new ArrayList<>() ;
		}
		for (String id:idRoles.split(","))
		{
			listId.add(Long.parseLong(id));
			System.out.println(id);
		}
		return userAuthoritiesDetailsService.getPrivilegesOfRoles(listId);
	}
	
	@GetMapping(path = "/staffadmin/administration/getPrivilegeAccessDtoFromRolesIdsAndUserId")
	public List<PrivilegeAccessDto> getPrivilegeAccessDtoFromRolesIdsAndUserId(@RequestParam Long idUser, @RequestParam String idRoles)
	{
		List<Long> listId = new ArrayList<>() ;
		if(idRoles.equals(""))
		{
			return new ArrayList<>() ;
		}
		for (String id:idRoles.split(","))
		{
			listId.add(Long.parseLong(id));
			System.out.println(id);
		}
		List<AppPrivilege> privileges  = userAuthoritiesDetailsService.getPrivilegesOfRoles(listId);
		return privileges.stream()
						 .map(p->new PrivilegeAccessDto
								 (
										 p.getIdPrivilege(), 
										 idUser, 
										 p.getPrivilegeName(), 
										 userAuthoritiesDetailsService.hasPrivilege(idUser, p))
								 )
						 .collect(Collectors.toList());
	}
	
	@GetMapping(path="/staffadmin/administration/getRolePrivilegesAssignationDto")
	public List<RolePrivilegeAssignationDto> getRolePrivilegeAssignationDtos(@RequestParam long roleId)
	{
		List<AppPrivilege> allPrivileges = privilegeDao.findAll();
		List<AppPrivilege> privileges = privilegeDao.findByRoles_idRole(roleId);
		
		List<RolePrivilegeAssignationDto> privilegeAssignationDtos = 
		allPrivileges.stream()
	    .map
	    (
	    	priv->new RolePrivilegeAssignationDto
	    	(
	    		roleId, 
	    		priv, 
	    		privileges.stream().anyMatch(p->p.getIdPrivilege().longValue()==priv.getIdPrivilege().longValue())
	    	)
	    ).collect(Collectors.toList());
		
		return privilegeAssignationDtos;
	}
	
}
