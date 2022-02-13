package com.area.repository;

import com.area.domain.Participant;
import com.area.domain.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Participant entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ParticipantRepository extends JpaRepository<Participant, Long> {
    Optional<Participant> findOneByCin(String cin);
    Optional<Participant> findOneByEmailIgnoreCase(String email);
}
