package dmp.staffadmin.metier.entities;

import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity 
@Data @AllArgsConstructor @NoArgsConstructor
public class Grade 
{
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idGrade;
	private String nomGrade;
	@OneToMany(mappedBy = "grade")
	private Collection<Agent> listAgents; 
}
