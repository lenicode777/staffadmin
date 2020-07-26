package dmp.staffadmin.metier.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import dmp.staffadmin.security.userdetailsservice.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data @AllArgsConstructor @NoArgsConstructor
public class Decision 
{
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idDecision;
	private boolean verdict;
	private String motif;
	@ManyToOne @JoinColumn(name="ID_DEMANDE")
	private Dmde demande;
	@ManyToOne @JoinColumn(name="ID_USER")
	private User user;
}
