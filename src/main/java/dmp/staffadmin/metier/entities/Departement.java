package dmp.staffadmin.metier.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import dmp.staffadmin.metier.enumeration.PointsCardinauxEnum;

@Entity
public class Departement 
{
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String nom;
	private String localisationGeo;
}