package dmp.staffadmin.security.dto;

import java.util.Collection;
import java.util.List;

import dmp.staffadmin.security.model.AppPrivilege;
import dmp.staffadmin.security.model.AppRole;
import dmp.staffadmin.security.model.AppUser;
import dmp.staffadmin.security.services.SecurityUser;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor @AllArgsConstructor
public class AuthoritiesDto {
	private SecurityUser securityUser;
	private Collection<AppRole> roles;
	private Collection<AppPrivilege> revokedPrivileges;
	private AppRole defaultRole;
	
}
