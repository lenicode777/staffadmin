package dmp.staffadmin.metier.enumeration;

public enum EtatActe 
{
	INITIALISATION("Initialisation"),
	VALIDATIONSD("En attente de validation du SD"),
	SIGNATUREDMP("En attente de validation du Directeur"),
	SIGNATUREDGBF("En attente de signatue du DGMP"),
	REFUSE("Refusé"),
	ACCORDE("Accordé");
	private String etatAvancement="";
	EtatActe(String etatAvancement)
	{
		this.etatAvancement = etatAvancement;
	}
	public String toString() {return etatAvancement;}
}
