package dmp.staffadmin.metier.validation;

import java.util.Date;

import org.springframework.web.multipart.MultipartFile;

import dmp.staffadmin.metier.entities.Agent;

public interface IAgentValidation extends IValidation<Agent> 
{
	public boolean isValideDateNaissance(Date dateNaissance);
	public boolean isValideDatePriseService1(Date datePriseService1);
	public boolean isValideDatePriseServiceDMP(Date datePriseServiceDMP);
	public boolean isValideEmail(String email);
	public boolean isValideEmploi(String emploi);
	public boolean isValideFonction(String fonction);
	public boolean isValideGrade(String grade);
	public boolean isValideMatricule(String matricule);
	public boolean isValidetel(String tel);
	public boolean isValidNoteServiceDAAF(MultipartFile file, String typeErrorMsg, String sizeErrorMsg);
	public boolean isValidNoteServiceDGBF(MultipartFile file, String typeErrorMsg, String sizeErrorMsg);
	public boolean isValidCertificatService1(MultipartFile file, String typeErrorMsg, String sizeErrorMsg);
	public boolean isValidArreteNomination(MultipartFile file, String typeErrorMsg, String sizeErrorMsg);
	public boolean isValidDecisionAttente(MultipartFile file, String typeErrorMsg, String sizeErrorMsg);
	public boolean isValidCv(MultipartFile file, String typeErrorMsg, String sizeErrorMsg);
	public boolean isValidPieceIdentite(MultipartFile file, String typeErrorMsg, String sizeErrorMsg);
	public boolean isValidPhoto(MultipartFile file, String typeErrorMsg, String sizeErrorMsg);
}
