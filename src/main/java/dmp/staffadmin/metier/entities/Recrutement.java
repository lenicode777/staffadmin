package dmp.staffadmin.metier.entities;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Transient;

import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Data @NoArgsConstructor @AllArgsConstructor @ToString
public class Recrutement 
{
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idRecrutement;
	private String statut;
	private Date dateEnregistrementAgent;
	private Date dateMutation;
	private Date dateSignatureCertificatServiceDmp;
	private Date dateSignatureCertificatServiceDGBF;
	private Date dateSignatureCertificatServiceDAF;
	private String ficheEnregistrementPath;
	@Transient private MultipartFile ficheEnregistrementFile;
	private String ficheRecrutementPath;
	@Transient private MultipartFile ficheRecrutementFile;
	@OneToOne @JoinColumn(name = "ID_AGENT")
	private Agent agent;
}
