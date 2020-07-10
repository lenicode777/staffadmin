package dmp.staffadmin.metier.entities;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity @DiscriminatorValue(value = "DEMANDE_AFFECTATION")
@Data @NoArgsConstructor @AllArgsConstructor
public class DmdeMouvement extends Demande
{
	@ManyToOne @JoinColumn(name = "ID_NATURE_MOUVEMENT")
	private NatureMouvement natureMouvement;
	@ManyToOne(fetch = FetchType.EAGER) @JoinColumn(name = "ID_DESTINATION")
	private UniteAdministrative destination;
}