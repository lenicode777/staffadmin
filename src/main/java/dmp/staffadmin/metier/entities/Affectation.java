package dmp.staffadmin.metier.entities;

import javax.persistence.Entity;
import javax.persistence.GenerationType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data @NoArgsConstructor @AllArgsConstructor
public class Affectation 
{
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@ManyToOne @JoinColumn(name="ID_UA_DEPART")
	private UniteAdmin uaDepart;
	@ManyToOne @JoinColumn(name="ID_UA_ARRIVEE")
	private UniteAdmin uaArrivee;
	@ManyToOne @JoinColumn(name="ID_AGENT")
	private Agent agent;
	private boolean vue;
}
