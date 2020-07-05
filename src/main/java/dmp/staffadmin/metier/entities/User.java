package dmp.staffadmin.metier.entities;

import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Data @NoArgsConstructor @AllArgsConstructor @ToString
public class User 
{
	@Id
	private String username;
	private String password;
	@ManyToMany @JoinTable(name = "USER_ROLE", joinColumns = @JoinColumn(name="ID_USER"), inverseJoinColumns = @JoinColumn(name="ID_ROLE"))
	private Collection<Role> roles;
	@OneToOne @JoinColumn(name = "ID_AGENT")
	private Agent agent; 	
}
