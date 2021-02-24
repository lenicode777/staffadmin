package dmp.staffadmin.metier.enumeration;

public enum EtatRecrutement 
{
	ATTENTE_MUTATION("EN attente de mutation"),
	ATTENTE_CERTIFICAT_SERVICE_DIRECTION("En attente du certificat de prise de service Direction"),
	ATTENTE_CERTIFICAT_SERVICE_DGMP("En attente du certificat de prise de service DGMP"),
	//ATTENTE_CERTIFICAT_SERVICE_DAF("En attente du certificat de prise de service DAF"),
	EN_SERVICE("En service");
	private String etatRecrutement ="";
	EtatRecrutement(String etatRecrutement)
	{
		this.etatRecrutement = etatRecrutement;
	}
	public String toString() {return etatRecrutement;}
}
