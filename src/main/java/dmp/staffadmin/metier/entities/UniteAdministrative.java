package dmp.staffadmin.metier.entities;

import java.util.Date;
import java.util.List;

import javax.persistence.Transient;

import org.springframework.web.multipart.MultipartFile;

public class UniteAdministrative 
{
	private Long idAdmin;
	private int level;
	private int administrativeLevel;
	private String single;
	private String appelation;
	private String situationGeo;
	private Date dateCreation;
	private Agent responsable;
	private List<Agent> personnel;
	private UniteAdministrative tutelle;
	private List<UniteAdministrative> uniteAdministrativesSousTutelle;
	private String ficheTechPath;
	@Transient
	private MultipartFile ficheTechFile;
	
	public UniteAdministrative ajouterUniteAdministrative(UniteAdministrative ua)
	{
		uniteAdministrativesSousTutelle.add(ua);
		return this;
	}
}
