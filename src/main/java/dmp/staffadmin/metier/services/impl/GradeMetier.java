package dmp.staffadmin.metier.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dmp.staffadmin.dao.IGradeDao;
import dmp.staffadmin.metier.entities.Grade;
import dmp.staffadmin.metier.services.interfaces.IGradeMetier;

@Service
public class GradeMetier implements IGradeMetier
{
	@Autowired
	private IGradeDao gradeDao;

	@Override
	public Grade save(Grade grade)
	{
		grade.setNomGrade(grade.getNomGrade().toUpperCase());
		return gradeDao.save(grade);
	}

	@Override
	public Grade update(Grade grade)
	{
		return save(grade);
	}

	@Override
	public Grade update(Long gradeBodyId, Grade gradeBody)
	{
		gradeBody.setIdGrade(gradeBodyId);
		return save(gradeBody);
	}

	@Override
	public List<Grade> findAll()
	{
		return gradeDao.findAll();
	}

	@Override
	public Grade findByNom(String nomGrade)
	{
		return gradeDao.findByNomGrade(nomGrade).isEmpty() ? null : gradeDao.findByNomGrade(nomGrade).get(0);
	}
}
