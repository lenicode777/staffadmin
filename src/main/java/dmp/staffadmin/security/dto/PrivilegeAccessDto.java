package dmp.staffadmin.security.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor @AllArgsConstructor
public class PrivilegeAccessDto 
{
	private long idPrivilege;
	private long idUser;
	private String privilegeName;
	private boolean notRevoked;
	
}