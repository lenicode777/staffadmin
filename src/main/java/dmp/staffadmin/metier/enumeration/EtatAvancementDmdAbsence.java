package dmp.staffadmin.metier.enumeration;

public enum EtatAvancementDmdAbsence 
{
	INITIALISATION("INITIALISATION"),
	VALIDATIONSD("EN ATTENTE DE VALIDATION DU SD"),
	ACCORDDMP("EN ATTENTE DE L'ACCORD DU DMP");

	private String etatAvancement="";
	EtatAvancementDmdAbsence(String etatAvancement)
	{
		this.etatAvancement = etatAvancement;
	}
	public String toString() {return etatAvancement;}
}
