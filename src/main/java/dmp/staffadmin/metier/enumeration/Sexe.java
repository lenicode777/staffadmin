package dmp.staffadmin.metier.enumeration;

public enum Sexe 
{
	MASCULIN("M"),
	FEMININ("F");

	Sexe(String sexe)
	{
		this.sexe = sexe;
	}
	private String sexe;
	
	public String toString() {return this.sexe;}
}
