package dmp.staffadmin.security.dto.factory;

import dmp.staffadmin.metier.entities.Affectation;
import dmp.staffadmin.metier.entities.Nomination;
import dmp.staffadmin.metier.entities.Promotion;
import dmp.staffadmin.security.dto.HistoriqueAgentDto;

public class HisoriqueAgentDtoFactory implements IHisoriqueAgentDtoFactory 
{
	final String AFFECTATION = "Affectation";
	final String PROMOTION = "Promotion";
	final String NOMINATION = "Nomination";
	@Override
	public HistoriqueAgentDto createHistoriqueAgentDto(Affectation affectation) 
	{
		HistoriqueAgentDto historiqueAgentDto = new HistoriqueAgentDto();
		historiqueAgentDto.setTypeHistorique(AFFECTATION);
		historiqueAgentDto.setDateHistorique(affectation.getDateAffectation());
		String detailHistorique = "Origine : "+ affectation.getUaDepart()+"\n";
		detailHistorique += "Destination : " + affectation.getUaArrivee();
		historiqueAgentDto.setDetailHistorique(detailHistorique);
		return historiqueAgentDto;
	}

	@Override
	public HistoriqueAgentDto createHistoriqueAgentDto(Promotion affectation) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public HistoriqueAgentDto createHistoriqueAgentDto(Nomination affectation) {
		// TODO Auto-generated method stub
		return null;
	}

}
