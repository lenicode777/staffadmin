package dmp.staffadmin.security.dto;

import java.util.List;

import dmp.staffadmin.security.model.AppPrivilege;
import dmp.staffadmin.security.model.AppRole;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor @AllArgsConstructor
public class UserAccessDto 
{
	private long idUser;
	private long idUaChampVisuel;
	private boolean active;
	private List<Long> rolesIds;
	private List<PrivilegeAccessDto> privilegeAccessDtos;
}
