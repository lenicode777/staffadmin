package dmp.staffadmin.metier.interfaces;

public interface IAgentExistingRestController 
{
	public boolean existingEmail(String email);
	public boolean existingEmail(String email, Long idAgent);
	
	public boolean existingTel(String tel);
	public boolean existingTel(String tel, Long idAgent);
	
	public boolean existingNumPiece(String numPiece);
	public boolean existingNumPiece(String numPiece, Long idAgent);
	
	public boolean existingMatricule(String matricule);
	public boolean existingMatricule(String matricule, Long idAgent);
}
