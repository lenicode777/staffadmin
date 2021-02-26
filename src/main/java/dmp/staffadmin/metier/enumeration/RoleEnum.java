package dmp.staffadmin.metier.enumeration;

public enum RoleEnum 
{
	AGENT("AGENT"),
	CHEF_DE_SERVICE("CS"),
	CHEF_DE_SERVICE_RATTACHE("CSR"),
	SAF("SAF"),
	DIRECTEUR_REGIONAL("DR"),
	DIRECTEUR("D"),
	DIRECTEUR_GENERAL("DG"),
	SOUS_DIRECTEUR("SD"),
	RESPONSABLE("RESPONSABLE");
	
	private String role;
	RoleEnum (String role)
	{
		this.role = role;
	}
	public String toString() {return role;}
}
