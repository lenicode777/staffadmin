package dmp.staffadmin.metier.entities;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
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

@Data @NoArgsConstructor @AllArgsConstructor
@Entity
public class Depart 
{
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date dateDepart;
	private String motif;
	@ManyToOne @JoinColumn(name = "ID_TYPE_DEPART")
	private TypeDepart typeDepart;
	@OneToOne
	private Agent agent;

	@OneToOne @JoinColumn(name = "ID_ARCHIVE_DEPART")
	private ArchiveAgent archiveAgent;

	private long idUserCreateur;
	private long idUserDerniereModif;
	private Date dateCreation;
	private Date dateDerniereModif;
	
	@Transient
	private MultipartFile acteAdministratif;
}
