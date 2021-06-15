package dmp.staffadmin.metier.enumeration;

public enum PositionEnum
{
	ACTIVITE("EN ACTIVITE A LA DGMP"),
	DETACHEMENT("EN DETACHEMENT"),
	DISPONIBILITE("EN DISPONIBILITE"),
	DRAPEAUX("SOUS LES DRAPEAUX"),
	PARTI("Parti de la DGMP");
	
	PositionEnum(String position)
	{
		this.position = position;
	}
	private String position;
	
	public String toString() {return this.position;}
}
