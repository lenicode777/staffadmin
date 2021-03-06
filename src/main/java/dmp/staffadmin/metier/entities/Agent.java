package dmp.staffadmin.metier.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.annotation.JsonIgnore;

import dmp.staffadmin.dao.IAgentDao;
import dmp.staffadmin.security.model.AppUser;
import dmp.staffadmin.utilities.DateManager;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Data @NoArgsConstructor @AllArgsConstructor
public class Agent 
{
	//private static final long serialVersionUID = 6994365318807504839L;
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idAgent;
	@Column(length = 50)
	private String nom;
	@Column(length = 50)
	private String prenom;
	@Column(length = 5)
	private String sexe;
	
	@Column(length = 100, unique = true)
	private String email;
	
	@Column(length = 100, unique = true)
	private String emailPro;
	
	@Column(length = 20, unique = true)
	private String tel;
	private String fixeBureau;
	private String lieuNaissance; //Localité (Plus précis)
	private String departementNaissance;
	@Temporal(TemporalType.DATE) // Pour JPA. Signifie que dans la BD la date aura le type Date et non le type TimeStamp
	@DateTimeFormat(pattern = "yyyy-MM-dd") // Spring formate la date pour nous
	private Date dateNaissance; 
	private String typePiece;
	@Column(length = 50, unique = true)
	private String numPiece;
	private String nomPere;
	private String nomMere;
	private boolean attenteAffectation;
	
	
	@Column(unique = true)
	private String numBadge;
	@Column(length = 20, unique = true)
	private String matricule;
	private String situationMatrimonial;
	private long idUserCreateur;
	private long idUserDerniereModif;
	private Date dateCreation;
	private Date dateDerniereModif;
	@ManyToOne	@JoinColumn(name = "ID_FONCTION")
	private Fonction fonction;
	@ManyToOne	@JoinColumn(name = "ID_FONCTION_NOMINATION")
	private Fonction fonctionNomination;
	
	@OneToOne @JoinColumn(name = "ID_POST")
	@JsonIgnore
	private Post post; 
	@ManyToOne @JoinColumn(name = "ID_EMPLOI") 
	private Emploi emploi;
	@ManyToOne @JoinColumn(name = "ID_GRADE")
	private Grade grade;
	private String titre;
	@ManyToOne @JoinColumn(name = "ID_TUTELLE_DIRECT")
	private UniteAdmin tutelleDirecte;
	

	private String situationPresence;
	@Temporal(TemporalType.DATE) // Pour JPA. Signifie que dans la BD la date aura le type Date et non le type TimeStamp
	@DateTimeFormat(pattern = "yyyy-MM-dd") // Spring formate la date pour nous
	private Date datePriseService1;
	
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date datePriseServiceDGMP;
	
	@Transient
	private Date dateDepartRetraite;
	private String statutAgent; //Fonctionnaire, Contractuel
	private String position; //Activite, Detachement, Disponibilite, Sous les drapeaux
	private String etatRecrutement; // En service, En attente de première affectation, En attente d'affectation dans une SD, En attente d'affectation dans un service 
	private boolean active;
	@OneToOne(fetch = FetchType.EAGER)
	private AppUser user;
	@Column(length = 255, unique = true)
	private String nomPhoto;
	@Transient private MultipartFile photoFile;
	
	
	@Transient private long age;
	public long getAge()
	{
		return DateManager.dateDiff(dateNaissance, new Date());
	}
	public static String generateFileName(String matriculeAgent, String typeFile)
	{
		return typeFile+matriculeAgent;
	}
	
	public int getAgeRetraite()
	{
		if(this.getGrade().getNomGrade().startsWith("A"))
		{
			if(this.getGrade().getNomGrade().compareTo("A4")>=0)
			{
				return 65;
			}
			else
			{
				return 60;
			}
		}
		else
		{
			return 60;
		}
	}
	
	public String getTempsTravailRestant()
	{
		return DateManager.getRemainTime(new Date(), getDateDepartRetraite());
	}
	
	public Date getDateDepartRetraite()
	{
		int ageRetraite = this.getAgeRetraite();
		return DateManager.addYears(this.getDateNaissance(), ageRetraite);
	}
	@Override
	public String toString()
	{
		return this.nom + " " + this.prenom + " ("+this.matricule+ ")";
	}
	
	public boolean isAffectable()
	{
		boolean isAffectable = false;
		//Si l'agent n'occupe aucun post, on retourn vrai, sinon s'il occupe un post et que ce post n'est pas un post de nomination, on retourn vrai
		isAffectable = (post == null ? true : post!=null && !post.getFonction().isFonctionDeNomination() ? true : false);

		return isAffectable;
	}
	
}