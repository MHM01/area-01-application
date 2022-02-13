package com.area.repository;

import com.area.domain.Inscription;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Inscription entity.
 */
@SuppressWarnings("unused")
@Repository
public interface InscriptionRepository extends JpaRepository<Inscription, Long> {}
