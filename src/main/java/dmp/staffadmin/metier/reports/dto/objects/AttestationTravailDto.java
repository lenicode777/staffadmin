package dmp.staffadmin.metier.reports.dto.objects;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data @NoArgsConstructor @AllArgsConstructor
public class AttestationTravailDto
{
    private String civilite;
    private String nom;
    private String prenom;
    private String emploi;
    private String matricule;
    private Date datePriseServiceDgmp;
}
