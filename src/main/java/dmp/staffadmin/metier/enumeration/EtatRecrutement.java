package dmp.staffadmin.metier.enumeration;

public enum EtatRecrutement 
{
	ATTENTE_MUTATION("EN ATTENTE DE MUTATION"),
	ATTENTE_CERTIFICAT_SERVICE_DMP("EN ATTENTE DU CERTIFICAT DE PRISE DE SERVICE DMP"),
	ATTENTE_CERTIFICAT_SERVICE_DGBF("EN ATTENTE DU CERTIFICAT DE PRISE DE SERVICE DGBF"),
	ATTENTE_CERTIFICAT_SERVICE_DAF("EN ATTENTE DDU CERTIFICAT DE PRISE DE SERVICE DAF"),
	RECRUTE("RECRUTE");
	private String etatRecrutement ="";
	EtatRecrutement(String etatRecrutement)
	{
		this.etatRecrutement = etatRecrutement;
	}
	public String toString() {return etatRecrutement;}
}
