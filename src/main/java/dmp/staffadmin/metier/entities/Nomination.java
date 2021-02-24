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
	
	public String getTitreNomination2()
	{
		return getTitreNomination2(this.fonctionNomination, this.uniteAdminDeNomination);
	}
	
	public static String getTitreNomination2(Fonction fonction, UniteAdmin uniteAdmin)
	{
		StringBuilder titreBuilder = new StringBuilder(fonction.getNomFonction());
		String[] splitedAppellation = uniteAdmin.getAppellation().split(" ");
		String nomTypeUniteAdmin = uniteAdmin.getTypeUniteAdmin().getNomTypeUniteAdmin();
		
		if(nomTypeUniteAdmin.equalsIgnoreCase("Direction Centrale") || nomTypeUniteAdmin.equalsIgnoreCase("Service Rattaché"))
		{
			for(int i=uniteAdmin.getTypeUniteAdmin().getNomTypeUniteAdmin().split(" ").length-1 ; i< splitedAppellation.length; i++) 
			{
				titreBuilder.append(" "+splitedAppellation[i]);
			}
		}
		else
		{
			for(int i=uniteAdmin.getTypeUniteAdmin().getNomTypeUniteAdmin().split(" ").length ; i< splitedAppellation.length; i++) 
			{
				titreBuilder.append(" "+splitedAppellation[i]);
			}
		}

		return titreBuilder.toString();
	}
	
	public static void main(String[] args)
	{
		Fonction fonction1 = new Fonction(); fonction1.setNomFonction("Directeur Général Adjoint");
		Fonction fonction2 = new Fonction(); fonction2.setNomFonction("Directeur Général");
		Fonction fonction3 = new Fonction(); fonction3.setNomFonction("Directeur");
		Fonction fonction4 = new Fonction(); fonction4.setNomFonction("Chef de Service");
		UniteAdmin uniteAdmin1 =  new UniteAdmin(); uniteAdmin1.setAppellation("Direction Générale des Marchés Publics");
		UniteAdmin uniteAdmin2 =  new UniteAdmin(); uniteAdmin2.setAppellation("Direction des Procédures et Opérations");
		UniteAdmin uniteAdmin3 =  new UniteAdmin();uniteAdmin3.setAppellation("Services des Moyens Généraux et du Personnel");
		TypeUniteAdmin type1 = new TypeUniteAdmin(null, "Direction Générale", 1, null);
		TypeUniteAdmin type2 = new TypeUniteAdmin(null, "Direction", 2, null);
		TypeUniteAdmin type3 = new TypeUniteAdmin(null, "Service", 2, null);
		TypeUniteAdmin type4 = new TypeUniteAdmin(null, "Service Rattaché", 2, null);
		uniteAdmin1.setTypeUniteAdmin(type1);
		uniteAdmin2.setTypeUniteAdmin(type2);
		uniteAdmin3.setTypeUniteAdmin(type3);
		
		
			
		
		
		
		
		System.out.println(getTitreNomination2(fonction1, uniteAdmin1));
		System.out.println(getTitreNomination2(fonction2, uniteAdmin1));
		
		System.out.println(getTitreNomination2(fonction3, uniteAdmin2));
		System.out.println(getTitreNomination2(fonction4, uniteAdmin3));
	}
}
