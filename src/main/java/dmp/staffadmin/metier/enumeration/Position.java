package dmp.staffadmin.metier.enumeration;

public enum Position 
{
	ACTIVITE("EN ACTIVITE A LA DMP"),
	DETACHEMENT("EN DETACHEMENT"),
	DISPONIBILITE("EN DISPONIBILITE"),
	DRAPEAUX("SOUS LES DRAPEAUX");
	
	Position(String position)
	{
		this.position = position;
	}
	private String position;
	
	public String toString() {return this.position;}
}
