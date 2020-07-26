package dmp.staffadmin.security.userdetailsservice;

import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Data @NoArgsConstructor @AllArgsConstructor @ToString
public class Role
{
	@Id @GeneratedValue
	private Long idRole;
	@Column(unique = true, nullable = false)
	private String role;
	@ManyToMany(mappedBy = "roles")
	private Collection<User> users;
}
