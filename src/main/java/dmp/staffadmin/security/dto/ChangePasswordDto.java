package dmp.staffadmin.security.dto;

import dmp.staffadmin.security.model.AppUser;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor @AllArgsConstructor
public class ChangePasswordDto
{
	private AppUser user;
	private String oldPassword;
	private String newPassword;
	private String confirmPassword;
}