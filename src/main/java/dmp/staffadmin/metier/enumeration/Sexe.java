package dmp.staffadmin.metier.enumeration;

public enum Sexe 
{
	HOMME("HOMME"),
	FEMME("FEMME");

	Sexe(String sexe)
	{
		this.sexe = sexe;
	}
	private String sexe;
	
	public String toString() {return this.sexe;}
}
