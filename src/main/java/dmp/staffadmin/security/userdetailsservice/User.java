package dmp.staffadmin.security.userdetailsservice;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Random;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Transient;

import dmp.staffadmin.metier.entities.Agent;
import dmp.staffadmin.metier.entities.UniteAdmin;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User
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
	private Collection<Role> roles = new ArrayList<Role>();;
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "ID_AGENT")
	private Agent agent;
	private Long idUaChampVisuel;

	private boolean active;

	@Transient
	private UniteAdmin uaChampVisuel;

	@Override
	public String toString()
	{
		return "Username = " + username + " ID = " + idUser;
	}

	public boolean hasRole(Role role)
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

		for (Role r : roles)
		{
			if (r.getIdRole() == role.getIdRole())
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

		for (Role r : roles)
		{
			if (r.getRole().equals(role))
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

	public void addRole(Role role)
	{
		if (!hasRole(role))
		{
			this.roles.add(role);
		}
	}

	public void removeRoleToUser(Role role)
	{
		if (role == null)
			return;
		for (Role r : roles)
		{
			if (r.getIdRole() == role.getIdRole())
			{
				roles.remove(r);
				break;
			}
		}
	}
}
