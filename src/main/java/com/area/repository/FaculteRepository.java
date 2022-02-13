package com.area.repository;

import com.area.domain.Faculte;
import com.area.domain.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Faculte entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FaculteRepository extends JpaRepository<Faculte, Long> {
    Optional<Faculte> findOneByNom(String nom);
}
