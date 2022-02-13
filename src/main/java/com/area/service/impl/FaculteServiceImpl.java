package com.area.service.impl;

import com.area.domain.Faculte;
import com.area.repository.FaculteRepository;
import com.area.service.FaculteService;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Faculte}.
 */
@Service
@Transactional
public class FaculteServiceImpl implements FaculteService {

    private final Logger log = LoggerFactory.getLogger(FaculteServiceImpl.class);

    private final FaculteRepository faculteRepository;

    public FaculteServiceImpl(FaculteRepository faculteRepository) {
        this.faculteRepository = faculteRepository;
    }

    @Override
    public Faculte save(Faculte faculte) {
        log.debug("Request to save Faculte : {}", faculte);
        return faculteRepository.save(faculte);
    }

    @Override
    public Optional<Faculte> partialUpdate(Faculte faculte) {
        log.debug("Request to partially update Faculte : {}", faculte);

        return faculteRepository
            .findById(faculte.getId())
            .map(existingFaculte -> {
                if (faculte.getNom() != null) {
                    existingFaculte.setNom(faculte.getNom());
                }

                return existingFaculte;
            })
            .map(faculteRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Faculte> findAll() {
        log.debug("Request to get all Facultes");
        return faculteRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Faculte> findOne(Long id) {
        log.debug("Request to get Faculte : {}", id);
        return faculteRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Faculte : {}", id);
        faculteRepository.deleteById(id);
    }
}
