package dmp.staffadmin.security.dto.factory;

import dmp.staffadmin.metier.entities.Affectation;
import dmp.staffadmin.metier.entities.Nomination;
import dmp.staffadmin.metier.entities.Promotion;
import dmp.staffadmin.security.dto.HistoriqueAgentDto;

public interface IHisoriqueAgentDtoFactory
{
	HistoriqueAgentDto createHistoriqueAgentDto(Affectation affectation);
	HistoriqueAgentDto createHistoriqueAgentDto(Promotion affectation);
	HistoriqueAgentDto createHistoriqueAgentDto(Nomination affectation);
}
