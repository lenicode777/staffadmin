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
	private int rang; //D = 1 , C = 2 ,  B = 3 , A = 4
	@OneToMany(mappedBy = "grade")
	private Collection<Agent> listAgents;
}