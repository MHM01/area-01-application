package com.area.service;

import com.area.domain.Faculte;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link Faculte}.
 */
public interface FaculteService {
    /**
     * Save a faculte.
     *
     * @param faculte the entity to save.
     * @return the persisted entity.
     */
    Faculte save(Faculte faculte);

    /**
     * Partially updates a faculte.
     *
     * @param faculte the entity to update partially.
     * @return the persisted entity.
     */
    Optional<Faculte> partialUpdate(Faculte faculte);

    /**
     * Get all the facultes.
     *
     * @return the list of entities.
     */
    List<Faculte> findAll();

    /**
     * Get the "id" faculte.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Faculte> findOne(Long id);

    /**
     * Delete the "id" faculte.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
