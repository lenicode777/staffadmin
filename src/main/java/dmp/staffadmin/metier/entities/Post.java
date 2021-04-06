package dmp.staffadmin.metier.entities;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Post
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idPost;
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "ID_FONCTION")
	private Fonction fonction;

	private String libellePost;
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "ID_UNITE_ADMIN")
	// @JsonProperty(access = Access.WRITE_ONLY)
	private UniteAdmin uniteAdmin;
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "ID_AGENT")
	@JsonProperty(access = Access.WRITE_ONLY)
	private Agent agent;
}
