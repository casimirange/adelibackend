/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.adeli.adelispringboot.Seance.repository;

import com.adeli.adelispringboot.Seance.entity.Seance;
import com.adeli.adelispringboot.Session.entity.SessionStatus;
import org.hibernate.validator.constraints.pl.REGON;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

/**
 *
 * @author Casimir
 */
@Repository
public interface ISeanceRepository extends JpaRepository<Seance, Long> {

    boolean existsSeanceByStatus(SessionStatus sessionStatus);
    Seance getSeanceByDate(LocalDate date);
    List<Seance> findTop2ByOrderByIdDesc();
    Page<Seance> findAll(Specification<Seance> specification, Pageable pageable);
//    String cr = "select c.id_compte_rendu, c.date, c.details from compte_rendu c "
//            + "join session s on c.id_session = s.id_session "
//            + "where s.etat = 1"
//            + " ORDER by c.date desc ";
//
//    @Query(value=cr, nativeQuery = true)
//    public List<JSONObject> findCR();
}