package dmp.staffadmin.metier.validation;

import java.util.Calendar;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
		System.out.println("1===========DATE NAISSANCE VALIDE==============");
		isValideDatePriseService1(agent.getDatePriseService1());
		System.out.println("2===========DATE DATE SERVICE1 VALIDE==============");
		//isValideDatePriseServiceDMP(agent.getDatePriseServiceDMP());
		//System.out.println("3===========DATE SERVICE DMP VALIDE==============");
		isValideEmploi(agent.getEmploi().getNomEmploi());
		System.out.println("4===========EMPLOI VALIDE==============");
		isValideFonction(agent.getFonction().getNomFonction());
		System.out.println("5===========FONCTION VALIDE==============");
		isValideGrade(agent.getGrade().getNomGrade());
		System.out.println("6===========GRADE VALIDE==============");
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
		System.out.println("===========DatePriseService1 VALIDE==============");
		return true;
	}

	@Override
	public boolean isValideDatePriseServiceDMP(Date datePriseServiceDMP) 
	{
		System.out.println();
		 if(isValideDatePriseService1(datePriseServiceDMP)) {System.out.println("===========DatePriseServiceDMP VALIDE==============");return true;}
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

}
