package dmp.staffadmin.metier.entities;

import java.util.Collection;

import javax.persistence.Entity;
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
	@Id
	private String role;
	@ManyToMany(mappedBy = "roles")
	private Collection<User> users;
}
