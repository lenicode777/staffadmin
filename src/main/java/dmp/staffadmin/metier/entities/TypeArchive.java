package dmp.staffadmin.metier.entities;

import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class TypeArchive
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(unique = true, nullable = false)
	private String codeType;
	private String nom;
	private String typeArchiveDir;
	private String typeArchivePath;
	@Transient
	private Object entity;
}
