package dmp.staffadmin.metier.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data @NoArgsConstructor @AllArgsConstructor
public class NatureAbsenceTemp 
{
	@Id @GeneratedValue
	private Long idNatureAbsenceTemp;
	private String natureAbsenceTemp;
}
