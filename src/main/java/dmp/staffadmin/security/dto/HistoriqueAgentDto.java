package dmp.staffadmin.security.dto;

import java.util.Date;

import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor @AllArgsConstructor
public class HistoriqueAgentDto 
{
	private String typeHistorique;
	private String detailHistorique;
	private Date dateHistorique;
	private MultipartFile fichier;
}
