package dmp.staffadmin.security.userdetailsservice;

import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Data @NoArgsConstructor @AllArgsConstructor
public class Role
{
	@Id @GeneratedValue
	private Long idRole;
	@Column(unique = true, nullable = false)
	private String role;
	@ManyToMany(mappedBy = "roles", fetch = FetchType.EAGER)
	private Collection<User> users;
	
	@Override
	public String toString()
	{
		return "Role = " + role + " ID = " + idRole;
	}
}
