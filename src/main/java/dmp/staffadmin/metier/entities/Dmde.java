package dmp.staffadmin.metier.entities;

import java.util.Collection;
import java.util.Date;

import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity @Inheritance(strategy = InheritanceType.JOINED) @DiscriminatorColumn(discriminatorType = DiscriminatorType.STRING, name = "TYPE_DMDE")
@Data @AllArgsConstructor @NoArgsConstructor
public abstract class Dmde 
{
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idDmde;
	private Date dateDmde;
	private String statutDmde;
	@ManyToOne @JoinColumn(name = "ID_AGENT")
	private Agent demandeur;
	@OneToMany(mappedBy = "demande")
	private Collection<Decision> decision;

	private long idUserCreateur;
	private long idUserDerniereModif;
	private Date dateCreation;
	private Date dateDerniereModif;
}
