package dmp.staffadmin.metier.services.interfaces;

public interface IAgentExistingRestController 
{
	public boolean existingEmail(String email);
	public boolean existingEmail(String email, Long idAgent);
	
	public boolean existingEmailPro(String emailPro);
	public boolean existingEmailPro(String emailPro, Long idAgent);
	
	public boolean existingTel(String tel);
	public boolean existingTel(String tel, Long idAgent);
	
	public boolean existingNumPiece(String numPiece);
	public boolean existingNumPiece(String numPiece, Long idAgent);
	
	public boolean existingMatricule(String matricule);
	public boolean existingMatricule(String matricule, Long idAgent);
	
	public boolean existingNumBadge(String numBadge);
	public boolean existingNumBadge(String numBadge, Long idAgent);
}
