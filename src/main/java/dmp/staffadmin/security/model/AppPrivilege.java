package dmp.staffadmin.security.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

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
public class AppPrivilege
{	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idPrivilege;
	@Column(unique = true) 
	private String privilegeName;
	
	@OneToMany(mappedBy = "revokedPrivilege")
	@JsonProperty(access = Access.WRITE_ONLY)
	private List<UsersRevokedPrivileges> usersRevokedPrivileges;
	
	@ManyToMany(mappedBy = "privileges")
	@Fetch(FetchMode.SUBSELECT)
	@JsonProperty(access = Access.WRITE_ONLY)
	private List<AppRole> roles;
	
	//@ManyToMany(fetch = FetchType.EAGER)
	//@JoinTable(name = "USERS_REVOKEDPRIVILEGES", joinColumns = @JoinColumn(name = "id_privilege", referencedColumnName = "idPrivilege"), inverseJoinColumns = @JoinColumn(name = "id_user", referencedColumnName = "idUser"))
	//@Fetch(FetchMode.SUBSELECT)
	//@JsonProperty(access = Access.WRITE_ONLY)
	//private List<AppUser> revokedUsers;
}	
	