package dmp.staffadmin.metier.entities;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import dmp.staffadmin.dao.IUniteAdminDao;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor @AllArgsConstructor
@Entity
public class UniteAdmin 
{
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idUniteAdmin;
	private int level;
	private String sigle;
	private String appellation;
	private String situationGeo;
	private String contacts;
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date dateCreation;
	@OneToOne @JoinColumn(name = "ID_POST_MANAGER")
	@JsonIgnore
	private Post postManager;
	@OneToMany(mappedBy = "tutelleDirecte")
	@JsonIgnore
	private List<Agent> personnel;
	@ManyToOne @JoinColumn(name = "ID_TUTELLE_DIRECTE")
	@JsonIgnore
	private UniteAdmin tutelleDirecte;
	
	@OneToOne
	@JsonIgnore
	private UniteAdmin secretariat;
	
	@OneToMany(mappedBy = "tutelleDirecte")
	@JsonIgnore
	private List<UniteAdmin> uniteAdminSousTutelle;
	@ManyToOne @JoinColumn(name="ID_TYPE_UA")
	private TypeUniteAdmin typeUniteAdmin;
	private String ficheTechPath;

	private long idUserCreateur;
	private long idUserDerniereModif;
	private Date dateEnregistrement;
	private Date dateDerniereModif;

	@Transient
	private MultipartFile ficheTechFile;
	
	@OneToMany(mappedBy = "uniteAdmin") @JsonIgnore
	private List<Post> postesDeResponsabilites;
	
	public UniteAdmin ajouterUA(UniteAdmin ua)
	{
		uniteAdminSousTutelle.add(ua);
		ua.setTutelleDirecte(this);
		ua.setLevel(this.level + 1);
		return this;
	}
	
	public UniteAdmin retirerUA(UniteAdmin ua)
	{
		uniteAdminSousTutelle.remove(ua);
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
		this.postManager.setAgent(newResponsable) ;
		return this;
	}
	
	@Override
	public String toString()
	{
		return this.appellation + " ("+this.sigle + ")"; 
	}
	
	public String generateTitreOfPostManager() 
	{
		String titre;
		//==============================================//
		return "";
	}
	
	@JsonIgnore
	public Stream<UniteAdmin> getSubAdminStream()
	{
		return Stream.concat(Stream.of(this), uniteAdminSousTutelle.stream().flatMap(UniteAdmin::getSubAdminStream));
	}
	
	@JsonIgnore
	public Stream<UniteAdmin> getPatrentsStream()
	{
		return Stream.concat(Stream.of(this), Stream.of(tutelleDirecte).filter(Objects::nonNull).flatMap(UniteAdmin::getPatrentsStream));
	}
	
	@JsonIgnore
	public Stream<Long> getIdResponsablesStream()
	{
		return Stream.concat(Stream.of(this.getPostManager().getAgent().getIdAgent()), Stream.of(tutelleDirecte).filter(Objects::nonNull).flatMap(UniteAdmin::getIdResponsablesStream));
	}
	

}
