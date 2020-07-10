package dmp.staffadmin.metier.entities;

import java.util.Date;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity @DiscriminatorValue(value = "DEMANDE_ABSENCE_TEMP") 
@Data @NoArgsConstructor @AllArgsConstructor
public class DmdeAbsenceTemp extends Demande
{
	@ManyToOne @JoinColumn(name = "ID_NATURE_ABSENCE")
	private NatureAbsenceTemp natureAbsenceDmdeTemp;
	private int duree;
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date dateDebut;
	private Date dateFin;
}
