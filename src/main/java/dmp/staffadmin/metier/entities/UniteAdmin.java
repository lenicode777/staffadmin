package dmp.staffadmin.metier.entities;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
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
public class UniteAdmin 
{
	@Id @GeneratedValue
	private Long idAdmin;
	private int level;
	private String single;
	private String appelation;
	private String situationGeo;
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date dateCreation;
	@OneToOne @JoinColumn(name = "ID_RESPONSABLE")
	private Agent responsable;
	@OneToMany(mappedBy = "tutelleDirecte", fetch = FetchType.LAZY)
	private List<Agent> personnel;
	@ManyToOne(fetch = FetchType.EAGER) @JoinColumn(name = "ID_TUTELLE_DIRECTE")
	private UniteAdmin tutelleDirecte;
	@OneToMany(mappedBy = "tutelleDirecte", fetch = FetchType.LAZY)
	private List<UniteAdmin> UASousTutelle;
	@ManyToOne() @JoinColumn(name="ID_TYPE_UA")
	private TypeUniteAdmin typeUA;
	private String ficheTechPath;
	@Transient
	private MultipartFile ficheTechFile;
	
	public UniteAdmin ajouterUA(UniteAdmin ua)
	{
		UASousTutelle.add(ua);
		return this;
	}
	
	public UniteAdmin retirerUA(UniteAdmin ua)
	{
		UASousTutelle.remove(ua);
		return this;
	}
	
	public UniteAdmin ajouterAgent(Agent a)
	{
		personnel.add(a);
		return this;
	}
	
	public UniteAdmin retirerAgent(Agent a)
	{
		personnel.remove(a);
		return this;
	}
	
	public UniteAdmin changerResponsable(Agent newResponsable)
	{
		this.responsable = newResponsable;
		return this;
	}
}
