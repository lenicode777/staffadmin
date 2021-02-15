package dmp.staffadmin.controllers;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor @AllArgsConstructor
public class AgentFormCritere
{
	private Long idDc;
	private Long idSd;
	private Long idService;
	private String situationPresence;
	private String situationAffectation;
	private String situationPosition;
	private String situationConge;
	private String situationRetraite;
	private String necrologie;
}
