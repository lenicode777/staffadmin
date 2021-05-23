package dmp.staffadmin.security.dto.factory;

import java.util.Collection;
import java.util.List;

import org.springframework.web.bind.annotation.RequestParam;

import dmp.staffadmin.metier.entities.Agent;
import dmp.staffadmin.security.dto.UserAccessDto;
import dmp.staffadmin.security.model.AppPrivilege;

public interface IUserAccessDtoFactory 
{
	public UserAccessDto createUserAccessDto(Agent agent, List<Long> privilegeIds);
	public UserAccessDto createUserAccessDto(Agent agent, Collection<AppPrivilege> privileges);
	public UserAccessDto createUserAccessDto(Long idUser,
											 String idRoles, 
											 Long idUaChampVisuel,
											 boolean active,
											 String notRevokedPrivilegeIds,
											 String privilegeIds);
}
