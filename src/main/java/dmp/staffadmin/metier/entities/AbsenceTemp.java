package dmp.staffadmin.metier.entities;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Transient;

import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data @NoArgsConstructor @AllArgsConstructor
public class AbsenceTemp 
{
	@Id @GeneratedValue
	private Long idAbsenceTemp;
	private String acteAbsenceTempPath;
	@ManyToOne(fetch = FetchType.EAGER) @JoinColumn(name = "ID_AGENT")
	private Agent agent;
	@OneToOne(fetch = FetchType.EAGER) @JoinColumn(name = "ID_DMDE")
	private DmdeAbsenceTemp dmdeAbsenceTemp;
	@Transient
	private MultipartFile acteAbsenceTempFile;
}
