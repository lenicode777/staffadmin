package dmp.staffadmin.controllers.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor @AllArgsConstructor
public class SelectRoleDto 
{
	private Long idUser;
	private Long selectedRoleId;
}
