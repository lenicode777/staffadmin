package dmp.staffadmin.metier.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import dmp.staffadmin.security.userdetailsservice.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data @NoArgsConstructor @AllArgsConstructor
public class Besoin
{
	@Id @GeneratedValue
	private Long idBesoin;
	private int nombreAgents;
	@ManyToOne
	private Profil profil;
	@OneToOne @JoinColumn(name = "ID_USER")
	private User emetteur;
}
