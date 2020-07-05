package dmp.staffadmin.metier.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity 
@Data @AllArgsConstructor @NoArgsConstructor
public class TypeDmde 
{
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idTypeDmde;
	private String libeleTypeDmde;
}
