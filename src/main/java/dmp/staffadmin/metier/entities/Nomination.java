package dmp.staffadmin.metier.entities;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
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
public class Nomination 
{
	@Id @GeneratedValue
	private Long idNomination;
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date dateNomination;
	private String titreNomination;
	private String acteNominationPath;
	@ManyToOne @JoinColumn(name = "ID_FONCTION_NOMINATION")
	private Fonction fonctionNomination; 
	@OneToOne @JoinColumn(name = "ID_POST_NOMINATION")
	private Post postNomination;
	@ManyToOne
	private UniteAdmin uniteAdminDeNomination;
	@OneToOne @JoinColumn(name = "ID_AGENT_NOMME")
	private Agent agentNomme;
	@Transient
	private MultipartFile acteNominationFile;
}
