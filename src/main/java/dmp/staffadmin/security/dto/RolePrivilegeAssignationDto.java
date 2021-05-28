package dmp.staffadmin.security.dto;

import java.util.List;

import dmp.staffadmin.security.model.AppPrivilege;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor @AllArgsConstructor
public class RolePrivilegeAssignationDto 
{
	private Long roleId;
	private AppPrivilege privilege;
	private boolean active;
}

@Data @NoArgsConstructor @AllArgsConstructor
class ActivePrivilegeDto
{
	private long privilegeIds;
	private boolean active;
}