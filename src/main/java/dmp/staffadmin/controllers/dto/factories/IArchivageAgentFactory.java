package dmp.staffadmin.controllers.dto.factories;

import dmp.staffadmin.metier.entities.ArchiveAgent;
import dmp.staffadmin.metier.entities.Depart;

public interface IArchivageAgentFactory
{
    ArchiveAgent createArchivageAgentFromDepart(Depart depart);
}
