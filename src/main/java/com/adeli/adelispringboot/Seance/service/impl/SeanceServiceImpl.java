package com.adeli.adelispringboot.Seance.service.impl;

import com.adeli.adelispringboot.Mangwa.entity.EStatusTransaction;
import com.adeli.adelispringboot.Mangwa.entity.TypeTransaction;
import com.adeli.adelispringboot.Projet.entity.Projet;
import com.adeli.adelispringboot.Seance.entity.Seance;
import com.adeli.adelispringboot.Seance.repository.ISeanceRepository;
import com.adeli.adelispringboot.Seance.service.ISeanceService;
import com.adeli.adelispringboot.Session.entity.EStatusSession;
import com.adeli.adelispringboot.Session.entity.Session;
import com.adeli.adelispringboot.Session.entity.SessionStatus;
import com.adeli.adelispringboot.Session.repository.ISessionRepo;
import com.adeli.adelispringboot.Session.repository.IStatusSessionRepo;
import com.adeli.adelispringboot.Users.entity.Users;
import com.adeli.adelispringboot.authentication.dto.MessageResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.Predicate;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class SeanceServiceImpl implements ISeanceService {

    final ISeanceRepository iSeanceRepository;

    final ResourceBundleMessageSource messageSource;

    final IStatusSessionRepo iStatusSessionRepo;
    final ISessionRepo iSessionRepo;

    @Override
    public Page<Seance> getAllSeances(LocalDate date, int page, int size, String sort, String order) {
        Specification<Seance> specification = ((root, query, criteriaBuilder) -> {

            List<Predicate> predicates = new ArrayList<>();

            if (date != null){
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("date").as(String.class)), '%' + date.toString() + '%'));
            }
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        });
        return iSeanceRepository.findAll(specification, PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(order), sort)));
    }

    @Override
    public void createSeance(Seance seance) {
        iSeanceRepository.save(seance);
    }

    @Override
    public void terminerSeance(Seance seance) {
        iSeanceRepository.save(seance);
    }

    @Override
    public Seance getById(Long id) {
        return iSeanceRepository.findById(id).get();
    }

    @Override
    public Seance getByDate(LocalDate date) {
        return iSeanceRepository.getSeanceByDate(date);
    }
}
