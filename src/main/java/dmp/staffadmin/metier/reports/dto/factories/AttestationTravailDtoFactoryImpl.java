package dmp.staffadmin.metier.reports.dto.factories;

import dmp.staffadmin.metier.entities.Agent;
import dmp.staffadmin.metier.reports.dto.objects.AttestationTravailDto;

import java.util.List;
import java.util.stream.Collectors;

public class AttestationTravailDtoFactoryImpl implements AttestationTravailDtoFactory {
    @Override
    public AttestationTravailDto createAttestationTravailDto(Agent agent)
    {
        AttestationTravailDto attestationTravailDto = new AttestationTravailDto();
        attestationTravailDto.setCivilite(agent.getSexe().equalsIgnoreCase("M")|| agent.getSexe().equalsIgnoreCase("HOMME") ? "Monsieur": "Madame");
        attestationTravailDto.setMatricule(agent.getMatricule());
        attestationTravailDto.setNom(agent.getNom());
        attestationTravailDto.setPrenom(agent.getPrenom());
        attestationTravailDto.setEmploi(agent.getEmploi().getNomEmploi());
        attestationTravailDto.setDatePriseServiceDgmp(agent.getDatePriseServiceDGMP());
        return attestationTravailDto;
    }

    @Override
    public List<AttestationTravailDto> generateAttestationTravailDtoList(List<Agent> agents)
    {
        return agents.stream().map(agent-> this.createAttestationTravailDto(agent)).collect(Collectors.toList());
    }
}
