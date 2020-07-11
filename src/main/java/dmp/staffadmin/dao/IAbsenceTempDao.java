package dmp.staffadmin.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import dmp.staffadmin.metier.entities.AbsenceTemp;

public interface IAbsenceTempDao extends JpaRepository<AbsenceTemp, Long> 
{

}
