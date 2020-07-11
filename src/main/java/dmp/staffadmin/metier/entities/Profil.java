package dmp.staffadmin.metier.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor @AllArgsConstructor
@Entity
public class Profil
{
	@Id @GeneratedValue
	private Long idProfil;
	private String nomProfil;
	private String descriptionProfil;
	@ManyToOne private Emploi emploi;
	@ManyToOne private Fonction fonction;
	@ManyToOne private Grade grade;
}
