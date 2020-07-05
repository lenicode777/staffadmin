package dmp.staffadmin.metier.enumeration;

public enum SituationPresence 
{
	PRESENT("PRESENT"),
	ABSENT("ABSENT"),
	ABSENT_MALADE("ABSENT POUR MALADIE"),
	PERMISSIONNAIRE("PERMISSIONNAIRE"),
	CONGE_ANNUEL("EN CONGE"),
	CONGE_MALADIE("EN CONGE DE MALADIE"),
	CONGE_MATERNITE("EN CONGE DE MATERNITE"),
	FORMATION("EN FORMATION"),
	DETACHEMENT("EN DETACHEMENT"),
	DECEDE("DECEDE"),
	PARTI("PARTI DE LA DMP");
	
	SituationPresence(String situationPresence)
	{
		this.situationPresence = situationPresence;
	}
	private String situationPresence;
	
	public String toString() {return this.situationPresence;}
}
