package com.area.service;

import com.area.domain.Participant;
import com.area.domain.User;
import com.area.service.dto.AdminUserDTO;
import com.area.web.rest.vm.ParticipantVM;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link Participant}.
 */
public interface ParticipantService {
    /**
     * Save a participant.
     *
     * @param participant the entity to save.
     * @return the persisted entity.
     */
    Participant save(Participant participant);

    /**
     * Partially updates a participant.
     *
     * @param participant the entity to update partially.
     * @return the persisted entity.
     */
    Optional<Participant> partialUpdate(Participant participant);

    /**
     * Get all the participants.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Participant> findAll(Pageable pageable);

    /**
     * Get the "id" participant.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Participant> findOne(Long id);

    /**
     * Delete the "id" participant.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    Participant registerParticipant(ParticipantVM userDTO, String password);
}
