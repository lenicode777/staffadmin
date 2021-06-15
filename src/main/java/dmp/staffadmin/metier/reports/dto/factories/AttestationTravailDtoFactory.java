package dmp.staffadmin.metier.reports.dto.factories;

import dmp.staffadmin.metier.entities.Agent;
import dmp.staffadmin.metier.reports.dto.objects.AttestationTravailDto;

import java.util.List;

public interface AttestationTravailDtoFactory
{
    AttestationTravailDto createAttestationTravailDto(Agent agent);
    List<AttestationTravailDto> generateAttestationTravailDtoList(List<Agent> agents);
}
