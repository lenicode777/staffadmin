package dmp.staffadmin.security.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import dmp.staffadmin.metier.entities.Agent;
import dmp.staffadmin.metier.entities.UniteAdmin;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
//@Deprecated
public class AppUser
{
	@Id
	@GeneratedValue
	private Long idUser;
	@Column(unique = true, nullable = false)
	private String username;
	@Column(nullable = false)
	private String password;
	private String formPassword;
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "USER_ROLE", joinColumns = @JoinColumn(name = "ID_USER"), inverseJoinColumns = @JoinColumn(name = "ID_ROLE"))
	@Fetch(value = FetchMode.SUBSELECT)
	@JsonProperty(access = Access.WRITE_ONLY)
	private Collection<AppRole> roles = new ArrayList<AppRole>();;
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "ID_AGENT")
	private Agent agent;
	private Long idUaChampVisuel;
	private Long defaultRoleId;
	
	@OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
	@Fetch(value = FetchMode.SUBSELECT)
	@JsonProperty(access = Access.WRITE_ONLY)
	private List<UsersRevokedPrivileges> usersRevokedPrivileges;

	//@ManyToMany(fetch = FetchType.EAGER, mappedBy = "revokedUsers")
	//@Fetch(value = FetchMode.SUBSELECT)
	//private List<AppPrivilege> desactivatedPrivileges;

	private boolean active;

	@Transient
	private UniteAdmin uaChampVisuel;

	@Transient
	private AppRole defaultRoleRole;

	@Override
	public String toString()
	{
		return "Username = " + username + " ID = " + idUser;
	}
	
	public boolean hasRole(AppRole role)
	{
		boolean hasRole = false;
		// System.out.println("ROLE A VERIFIER : "+role.getRole()+ " _ "+
		// role.getIdRole());
		// System.out.println("roles vide Oui ou Non = " + roles==null);
		if (role == null)
			return true;
		if (roles == null)
			return false;
		if (roles.isEmpty())
			return false;

		for (AppRole r : roles)
		{
			if (r.getIdRole().longValue() == role.getIdRole().longValue())
			{
				hasRole = true;
				break;
			}
		}

		return hasRole;
	}

	public boolean hasRole(String role)
	{
		boolean hasRole = false;
		if (role == null)
			return true;
		if (roles == null)
			return false;
		if (roles.isEmpty())
			return false;

		for (AppRole r : roles)
		{
			if (r.getRoleName().equals(role))
			{
				hasRole = true;
				break;
			}
		}

		return hasRole;
	}

	public String generateUsername()
	{
		return this.agent.getNom() + this.agent.getIdAgent();
	}

	public String generatePassword()
	{
		Random random = new Random();
		String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ" + "0123456789" + "0123456789"
				+ "abcdefghijklmnopqrstuvwxyz";
		int nbChar = random.nextInt(15 - 10 + 1) + 10;
		StringBuilder passwordStringBuilder = new StringBuilder(nbChar);
		for (int i = 0; i < nbChar; i++)
		{
			int index = random.nextInt((AlphaNumericString.length() - 1) - 0 + 1) + 0;
			passwordStringBuilder.append(AlphaNumericString.charAt(index));
		}
		return passwordStringBuilder.toString();
	}

	public void addRole(AppRole role)
	{
		if (!hasRole(role))
		{
			this.roles.add(role);
		}
	}

	public void removeRoleToUser(AppRole role)
	{
		if (role == null)
			return;
		for (AppRole r : roles)
		{
			if (r.getIdRole() == role.getIdRole())
			{
				roles.remove(r);
				break;
			}
		}
	}

	/*public boolean hasPrivilegeByIdPrivilege(long idPrivilege)
	{
		boolean hasPrivilege = roles.stream().map(AppRole::getPrivileges).flatMap(Collection::stream)
				.map(p -> p.getIdPrivilege().longValue()).collect(Collectors.toList()).contains(idPrivilege);

		boolean isDesactivated = desactivatedPrivileges.stream().map(p -> p.getIdPrivilege())
				.collect(Collectors.toList()).contains(idPrivilege);

		return hasPrivilege && isDesactivated;
	}

	public boolean hasPrivilegeByNomPrivilege(String nomPrivilege)
	{
		boolean hasPrivilege = roles.stream()
				.map(AppRole::getPrivileges).flatMap(Collection::stream)
				.map(p -> p.getPrivilegeName()).collect(Collectors.toList()).contains(nomPrivilege);

		boolean isDesactivated = desactivatedPrivileges.stream().map(p -> p.getPrivilegeName())
				.collect(Collectors.toList()).contains(nomPrivilege);
		return hasPrivilege && isDesactivated;
	}*/
}
