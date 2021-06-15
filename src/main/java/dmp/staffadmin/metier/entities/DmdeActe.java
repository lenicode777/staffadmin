package dmp.staffadmin.metier.entities;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity @DiscriminatorValue(value = "DEMANDE_ACTE")
@Data @NoArgsConstructor @AllArgsConstructor
public class DmdeActe extends Dmde
{
	@ManyToOne @JoinColumn(name = "ID_NATURE_ACTE")
	private NatureActe natureActe;
}
