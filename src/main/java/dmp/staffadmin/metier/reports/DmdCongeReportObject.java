package dmp.staffadmin.metier.reports;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

//import dmp.staffadmin.entities.DemandeConge;
//import dmp.staffadmin.entities.Employe;
import dmp.staffadmin.metier.enumeration.TypeRapport;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

//@Data @NoArgsConstructor @AllArgsConstructor
@Deprecated
public class DmdCongeReportObject 
{
	/*
	private DemandeConge dmdConge;
	private String numDmdConge;
	private String nom;
	private String matricule;
	private String emploi;
	private String fonction;
	private int nbJoursConge;
	private int anneeConge;
	private Date dateDebut;
	private Date dateFin;
	private TypeRapport typeRapport = TypeRapport.DEMANDE_CONGE;
	private String reportFileName;
	
	public void setReportFileName()
	{
		// typeRapport_matriculeDemandeur_NumDemande
		this.reportFileName = this.typeRapport.toString()+ "_" + this.dmdConge.getDemandeur().getMatricule() + "_" + this.dmdConge.getIdDmdeConge();
	}
	public void setDmdConge(DemandeConge dmdConge)
	{
		Employe emp = dmdConge.getDemandeur();
		this.numDmdConge = dmdConge.getUniqueCode(); 
		this.dmdConge = dmdConge;
		this.nom = emp.getNom() + " " + emp.getPrenom();
		this.matricule = emp.getMatricule();
		this.emploi = emp.getEmploi().getNomEmploi();
		this.fonction = emp.getFonction().getNomFonction();
		this.nbJoursConge = dmdConge.getNbJours();
		this.anneeConge = dmdConge.getAnneeConge();
		this.dateDebut = dmdConge.getDateDebut();
		this.dateFin = dmdConge.getDateFin();
		setReportFileName();
	}
	
	private static DmdCongeReportObject getDmdCongeReportObject(DemandeConge dmdConge)
	{
		DmdCongeReportObject dmdCongeReportObject = new DmdCongeReportObject();
		dmdCongeReportObject.setDmdConge(dmdConge);
		return dmdCongeReportObject;
		
	}
		
	public static List<DmdCongeReportObject> getDmdCongeReportObjectList(List<DemandeConge> dmdConges)
	{
		List<DmdCongeReportObject> dmdCongeReportObjects = new ArrayList<DmdCongeReportObject>();
		for(DemandeConge dmdConge : dmdConges)
		{
			dmdCongeReportObjects.add(DmdCongeReportObject.getDmdCongeReportObject(dmdConge));
		}
		return dmdCongeReportObjects;
	}
	
	//////////////////////////////////////////////////////////////////////////////////////////////////
	public static List<List<DmdCongeReportObject>> getDmdCongeReportObjectListOfOneElement(List<DemandeConge> dmdConges)
	{
		//List<DmdCongeReportObject> dmdCongeReportObjects = new ArrayList<DmdCongeReportObject>();
		List<List<DmdCongeReportObject>> listOfOneElement = new ArrayList<>();
		
		List<DmdCongeReportObject> dmdCongeReportObjects = DmdCongeReportObject.getDmdCongeReportObjectList(dmdConges);
		for(DmdCongeReportObject dmdCongeReportObject : dmdCongeReportObjects )
		{
			List<DmdCongeReportObject> listOfOne = new ArrayList<>();
			listOfOne.add(dmdCongeReportObject);
			listOfOneElement.add(listOfOne);
		}
		return listOfOneElement;
	}
	*/
}
