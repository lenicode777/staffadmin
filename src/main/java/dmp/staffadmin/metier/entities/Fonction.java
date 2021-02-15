package dmp.staffadmin.metier.entities;

import java.util.Collection;

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
public class Fonction 
{
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idFonction;
	private String nomFonction;
	private boolean fonctionDeNomination;
	private boolean fonctionTopManager;
	@OneToMany(mappedBy = "fonction")
	private Collection<Agent> listAgents; 
	@ManyToOne @JoinColumn(name = "ID_TYPE_UNITE_ADMIN")
	private TypeUniteAdmin typeUniteAdmin;
}
