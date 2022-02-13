package com.area.service.impl;

import com.area.domain.Groupe;
import com.area.domain.Inscription;
import com.area.domain.Participant;
import com.area.domain.enumeration.Statut;
import com.area.domain.enumeration.Type;
import com.area.repository.FaculteRepository;
import com.area.repository.ParticipantRepository;
import com.area.service.*;
import com.area.web.rest.vm.ParticipantVM;
import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
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

    private final FaculteRepository faculteRepository;

    private final PasswordEncoder passwordEncoder;

    public ParticipantServiceImpl(
        ParticipantRepository participantRepository,
        FaculteRepository faculteRepository,
        PasswordEncoder passwordEncoder
    ) {
        this.participantRepository = participantRepository;
        this.faculteRepository = faculteRepository;
        this.passwordEncoder = passwordEncoder;
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

    @Override
    public Participant registerParticipant(ParticipantVM participantVM, String password) {
        participantRepository
            .findOneByCin(participantVM.getCin())
            .ifPresent(existingUser -> {
                throw new UsernameAlreadyUsedException();
            });
        participantRepository
            .findOneByEmailIgnoreCase(participantVM.getEmail())
            .ifPresent(existingUser -> {
                throw new EmailAlreadyUsedException();
            });

        Participant newUser = new Participant();
        String encryptedPassword = passwordEncoder.encode(password);
        newUser.setCin(participantVM.getCin());
        // new user gets initially a generated password
        newUser.setPassword(encryptedPassword);
        newUser.setPrenom(participantVM.getPrenom());
        newUser.setNom(participantVM.getNom());
        if (participantVM.getEmail() != null) {
            newUser.setEmail(participantVM.getEmail().toLowerCase());
        }
        newUser.setDateDeNaissance(participantVM.getDateDeNaissance());
        newUser.setSexe(participantVM.getSexe());
        // Get Faculté
        faculteRepository
            .findOneByNom(participantVM.getNomFaculte())
            .ifPresent(faculte -> {
                newUser.setFaculte(faculte);
            });

        Inscription inscription = new Inscription();
        inscription.setType(participantVM.getTypeDeparticipation());
        inscription.statut(Statut.NON_PAYE);
        newUser.setInscription(inscription);

        //Group and participation type
        if (Type.COMPETITEUR.equals(participantVM.getTypeDeparticipation())) {
            // Add participation group
            String participantOfGroupe = participantVM.getParticipantOfGroupe();
            if (Objects.nonNull(participantOfGroupe)) {
                Groupe groupe = new Groupe();
                Arrays
                    .stream(participantOfGroupe.split("#"))
                    .forEach(cin -> {
                        participantRepository
                            .findOneByCin(cin)
                            .ifPresent(participant -> {
                                if (Objects.nonNull(participant.getGroupe())) {
                                    throw new ParticipantAlreadyInGroupException(cin);
                                }
                                groupe.addParticipant(participant);
                            });
                    });
                newUser.setGroupe(groupe);
            }

            throw new AtLeastOneParticpantRequiredInAgroupeException();
        }
        participantRepository.save(newUser);
        log.debug("Created Information for Participant: {}", newUser);
        return newUser;
    }
}
