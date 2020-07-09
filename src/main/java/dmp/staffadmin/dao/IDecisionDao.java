package dmp.staffadmin.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import dmp.staffadmin.metier.entities.Decision;

public interface IDecisionDao extends JpaRepository<Decision, Long> 
{

}
