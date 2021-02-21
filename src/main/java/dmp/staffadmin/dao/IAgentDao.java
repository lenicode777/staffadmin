package dmp.staffadmin.dao;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import dmp.staffadmin.metier.entities.Agent;
import dmp.staffadmin.metier.entities.Emploi;
import dmp.staffadmin.metier.entities.Fonction;
import dmp.staffadmin.metier.entities.Grade;
import dmp.staffadmin.metier.entities.Post;
import dmp.staffadmin.metier.entities.UniteAdmin;
import dmp.staffadmin.security.userdetailsservice.User;

public interface IAgentDao extends JpaRepository<Agent, Long> 
{
	public List<Agent> findByEmail(String email);
	public List<Agent> findByTel(String tel);
	public List<Agent> findByNumPiece(String numPiece);
	public Agent findByMatricule(String matricule);
	public List<Agent> findByActiveTrue();
	public List<Agent> findByActiveFalse();
	
	//public List<Agent> findByDateDepartRetraite(Date dateDepartRetraite);
	//public List<Agent> findByDateDepartRetraiteBefore(Date dateDepartRetraite);
	//public List<Agent> findByDateDepartRetraiteAfter(Date dateDepartRetraite);

	public List<Agent> findByDateNaissance(Date dateNaissance);
	public List<Agent> findByDateNaissanceBefore(Date dateNaissance);
	public List<Agent> findByDateNaissanceAfter(Date dateNaissance);
	
	public List<Agent> findByDatePriseService1(Date datePriseService1);
	public List<Agent> findByDatePriseService1Before(Date datePriseService1);
	public List<Agent> findByDatePriseService1After(Date datePriseService1);
	
	public List<Agent> findByDatePriseServiceDMP(Date datePriseServiceDMP);
	public List<Agent> findByDatePriseServiceDMPBefore(Date datePriseServiceDMP);
	public List<Agent> findByDatePriseServiceDMPAfter(Date datePriseServiceDMP);
	
	public List<Agent> findByEmploi(Emploi emploi);
	public List<Agent> findByFonction(Fonction fonction);
	public List<Agent> findByGrade(Grade grade);
	public List<Agent> findByFixeBureau(String fix);
	public List<Agent> findByLieuNaissance(String lieuNaissance);
	public List<Agent> findByLieuNaissanceContains(String lieuNaissance);
	public List<Agent> findByNom(String nom);
	public List<Agent> findByNomContains(String nom);
	public List<Agent> findByPrenom(String prenom);
	public List<Agent> findByPrenomContains(String prenom);
	public List<Agent> findByPosition(String position);
	public List<Agent> findByPost(Post post);
	public List<Agent> findBySexe(String sexe);
	public List<Agent> findBySituationPresence(String situationPresence);
	public List<Agent> findByStatutEmploye(String statutEmploye);
	public List<Agent> findByTutelleDirecte(UniteAdmin tutelleDirecte);
	
	public Agent findByUser(User user);
}
