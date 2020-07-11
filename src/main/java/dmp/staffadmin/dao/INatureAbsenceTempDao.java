package dmp.staffadmin.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import dmp.staffadmin.metier.entities.NatureAbsenceTemp;

public interface INatureAbsenceTempDao extends JpaRepository<NatureAbsenceTemp, Long> 
{

}
