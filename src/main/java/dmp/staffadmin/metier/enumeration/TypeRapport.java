package dmp.staffadmin.metier.enumeration;

public enum TypeRapport 
{
	DEMANDE_CONGE("DEMANDE DE CONGE ANNUEL"),
	DEMANDE_ABSENCE("DEMANDE D'AUTORISATION D'ABSENCE"),
	CERTIFICAT_PRESENCE("CERTIFICAT DE PRESENCE"),
	ATTESTATION_TRAVAIL("ATTESTATION DE TRAVAIL");
	
	TypeRapport(String type)
	{
		this.type = type;
	}
	private String type;
	
	public String toString() {return this.type;}
}
