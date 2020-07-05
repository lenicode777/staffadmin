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
import javax.persistence.Transient;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Data @NoArgsConstructor @AllArgsConstructor @ToString
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
	@Column(length = 20, unique = true)
	private String tel;
	private String fixeBureau;
	private String lieuNaissance;
	private Date dateNaissance; 
	private String typePiece;
	@Column(length = 50, unique = true)
	private String numPiece;
	private String nomPere;
	private String nomMere;
	
	@Column(length = 20, unique = true)
	private String matricule;
	@ManyToOne(fetch = FetchType.EAGER)	@JoinColumn(name = "ID_FONCTION")
	private Fonction fonction = new Fonction();
	@ManyToOne(fetch = FetchType.EAGER) @JoinColumn(name = "ID_EMPLOI") 
	private Emploi emploi = new Emploi();
	@ManyToOne(fetch = FetchType.EAGER) @JoinColumn(name = "ID_GRADE")
	private Grade grade = new Grade();
	@Column(length = 15)
	private String situationPresence;
	private Date datePriseService1;
	private Date datePriseServiceDMP;
	@Transient
	private Date dateDepartRetraite;
	private String statutEmploye; //Fonctionnaire, Contractuel
	private String position; //Activite, Detachement, Disponibilite, Sous les drapeaux
	private boolean active;
	@OneToOne @JoinColumn(name = "ID_RECRUTEMENT")
	private Recrutement recrutement;
	
	@Column(length = 255, unique = true)
	private String photoPath;
	@Column(length = 255, unique = true)
	private String cvPath;
	@Column(length = 255, unique = true)
	private String extraitPath;
	@Column(length = 255, unique = true)
	private String certificatService1Path;
	
}
