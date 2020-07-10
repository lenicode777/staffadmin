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
public class UniteAdministrative 
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
	private UniteAdministrative tutelleDirecte;
	@OneToMany(mappedBy = "tutelleDirecte", fetch = FetchType.LAZY)
	private List<UniteAdministrative> UASousTutelle;
	@ManyToOne() @JoinColumn(name="ID_TYPE_UA")
	private TypeUA typeUA;
	private String ficheTechPath;
	@Transient
	private MultipartFile ficheTechFile;
	
	public UniteAdministrative ajouterUA(UniteAdministrative ua)
	{
		UASousTutelle.add(ua);
		return this;
	}
	
	public UniteAdministrative retirerUA(UniteAdministrative ua)
	{
		UASousTutelle.remove(ua);
		return this;
	}
	
	public UniteAdministrative ajouterAgent(Agent a)
	{
		personnel.add(a);
		return this;
	}
	
	public UniteAdministrative retirerAgent(Agent a)
	{
		personnel.remove(a);
		return this;
	}
	
	public UniteAdministrative changerResponsable(Agent newResponsable)
	{
		this.responsable = newResponsable;
		return this;
	}
}
