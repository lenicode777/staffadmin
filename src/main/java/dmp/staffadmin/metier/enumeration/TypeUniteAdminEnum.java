package dmp.staffadmin.metier.enumeration;

public enum TypeUniteAdminEnum 
{
	MINISTERE("Ministère"),
	DIRECTION_GENERALE("Direction Générale"),
	DIRECTION_CENTRALE("Direction Centrale"),
	SOUS_DIRECTION("Sous Direction"),
	DIRECTION_REGIONALE("Direction Régionale"),
	SERVICE("Service"),
	CABINET_MINISTERE("Cabinet de Ministère"),
	CABINET_DG("Cabinet de Direction Générale"),
	CELLULE("Cellule");
	
	private String nomType="";
	TypeUniteAdminEnum(String nomType)
	{
		this.nomType = nomType;
	}
	public String toString() {return nomType;}
}
