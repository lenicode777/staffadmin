package dmp.staffadmin.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import dmp.staffadmin.metier.entities.DmdeAbsenceTemp;

public interface IDmdeAbsenceTempDao extends JpaRepository<DmdeAbsenceTemp, Long> 
{

}
