package dmp.staffadmin.metier.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data @NoArgsConstructor @AllArgsConstructor
public class NatureMouvement
{
	@Id @GeneratedValue
	private Long idNatureMouvement;
	private String natureMouvement;
}
