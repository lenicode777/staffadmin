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
import dmp.staffadmin.security.model.AppUser;

public interface IAgentDao extends JpaRepository<Agent, Long> 
{
	List<Agent> findByEmail(String email);
	List<Agent> findByTel(String tel);
	List<Agent> findByNumPiece(String numPiece);
	Agent findByMatricule(String matricule);
	List<Agent> findByActiveTrue();
	List<Agent> findByActiveFalse();



	List<Agent> findByDateNaissance(Date dateNaissance);
	List<Agent> findByDateNaissanceBefore(Date dateNaissance);
	List<Agent> findByDateNaissanceAfter(Date dateNaissance);
	
	List<Agent> findByDatePriseService1(Date datePriseService1);
	List<Agent> findByDatePriseService1Before(Date datePriseService1);
	List<Agent> findByDatePriseService1After(Date datePriseService1);
	
	List<Agent> findByDatePriseServiceDGMP(Date datePriseServiceDGMP);
	List<Agent> findByDatePriseServiceDGMPBefore(Date datePriseServiceDGMP);
	List<Agent> findByDatePriseServiceDGMPAfter(Date datePriseServiceDGMP);
	
	List<Agent> findByEmploi(Emploi emploi);
	List<Agent> findByFonction(Fonction fonction);
	List<Agent> findByGrade(Grade grade);
	List<Agent> findByFixeBureau(String fix);
	List<Agent> findByLieuNaissance(String lieuNaissance);
	List<Agent> findByLieuNaissanceContains(String lieuNaissance);
	List<Agent> findByNom(String nom);
	List<Agent> findByNomContains(String nom);
	List<Agent> findByPrenom(String prenom);
	List<Agent> findByPrenomContains(String prenom);
	List<Agent> findByPosition(String position);
	List<Agent> findByPost(Post post);
	List<Agent> findBySexe(String sexe);
	List<Agent> findBySituationPresence(String situationPresence);
	List<Agent> findByEtatRecrutement(String etatRecrutement);
	List<Agent> findByTutelleDirecte(UniteAdmin tutelleDirecte);
	List<Agent> findByStatutAgent(String statutAgent);
	
	Agent findByUser(AppUser user);
	Agent findByEmailPro(String emailPro);
	Agent findByNumBadge(String numBadge);
	
	
	boolean existsByUser(AppUser user);
	boolean existsByUserIdUser(Long idUser);
	boolean existsByEmailPro(String emailPro);
	boolean existsByNumBadge(String numBadge);
	boolean existsById(Long id);
	boolean existsByMatricule(String matricule);
	boolean existsByEmail(String email);
	boolean existsByNumPiece(String numPiece);
	boolean existsByTel(String tel);
}
