package dmp.staffadmin.metier.enumeration;

public enum EtatActe 
{
	INITIALISATION("INITIALISATION"),
	VALIDATIONSD("EN ATTENTE DE VALIDATION DU SD"),
	SIGNATUREDMP("EN ATTENTE DE VALIDATION DU DMP"),
	SIGNATUREDGBF("EN ATTENTE DE SIGNATURE DU DGBF"),
	REFUSE("REFUSE"),
	ACCORDE("ACCORDE");
	private String etatAvancement="";
	EtatActe(String etatAvancement)
	{
		this.etatAvancement = etatAvancement;
	}
	public String toString() {return etatAvancement;}
}
