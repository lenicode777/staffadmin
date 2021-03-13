package dmp.staffadmin.metier.enumeration;

public enum ModeEnum 
{
	
	FROM_SINGLE_AGENT("from-single-agent"),
	FROM_UNITE_ADMIN("from-unite-admin"),
	NEW("new"),
	UPDATE("update"),
	EDIT("edit");

	ModeEnum (String mode)
	{
		this.mode = mode;
	}
	private String mode;
	
	public String toString() {return this.mode;}
}
