package dmp.staffadmin.metier.validation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import dmp.staffadmin.dao.IAgentDao;
import dmp.staffadmin.dao.IEmploiDao;
import dmp.staffadmin.dao.IFonctionDao;
import dmp.staffadmin.dao.IGradeDao;
import dmp.staffadmin.metier.entities.Agent;
import dmp.staffadmin.metier.entities.Emploi;
import dmp.staffadmin.metier.interfaces.IEmploiMetier;
import dmp.staffadmin.metier.interfaces.IFonctionMetier;
import dmp.staffadmin.metier.interfaces.IGradeMetier;
import dmp.staffadmin.utilities.DateManager;

@Service
public class AgentValidation implements IAgentValidation
{
	@Autowired private IAgentDao agentDao;
	@Autowired private IGradeDao gradeDao;
	@Autowired private IFonctionDao fonctionDao;
	@Autowired private IEmploiDao emploiDao;
	@Autowired private IGradeMetier gradeMetier;
	@Autowired private IFonctionMetier fonctionMetier;
	@Autowired private IEmploiMetier emploiMetier;
	private DateManager dateManager = new DateManager();
	private Date today = new Date();
	private final List<String> imageAuthorizedTypes = Arrays.asList("image/jpeg","image/png");
	private final List<String> docAuthorizedTypes = Arrays.asList("application/pdf", "image/jpeg","image/png");
	private final List<String> pdfAuthorizedTypes = Arrays.asList("application/pdf");
	private long fileMaxSize = 1000000;
	@Override
	public boolean isValide(Agent agent) 
	{
		validate(agent) ;
		return true;
	}

	@Override
	public void validate(Agent agent) 
	{
		isValideDateNaissance(agent.getDateNaissance());
		isValideDatePriseService1(agent.getDatePriseService1());
		isValideEmploi(agent.getEmploi().getNomEmploi());
		isValideFonction(agent.getFonction().getNomFonction());
		isValideGrade(agent.getGrade().getNomGrade());
		isValidNoteServiceDAAF(agent.getNoteServiceDAAFFile(), "Document PDF requis", "Taille maximale autorisée : "+fileMaxSize);
		isValidNoteServiceDGBF(agent.getNoteServiceDGBFFile(), "Document PDF requis", "Taille maximale autorisée : "+fileMaxSize);
		isValidCertificatService1(agent.getCertificatService1File(), "Document PDF requis", "Taille maximale autorisée : "+fileMaxSize);
		isValidArreteNomination(agent.getArreteNominationFile(), "Document PDF requis", "Taille maximale autorisée : "+fileMaxSize);
		isValidDecisionAttente(agent.getDecisionAttenteFile(), "Document PDF requis", "Taille maximale autorisée : "+fileMaxSize);
		isValidCv(agent.getCvFile(), "Document PDF requis", "Taille maximale autorisée : "+fileMaxSize);
		isValidPieceIdentite(agent.getPieceIdentiteFile(), "* Fichiers autorisé : PDF, JPEG, PNG", "Taille maximale autorisée : "+fileMaxSize);
		isValidPhoto(agent.getPhotoFile(), "* Fichiers autorisé : JPEG, PNG", "Taille maximale autorisée : "+fileMaxSize);	
		System.out.println("14===========PHOTO VALIDE==============");
	}
	
	@Override
	public boolean isValideDateNaissance(Date dateNaissance)
	{
		Date dateNaissanceMax = dateManager.addDates(today, -20, Calendar.YEAR);
		if(dateNaissance.after(dateNaissanceMax)) 
		{
			throw new RuntimeException("L'agent doit être agé d'au moins 20 ans.");
		}
		System.out.println("===========DATE NAISSANCE VALIDE==============");
		return true;
	}

	@Override
	public boolean isValideDatePriseService1(Date datePriseService1) 
	{
		Date dateMin = dateManager.addDates(today, -50, Calendar.YEAR);
		if(datePriseService1.before(dateMin)) 
		{
			throw new RuntimeException("La date de prise de service ne peut être antérieur au ." + dateMin);
		}
		else if(datePriseService1.after(today))
		{
			throw new RuntimeException("La date de prise de service ne peut être ultérieur à aujourd'hui.");
		}
		return true;
	}

	@Override
	public boolean isValideDatePriseServiceDMP(Date datePriseServiceDMP) 
	{
		System.out.println();
		 if(isValideDatePriseService1(datePriseServiceDMP)) 
		 {
			 System.out.println("===========DatePriseServiceDMP VALIDE==============");
			 return true;
		}
		 else return false;
	}

	@Override
	public boolean isValideEmail(String email)
	{
		return false;
	}

	@Override
	public boolean isValideEmploi(String emploi)
	{
		System.out.println("===================RECHERCHE DE L'EMPLOI BY NAME = " + emploi+"=================");
		Emploi emploi0 =  emploiMetier.findByNom(emploi);
		System.out.println();
		System.out.println("================IdEmploi = "+emploi0.getIdEmploi());
		System.out.println("================NomEmploi = "+emploi0.getNomEmploi());
		System.out.println();
		if( emploiMetier.findByNom(emploi.toUpperCase()) == null ) 
		{
			System.out.println("===========ECHEC VALIDATION EMPLOI==============");
			throw new RuntimeException("Emploi invalide");
		}
		System.out.println("===========EMPLOI VALIDE==============");
		return true;
	}

	@Override
	public boolean isValideFonction(String fonction)
	{
		if(fonctionMetier.findByNom(fonction.toUpperCase())==null )
		{
			throw new RuntimeException("Fonction invalide");
		}
		return true;
	}

	@Override
	public boolean isValideGrade(String grade)
	{
		if(gradeMetier.findByNom(grade.toUpperCase())==null) 
		{
			throw new RuntimeException("Grade invalide");
		}
		return true;
	}

	@Override
	public boolean isValideMatricule(String matricule)
	{
		return false;
	}

	@Override
	public boolean isValidetel(String tel)
	{
		return false;
	}

	@Override
	public boolean isValidNoteServiceDAAF(MultipartFile file, String typeErrorMsg, String sizeErrorMsg) 
	{
		return checkFileValidity(file, pdfAuthorizedTypes, typeErrorMsg, sizeErrorMsg);
	}

	@Override
	public boolean isValidNoteServiceDGBF(MultipartFile file, String typeErrorMsg, String sizeErrorMsg) 
	{
		return checkFileValidity(file, pdfAuthorizedTypes, typeErrorMsg, sizeErrorMsg);
	}

	@Override
	public boolean isValidCertificatService1(MultipartFile file, String typeErrorMsg, String sizeErrorMsg) 
	{
		return checkFileValidity(file, pdfAuthorizedTypes, typeErrorMsg, sizeErrorMsg);
	}

	@Override
	public boolean isValidArreteNomination(MultipartFile file, String typeErrorMsg, String sizeErrorMsg) 
	{
		return checkFileValidity(file, pdfAuthorizedTypes, typeErrorMsg, sizeErrorMsg);
	}

	@Override
	public boolean isValidDecisionAttente(MultipartFile file, String typeErrorMsg, String sizeErrorMsg) 
	{
		return checkFileValidity(file, pdfAuthorizedTypes, typeErrorMsg, sizeErrorMsg);
	}

	@Override
	public boolean isValidCv(MultipartFile file, String typeErrorMsg, String sizeErrorMsg) 
	{
		return checkFileValidity(file, pdfAuthorizedTypes, typeErrorMsg, sizeErrorMsg);
	}

	@Override
	public boolean isValidPieceIdentite(MultipartFile file, String typeErrorMsg, String sizeErrorMsg) 
	{
		return checkFileValidity(file, docAuthorizedTypes, typeErrorMsg, sizeErrorMsg);
	}

	@Override
	public boolean isValidPhoto(MultipartFile file, String typeErrorMsg, String sizeErrorMsg) 
	{
		return checkFileValidity(file, imageAuthorizedTypes ,typeErrorMsg ,sizeErrorMsg);
	}

	private boolean checkFileValidity(MultipartFile file, List<String> authorizedTypes , String typeErrorMsg, String sizeErrorMsg) 
	{
		if(!authorizedTypes.contains(file.getContentType()))
		{
			System.out.println(file.getContentType());
			throw new RuntimeException(typeErrorMsg);
		}
	
		if(file.getSize() > fileMaxSize)
		{
			System.out.println(file.getSize());
			throw new RuntimeException(sizeErrorMsg);
		}
		return true;
	}
}
