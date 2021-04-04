package dmp.staffadmin.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import dmp.staffadmin.metier.entities.Grade;

public interface IGradeDao extends JpaRepository<Grade, Long> 
{
	public List<Grade> findByNomGrade(String nomGrade);
	public List<Grade> findByRang(int rang);
	public List<Grade> findByRangLessThan(int rang);
	public List<Grade> findByRangGreaterThan(int rang);
}
