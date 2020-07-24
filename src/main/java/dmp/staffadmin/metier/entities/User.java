package dmp.staffadmin.metier.entities;

import java.util.Collection;
import java.util.Random;

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
	
	public String getUsername()
	{
		return generateUsername();
	}
	public void setUsername()
	{
		this.username = this.getUsername();
	}
	
	private String generateUsername()
	{
		return this.agent.getNom()+this.agent.getIdAgent();
	}
	
	public String getPassword()
	{
		return generatePassword();
	}
	
	public void setPassword()
	{
		this.password = this.getPassword();
	}
	
	
	private String generatePassword()
	{
		Random random = new Random();
		String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ" + "0123456789" + "0123456789" + "abcdefghijklmnopqrstuvxyz";
		int nbChar = random.nextInt(15-10+1)+10;
		StringBuilder passwordStringBuilder = new StringBuilder(nbChar); 
		for(int i = 0 ; i< nbChar ; i++)
		{
			int index = random.nextInt(AlphaNumericString.length()-0+1)+0;
			passwordStringBuilder.append(AlphaNumericString.charAt(index));
		}
		return passwordStringBuilder.toString();
	}
}
