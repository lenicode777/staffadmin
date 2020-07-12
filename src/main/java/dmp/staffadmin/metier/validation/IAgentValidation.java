package dmp.staffadmin.metier.validation;

import java.util.Date;

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
}
