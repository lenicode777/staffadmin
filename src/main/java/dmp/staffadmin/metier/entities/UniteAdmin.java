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

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor @AllArgsConstructor
@Entity
public class UniteAdmin 
{
	@Id @GeneratedValue
	private Long idUniteAdmin;
	private int level;
	private String sigle;
	private String appelation;
	private String situationGeo;
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date dateCreation;
	@OneToOne @JoinColumn(name = "ID_RESPONSABLE")
	@JsonIgnore
	private Agent responsable;
	@OneToMany(mappedBy = "tutelleDirecte", fetch = FetchType.LAZY)
	@JsonIgnore
	private List<Agent> personnel;
	@ManyToOne(fetch = FetchType.EAGER) @JoinColumn(name = "ID_TUTELLE_DIRECTE")
	@JsonIgnore
	private UniteAdmin tutelleDirecte;
	@OneToMany(mappedBy = "tutelleDirecte", fetch = FetchType.LAZY)
	@JsonIgnore
	private List<UniteAdmin> UniteAdminSousTutelle;
	@ManyToOne() @JoinColumn(name="ID_TYPE_UA")
	@JsonIgnore
	private TypeUniteAdmin typeUniteAdmin;
	private String ficheTechPath;
	@Transient
	private MultipartFile ficheTechFile;
	
	public UniteAdmin ajouterUA(UniteAdmin ua)
	{
		UniteAdminSousTutelle.add(ua);
		ua.setLevel(this.level + 1);
		return this;
	}
	
	public UniteAdmin retirerUA(UniteAdmin ua)
	{
		UniteAdminSousTutelle.remove(ua);
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
	
	@Override
	public String toString()
	{
		return this.appelation + " - "+this.sigle; 
	}
}
