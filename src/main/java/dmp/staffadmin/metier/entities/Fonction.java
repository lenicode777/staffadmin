package dmp.staffadmin.metier.entities;

import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import dmp.staffadmin.security.model.AppRole;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data @AllArgsConstructor @NoArgsConstructor
public class Fonction 
{
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idFonction;
	private String nomFonction;
	@ManyToOne(fetch = FetchType.EAGER) @JoinColumn(name = "ID_ROLE")
	private AppRole roleAssocie;
	private boolean fonctionDeNomination;
	private boolean fonctionTopManager;
	@OneToMany(mappedBy = "fonction")
	private Collection<Agent> listAgents; 
	@ManyToOne(fetch = FetchType.EAGER) @JoinColumn(name = "ID_TYPE_UNITE_ADMIN") //Plusieurs Fonction peuvent correspondrent à un même type d'unité admin 
	private TypeUniteAdmin typeUniteAdmin; // Exemple : Directeur Général, Directeur Général Adjoint et Assistant DG correspondent tous à DG
}
