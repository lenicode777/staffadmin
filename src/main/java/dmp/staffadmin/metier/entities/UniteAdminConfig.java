package dmp.staffadmin.metier.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data @NoArgsConstructor @AllArgsConstructor
public class UniteAdminConfig 
{
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private Long idUniteAdminMere;
	private Long idCabinetUniteAdminMere;	
	private Long idDrh;
}
