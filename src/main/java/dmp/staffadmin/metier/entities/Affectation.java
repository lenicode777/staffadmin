package dmp.staffadmin.metier.entities;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GenerationType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data @NoArgsConstructor @AllArgsConstructor
public class Affectation 
{
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@ManyToOne @JoinColumn(name="ID_UA_DEPART")
	private UniteAdmin uaDepart;
	@ManyToOne @JoinColumn(name="ID_UA_ARRIVEE")
	private UniteAdmin uaArrivee;
	@ManyToOne @JoinColumn(name="ID_AGENT")
	private Agent agent;
	//private boolean vue;
	@Temporal(TemporalType.DATE) // Pour JPA. Signifie que dans la BD la date aura le type Date et non le type TimeStamp
	@DateTimeFormat(pattern = "yyyy-MM-dd") // Spring formate la date pour nous
	private Date dateAffectation;
}
