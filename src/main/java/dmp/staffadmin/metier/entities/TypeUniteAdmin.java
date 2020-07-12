package dmp.staffadmin.metier.entities;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor @AllArgsConstructor
@Entity(name = "type_ua") //@Table(name = "type_ua")
public class TypeUniteAdmin 
{
	@Id @GeneratedValue
	private Long idUniteAdmin;
	private String typeUniteAdmin;
	private int administrativeLevel;
	@OneToMany(mappedBy = "typeUniteAdmin", fetch = FetchType.LAZY)
	private List<UniteAdmin> UniteAdmins;
}
