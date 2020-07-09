package dmp.staffadmin.metier.interfaces;

public interface IAgentExistingRestController 
{
	public boolean existingEmail(String email);
	public boolean existingTel(String tel);
	public boolean existingNumPiece(String numPiece);
	public boolean existingMatricule(String matricule);
}
