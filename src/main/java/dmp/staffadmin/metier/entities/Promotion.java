package dmp.staffadmin.metier.entities;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data @NoArgsConstructor @AllArgsConstructor
public class Promotion 
{
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idPromotion;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Temporal(TemporalType.DATE)
	private Date datePromotion;
	private String numActePromotion;
	@ManyToOne @JoinColumn(name = "ID_AGENT")
	private Agent agentPromu;
	
	@ManyToOne @JoinColumn(name = "ID_FONCTION")
	private Fonction newFonction;
	@ManyToOne @JoinColumn(name = "ID_EMPLOI")
	private Emploi newEmploi;
	@ManyToOne @JoinColumn(name = "ID_GRADE")
	private Grade newGrade;
	
	@ManyToOne @JoinColumn(name = "ID_HOLD_FONCTION")
	private Fonction holdFonction;
	@ManyToOne @JoinColumn(name = "ID_HOLD_EMPLOI")
	private Emploi holdEmploi;
	@ManyToOne @JoinColumn(name = "ID_HOLD_GRADE")
	private Grade holdGrade;
	
	@Transient
	private MultipartFile actePromotionFile;
}
