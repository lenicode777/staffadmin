package dmp.staffadmin.security;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor @AllArgsConstructor
public class UserDetailsServiceException extends RuntimeException
{
	private String message;
}
