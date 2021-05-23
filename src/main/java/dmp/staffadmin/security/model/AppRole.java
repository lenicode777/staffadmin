package dmp.staffadmin.security.model;

import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
//@Deprecated
public class AppRole
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idRole;
	@Column(unique = true, nullable = false)
	private String roleName;
	@Column(unique = true, nullable = false)
	private String roleSigle;
	@ManyToMany(mappedBy = "roles", fetch = FetchType.EAGER)
	@Fetch(value = FetchMode.SUBSELECT)
	@JsonProperty(access = Access.WRITE_ONLY)
	private Collection<AppUser> users;

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "ROLE_PRIVILEGE", joinColumns = @JoinColumn(name = "ID_ROLE", referencedColumnName = "idRole"), inverseJoinColumns = @JoinColumn(name = "ID_PRIVILEGE", referencedColumnName = "idPrivilege"))
	@Fetch(value = FetchMode.SUBSELECT)
	@JsonProperty(access = Access.WRITE_ONLY)
	private Collection<AppPrivilege> privileges;

	@Override
	public String toString()
	{
		return "Role = " + roleName + " ID = " + idRole;
	}
}
