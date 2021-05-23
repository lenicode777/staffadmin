package dmp.staffadmin.security.dto.factory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import dmp.staffadmin.metier.entities.Agent;
import dmp.staffadmin.security.dao.AppPrivilegeDao;
import dmp.staffadmin.security.dto.PrivilegeAccessDto;
import dmp.staffadmin.security.dto.UserAccessDto;
import dmp.staffadmin.security.model.AppPrivilege;
import dmp.staffadmin.security.services.IUserAuthoritiesDetailsService;

@Component
public class UserAccessDtoFactory implements IUserAccessDtoFactory
{
	@Autowired private IUserAuthoritiesDetailsService userAuthoritiesDetailsService;
	@Autowired private AppPrivilegeDao privilegeDao;
	@Autowired private IPrivilegeAccessDtoFactory privilegeAccessDtoFactory;
	
	@Override
	public UserAccessDto createUserAccessDto(Agent agent, List<Long> privilegeIds) 
	{
		UserAccessDto userAccessDto = new UserAccessDto();
		userAccessDto.setActive(agent.getUser().isActive());
		userAccessDto.setIdUser(agent.getUser().getIdUser());
		userAccessDto.setIdUaChampVisuel(agent.getUser().getIdUaChampVisuel());
		
		List<AppPrivilege> privileges = privilegeIds.stream()
													.map(id->privilegeDao.findById(id).get())
													.collect(Collectors.toList());
		
		List<PrivilegeAccessDto> privilegeAccessDtos = privileges.stream()
																 .map(p->new PrivilegeAccessDto(p.getIdPrivilege(),agent.getUser().getIdUser() , p.getPrivilegeName(), userAuthoritiesDetailsService.hasPrivilege(agent.getUser().getUsername(), p)))
																 .collect(Collectors.toList());
		
		userAccessDto.setPrivilegeAccessDtos(privilegeAccessDtos);
		userAccessDto.setRolesIds(new ArrayList<>());
		return userAccessDto;
	}

	@Override
	public UserAccessDto createUserAccessDto(Agent agent, Collection<AppPrivilege> privileges) 
	{
		List<Long> privilegeIds = privileges.stream().map(AppPrivilege::getIdPrivilege).collect(Collectors.toList());
		return createUserAccessDto(agent, privilegeIds);
	}

	@Override
	public UserAccessDto createUserAccessDto(Long idUser0, 
											 String idRoles0, 
											 Long idUaChampVisuel0, 
											 boolean active0,
											 String notRevokedPrivilegeIds0, 
											 String privilegeIds0) 
	{
		System.out.println("=========ACTIVE==========");
		System.out.println("Active = " + active0);
		System.out.println("=========ACTIVE==========");
		
		Long idUser = idUser0;
		Long idUaChampVisuel = idUaChampVisuel0;
		boolean active = active0;
		
		List<Long> idRoles = Arrays.asList((idRoles0.split(",")))
				  .stream().map(id->Long.parseLong(id))
				  .collect(Collectors.toList());
		
		List<Long> notRevokedPrivilegeIds = Arrays.asList((notRevokedPrivilegeIds0.split(",")))
												  .stream().map(id->Long.parseLong(id))
												  .collect(Collectors.toList());
		List<Long> privilegeIds = Arrays.asList((privilegeIds0.split(",")))
				  .stream().map(id->Long.parseLong(id))
				  .collect(Collectors.toList());
		
		UserAccessDto userAccessDto = new UserAccessDto();
		userAccessDto.setIdUser(idUser);
		userAccessDto.setActive(active);
		userAccessDto.setRolesIds(idRoles);
		userAccessDto.setIdUaChampVisuel(idUaChampVisuel);
		userAccessDto.setPrivilegeAccessDtos(privilegeAccessDtoFactory.createListOfPrivilegeAccessDto(privilegeIds, notRevokedPrivilegeIds));
		
		
		return userAccessDto;
	}
}
