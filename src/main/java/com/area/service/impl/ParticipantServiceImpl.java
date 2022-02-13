package com.area.service.impl;

import com.area.domain.Participant;
import com.area.repository.ParticipantRepository;
import com.area.service.ParticipantService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Participant}.
 */
@Service
@Transactional
public class ParticipantServiceImpl implements ParticipantService {

    private final Logger log = LoggerFactory.getLogger(ParticipantServiceImpl.class);

    private final ParticipantRepository participantRepository;

    public ParticipantServiceImpl(ParticipantRepository participantRepository) {
        this.participantRepository = participantRepository;
    }

    @Override
    public Participant save(Participant participant) {
        log.debug("Request to save Participant : {}", participant);
        return participantRepository.save(participant);
    }

    @Override
    public Optional<Participant> partialUpdate(Participant participant) {
        log.debug("Request to partially update Participant : {}", participant);

        return participantRepository
            .findById(participant.getId())
            .map(existingParticipant -> {
                if (participant.getPrenom() != null) {
                    existingParticipant.setPrenom(participant.getPrenom());
                }
                if (participant.getNom() != null) {
                    existingParticipant.setNom(participant.getNom());
                }
                if (participant.getEmail() != null) {
                    existingParticipant.setEmail(participant.getEmail());
                }
                if (participant.getPassword() != null) {
                    existingParticipant.setPassword(participant.getPassword());
                }
                if (participant.getTelephone() != null) {
                    existingParticipant.setTelephone(participant.getTelephone());
                }
                if (participant.getDateDeNaissance() != null) {
                    existingParticipant.setDateDeNaissance(participant.getDateDeNaissance());
                }
                if (participant.getSexe() != null) {
                    existingParticipant.setSexe(participant.getSexe());
                }
                if (participant.getCin() != null) {
                    existingParticipant.setCin(participant.getCin());
                }

                return existingParticipant;
            })
            .map(participantRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Participant> findAll(Pageable pageable) {
        log.debug("Request to get all Participants");
        return participantRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Participant> findOne(Long id) {
        log.debug("Request to get Participant : {}", id);
        return participantRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Participant : {}", id);
        participantRepository.deleteById(id);
    }
}
