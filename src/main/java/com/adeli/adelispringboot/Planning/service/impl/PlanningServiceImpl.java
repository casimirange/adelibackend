package com.adeli.adelispringboot.Planning.service.impl;

import com.adeli.adelispringboot.Mangwa.service.IMangwaService;
import com.adeli.adelispringboot.Planning.service.IPlanningService;
import com.adeli.adelispringboot.Projet.service.IProjetService;
import com.adeli.adelispringboot.Tontine.service.ITontineService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PlanningServiceImpl implements IPlanningService {
    final ITontineService iTontineService;
    final IProjetService iProjetService;
    final IMangwaService iMangwaService;
    @Override
    public Double soldeMangwa() {
        return iMangwaService.soldeMangwa();
    }

    @Override
    public Double soldeTontine() {
        return iTontineService.soldeTontine();
    }

    @Override
    public Double soldeProjet() {
        return iProjetService.soldeProjet();
    }
}
