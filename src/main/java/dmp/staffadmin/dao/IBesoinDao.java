package dmp.staffadmin.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import dmp.staffadmin.metier.entities.Besoin;

public interface IBesoinDao extends JpaRepository<Besoin, Long> {

}
