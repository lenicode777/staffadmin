package dmp.staffadmin.metier.entities;

import java.util.Collection;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data @AllArgsConstructor @NoArgsConstructor
public class Demande 
{
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idDmde;
	private Date dateDmde;
	private String statutDmde;
	@ManyToOne @JoinColumn(name = "ID_AGENT")
	private Agent demandeur;
	@ManyToOne @JoinColumn(name = "ID_TYPE_DEMANDE")
	private TypeDmde typeDmde;
	private int duree;
	private Date dateDebut;
	private Date dateFin;
	@OneToMany(mappedBy = "demande")
	private Collection<Decision> decision;
}
