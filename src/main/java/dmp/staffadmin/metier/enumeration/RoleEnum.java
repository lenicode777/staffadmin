package dmp.staffadmin.metier.enumeration;

public enum RoleEnum
{
	AGENT("AGT"), CHEF_DE_SERVICE("CS"), DRH("DRH"), DIRECTEUR_REGIONAL("DR"),
	DIRECTEUR_CENTRAL("DC"), DIRECTEUR_GENERAL("DG"), SOUS_DIRECTEUR("SD"), RESPONSABLE("R"),
	GP("GP"), ADMINISTRATEUR("ADMIN"), SUPER_ADMIN("SUPER ADMIN");

	private String role;

	RoleEnum(String role)
	{
		this.role = role;
	}

	@Override
	public String toString()
	{
		return role;
	}
}
