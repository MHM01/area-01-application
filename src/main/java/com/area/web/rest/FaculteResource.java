package com.area.web.rest;

import com.area.domain.Faculte;
import com.area.repository.FaculteRepository;
import com.area.service.FaculteService;
import com.area.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.area.domain.Faculte}.
 */
@RestController
@RequestMapping("/api")
public class FaculteResource {

    private final Logger log = LoggerFactory.getLogger(FaculteResource.class);

    private static final String ENTITY_NAME = "faculte";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final FaculteService faculteService;

    private final FaculteRepository faculteRepository;

    public FaculteResource(FaculteService faculteService, FaculteRepository faculteRepository) {
        this.faculteService = faculteService;
        this.faculteRepository = faculteRepository;
    }

    /**
     * {@code POST  /facultes} : Create a new faculte.
     *
     * @param faculte the faculte to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new faculte, or with status {@code 400 (Bad Request)} if the faculte has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/facultes")
    public ResponseEntity<Faculte> createFaculte(@RequestBody Faculte faculte) throws URISyntaxException {
        log.debug("REST request to save Faculte : {}", faculte);
        if (faculte.getId() != null) {
            throw new BadRequestAlertException("A new faculte cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Faculte result = faculteService.save(faculte);
        return ResponseEntity
            .created(new URI("/api/facultes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /facultes/:id} : Updates an existing faculte.
     *
     * @param id the id of the faculte to save.
     * @param faculte the faculte to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated faculte,
     * or with status {@code 400 (Bad Request)} if the faculte is not valid,
     * or with status {@code 500 (Internal Server Error)} if the faculte couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/facultes/{id}")
    public ResponseEntity<Faculte> updateFaculte(@PathVariable(value = "id", required = false) final Long id, @RequestBody Faculte faculte)
        throws URISyntaxException {
        log.debug("REST request to update Faculte : {}, {}", id, faculte);
        if (faculte.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, faculte.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!faculteRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Faculte result = faculteService.save(faculte);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, faculte.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /facultes/:id} : Partial updates given fields of an existing faculte, field will ignore if it is null
     *
     * @param id the id of the faculte to save.
     * @param faculte the faculte to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated faculte,
     * or with status {@code 400 (Bad Request)} if the faculte is not valid,
     * or with status {@code 404 (Not Found)} if the faculte is not found,
     * or with status {@code 500 (Internal Server Error)} if the faculte couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/facultes/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Faculte> partialUpdateFaculte(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Faculte faculte
    ) throws URISyntaxException {
        log.debug("REST request to partial update Faculte partially : {}, {}", id, faculte);
        if (faculte.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, faculte.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!faculteRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Faculte> result = faculteService.partialUpdate(faculte);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, faculte.getId().toString())
        );
    }

    /**
     * {@code GET  /facultes} : get all the facultes.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of facultes in body.
     */
    @GetMapping("/facultes")
    public List<Faculte> getAllFacultes() {
        log.debug("REST request to get all Facultes");
        return faculteService.findAll();
    }

    /**
     * {@code GET  /facultes/:id} : get the "id" faculte.
     *
     * @param id the id of the faculte to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the faculte, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/facultes/{id}")
    public ResponseEntity<Faculte> getFaculte(@PathVariable Long id) {
        log.debug("REST request to get Faculte : {}", id);
        Optional<Faculte> faculte = faculteService.findOne(id);
        return ResponseUtil.wrapOrNotFound(faculte);
    }

    /**
     * {@code DELETE  /facultes/:id} : delete the "id" faculte.
     *
     * @param id the id of the faculte to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/facultes/{id}")
    public ResponseEntity<Void> deleteFaculte(@PathVariable Long id) {
        log.debug("REST request to delete Faculte : {}", id);
        faculteService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
