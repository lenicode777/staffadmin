package dmp.staffadmin.metier.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor @AllArgsConstructor
public class AuthorityException extends RuntimeException
{
	private String message;
}
