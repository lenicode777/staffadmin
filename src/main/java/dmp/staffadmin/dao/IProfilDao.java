package dmp.staffadmin.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import dmp.staffadmin.metier.entities.Profil;

public interface IProfilDao extends JpaRepository<Profil, Long> 
{

}
