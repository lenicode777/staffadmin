package dmp.staffadmin.metier.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor @AllArgsConstructor
public class AffectationException extends RuntimeException
{
	private String message;
}
