package dmp.staffadmin.metier.enumeration;

public enum UniteAdminEnum 
{
	DGMP("DGMP"),
	SMGP("SMGP"),
	DSICF("DSICF"),
	DES("DES"),
	DPO("DPO"),
	DRRP("DRRP"),
	CABINET_DGMP("CDGMP");
	
	private String sigleUniteAdmin;
	UniteAdminEnum(String sigleUniteAdmin)
	{
		this.sigleUniteAdmin = sigleUniteAdmin;
	}
	public String toString() {return sigleUniteAdmin;}
}
