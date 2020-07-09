package dmp.staffadmin.metier.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Data @NoArgsConstructor @AllArgsConstructor @ToString
public class Post 
{
	@Id @GeneratedValue
	private Long idPost;
	@ManyToOne(fetch = FetchType.EAGER) @JoinColumn(name = "ID_FONCTION")
	private Fonction fonction;
	@Column(unique = true)
	private String libellePost;
	
}
