//package com.area.web.rest;
//
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.hamcrest.Matchers.hasItem;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
//
//import com.area.IntegrationTest;
//import com.area.domain.Participant;
//import com.area.domain.enumeration.Sexe;
//import com.area.repository.ParticipantRepository;
//import java.time.Instant;
//import java.time.temporal.ChronoUnit;
//import java.util.List;
//import java.util.Random;
//import java.util.concurrent.atomic.AtomicLong;
//import javax.persistence.EntityManager;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.http.MediaType;
//import org.springframework.security.test.context.support.WithMockUser;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.transaction.annotation.Transactional;
//
///**
// * Integration tests for the {@link ParticipantResource} REST controller.
// */
//@IntegrationTest
//@AutoConfigureMockMvc
//@WithMockUser
//class ParticipantResourceIT {
//
//    private static final String DEFAULT_PRENOM = "AAAAAAAAAA";
//    private static final String UPDATED_PRENOM = "BBBBBBBBBB";
//
//    private static final String DEFAULT_NOM = "AAAAAAAAAA";
//    private static final String UPDATED_NOM = "BBBBBBBBBB";
//
//    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
//    private static final String UPDATED_EMAIL = "BBBBBBBBBB";
//
//    private static final String DEFAULT_PASSWORD = "AAAAAAAAAA";
//    private static final String UPDATED_PASSWORD = "BBBBBBBBBB";
//
//    private static final String DEFAULT_TELEPHONE = "AAAAAAAAAA";
//    private static final String UPDATED_TELEPHONE = "BBBBBBBBBB";
//
//    private static final Instant DEFAULT_DATE_DE_NAISSANCE = Instant.ofEpochMilli(0L);
//    private static final Instant UPDATED_DATE_DE_NAISSANCE = Instant.now().truncatedTo(ChronoUnit.MILLIS);
//
//    private static final Sexe DEFAULT_SEXE = Sexe.MALE;
//    private static final Sexe UPDATED_SEXE = Sexe.FEMALE;
//
//    private static final String DEFAULT_CIN = "AAAAAAAAAA";
//    private static final String UPDATED_CIN = "BBBBBBBBBB";
//
//    private static final String ENTITY_API_URL = "/api/participants";
//    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";
//
//    private static Random random = new Random();
//    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));
//
//    @Autowired
//    private ParticipantRepository participantRepository;
//
//    @Autowired
//    private EntityManager em;
//
//    @Autowired
//    private MockMvc restParticipantMockMvc;
//
//    private Participant participant;
//
//    /**
//     * Create an entity for this test.
//     *
//     * This is a static method, as tests for other entities might also need it,
//     * if they test an entity which requires the current entity.
//     */
//    public static Participant createEntity(EntityManager em) {
//        Participant participant = new Participant()
//            .prenom(DEFAULT_PRENOM)
//            .nom(DEFAULT_NOM)
//            .email(DEFAULT_EMAIL)
//            .password(DEFAULT_PASSWORD)
//            .telephone(DEFAULT_TELEPHONE)
//            .dateDeNaissance(DEFAULT_DATE_DE_NAISSANCE)
//            .sexe(DEFAULT_SEXE)
//            .cin(DEFAULT_CIN);
//        return participant;
//    }
//
//    /**
//     * Create an updated entity for this test.
//     *
//     * This is a static method, as tests for other entities might also need it,
//     * if they test an entity which requires the current entity.
//     */
//    public static Participant createUpdatedEntity(EntityManager em) {
//        Participant participant = new Participant()
//            .prenom(UPDATED_PRENOM)
//            .nom(UPDATED_NOM)
//            .email(UPDATED_EMAIL)
//            .password(UPDATED_PASSWORD)
//            .telephone(UPDATED_TELEPHONE)
//            .dateDeNaissance(UPDATED_DATE_DE_NAISSANCE)
//            .sexe(UPDATED_SEXE)
//            .cin(UPDATED_CIN);
//        return participant;
//    }
//
//    @BeforeEach
//    public void initTest() {
//        participant = createEntity(em);
//    }
//
//    @Test
//    @Transactional
//    void createParticipant() throws Exception {
//        int databaseSizeBeforeCreate = participantRepository.findAll().size();
//        // Create the Participant
//        restParticipantMockMvc
//            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(participant)))
//            .andExpect(status().isCreated());
//
//        // Validate the Participant in the database
//        List<Participant> participantList = participantRepository.findAll();
//        assertThat(participantList).hasSize(databaseSizeBeforeCreate + 1);
//        Participant testParticipant = participantList.get(participantList.size() - 1);
//        assertThat(testParticipant.getPrenom()).isEqualTo(DEFAULT_PRENOM);
//        assertThat(testParticipant.getNom()).isEqualTo(DEFAULT_NOM);
//        assertThat(testParticipant.getEmail()).isEqualTo(DEFAULT_EMAIL);
//        assertThat(testParticipant.getPassword()).isEqualTo(DEFAULT_PASSWORD);
//        assertThat(testParticipant.getTelephone()).isEqualTo(DEFAULT_TELEPHONE);
//        assertThat(testParticipant.getDateDeNaissance()).isEqualTo(DEFAULT_DATE_DE_NAISSANCE);
//        assertThat(testParticipant.getSexe()).isEqualTo(DEFAULT_SEXE);
//        assertThat(testParticipant.getCin()).isEqualTo(DEFAULT_CIN);
//    }
//
//    @Test
//    @Transactional
//    void createParticipantWithExistingId() throws Exception {
//        // Create the Participant with an existing ID
//        participant.setId(1L);
//
//        int databaseSizeBeforeCreate = participantRepository.findAll().size();
//
//        // An entity with an existing ID cannot be created, so this API call must fail
//        restParticipantMockMvc
//            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(participant)))
//            .andExpect(status().isBadRequest());
//
//        // Validate the Participant in the database
//        List<Participant> participantList = participantRepository.findAll();
//        assertThat(participantList).hasSize(databaseSizeBeforeCreate);
//    }
//
//    @Test
//    @Transactional
//    void getAllParticipants() throws Exception {
//        // Initialize the database
//        participantRepository.saveAndFlush(participant);
//
//        // Get all the participantList
//        restParticipantMockMvc
//            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
//            .andExpect(status().isOk())
//            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
//            .andExpect(jsonPath("$.[*].id").value(hasItem(participant.getId().intValue())))
//            .andExpect(jsonPath("$.[*].prenom").value(hasItem(DEFAULT_PRENOM)))
//            .andExpect(jsonPath("$.[*].nom").value(hasItem(DEFAULT_NOM)))
//            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
//            .andExpect(jsonPath("$.[*].password").value(hasItem(DEFAULT_PASSWORD)))
//            .andExpect(jsonPath("$.[*].telephone").value(hasItem(DEFAULT_TELEPHONE)))
//            .andExpect(jsonPath("$.[*].dateDeNaissance").value(hasItem(DEFAULT_DATE_DE_NAISSANCE.toString())))
//            .andExpect(jsonPath("$.[*].sexe").value(hasItem(DEFAULT_SEXE.toString())))
//            .andExpect(jsonPath("$.[*].cin").value(hasItem(DEFAULT_CIN)));
//    }
//
//    @Test
//    @Transactional
//    void getParticipant() throws Exception {
//        // Initialize the database
//        participantRepository.saveAndFlush(participant);
//
//        // Get the participant
//        restParticipantMockMvc
//            .perform(get(ENTITY_API_URL_ID, participant.getId()))
//            .andExpect(status().isOk())
//            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
//            .andExpect(jsonPath("$.id").value(participant.getId().intValue()))
//            .andExpect(jsonPath("$.prenom").value(DEFAULT_PRENOM))
//            .andExpect(jsonPath("$.nom").value(DEFAULT_NOM))
//            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL))
//            .andExpect(jsonPath("$.password").value(DEFAULT_PASSWORD))
//            .andExpect(jsonPath("$.telephone").value(DEFAULT_TELEPHONE))
//            .andExpect(jsonPath("$.dateDeNaissance").value(DEFAULT_DATE_DE_NAISSANCE.toString()))
//            .andExpect(jsonPath("$.sexe").value(DEFAULT_SEXE.toString()))
//            .andExpect(jsonPath("$.cin").value(DEFAULT_CIN));
//    }
//
//    @Test
//    @Transactional
//    void getNonExistingParticipant() throws Exception {
//        // Get the participant
//        restParticipantMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
//    }
//
//    @Test
//    @Transactional
//    void putNewParticipant() throws Exception {
//        // Initialize the database
//        participantRepository.saveAndFlush(participant);
//
//        int databaseSizeBeforeUpdate = participantRepository.findAll().size();
//
//        // Update the participant
//        Participant updatedParticipant = participantRepository.findById(participant.getId()).get();
//        // Disconnect from session so that the updates on updatedParticipant are not directly saved in db
//        em.detach(updatedParticipant);
//        updatedParticipant
//            .prenom(UPDATED_PRENOM)
//            .nom(UPDATED_NOM)
//            .email(UPDATED_EMAIL)
//            .password(UPDATED_PASSWORD)
//            .telephone(UPDATED_TELEPHONE)
//            .dateDeNaissance(UPDATED_DATE_DE_NAISSANCE)
//            .sexe(UPDATED_SEXE)
//            .cin(UPDATED_CIN);
//
//        restParticipantMockMvc
//            .perform(
//                put(ENTITY_API_URL_ID, updatedParticipant.getId())
//                    .contentType(MediaType.APPLICATION_JSON)
//                    .content(TestUtil.convertObjectToJsonBytes(updatedParticipant))
//            )
//            .andExpect(status().isOk());
//
//        // Validate the Participant in the database
//        List<Participant> participantList = participantRepository.findAll();
//        assertThat(participantList).hasSize(databaseSizeBeforeUpdate);
//        Participant testParticipant = participantList.get(participantList.size() - 1);
//        assertThat(testParticipant.getPrenom()).isEqualTo(UPDATED_PRENOM);
//        assertThat(testParticipant.getNom()).isEqualTo(UPDATED_NOM);
//        assertThat(testParticipant.getEmail()).isEqualTo(UPDATED_EMAIL);
//        assertThat(testParticipant.getPassword()).isEqualTo(UPDATED_PASSWORD);
//        assertThat(testParticipant.getTelephone()).isEqualTo(UPDATED_TELEPHONE);
//        assertThat(testParticipant.getDateDeNaissance()).isEqualTo(UPDATED_DATE_DE_NAISSANCE);
//        assertThat(testParticipant.getSexe()).isEqualTo(UPDATED_SEXE);
//        assertThat(testParticipant.getCin()).isEqualTo(UPDATED_CIN);
//    }
//
//    @Test
//    @Transactional
//    void putNonExistingParticipant() throws Exception {
//        int databaseSizeBeforeUpdate = participantRepository.findAll().size();
//        participant.setId(count.incrementAndGet());
//
//        // If the entity doesn't have an ID, it will throw BadRequestAlertException
//        restParticipantMockMvc
//            .perform(
//                put(ENTITY_API_URL_ID, participant.getId())
//                    .contentType(MediaType.APPLICATION_JSON)
//                    .content(TestUtil.convertObjectToJsonBytes(participant))
//            )
//            .andExpect(status().isBadRequest());
//
//        // Validate the Participant in the database
//        List<Participant> participantList = participantRepository.findAll();
//        assertThat(participantList).hasSize(databaseSizeBeforeUpdate);
//    }
//
//    @Test
//    @Transactional
//    void putWithIdMismatchParticipant() throws Exception {
//        int databaseSizeBeforeUpdate = participantRepository.findAll().size();
//        participant.setId(count.incrementAndGet());
//
//        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
//        restParticipantMockMvc
//            .perform(
//                put(ENTITY_API_URL_ID, count.incrementAndGet())
//                    .contentType(MediaType.APPLICATION_JSON)
//                    .content(TestUtil.convertObjectToJsonBytes(participant))
//            )
//            .andExpect(status().isBadRequest());
//
//        // Validate the Participant in the database
//        List<Participant> participantList = participantRepository.findAll();
//        assertThat(participantList).hasSize(databaseSizeBeforeUpdate);
//    }
//
//    @Test
//    @Transactional
//    void putWithMissingIdPathParamParticipant() throws Exception {
//        int databaseSizeBeforeUpdate = participantRepository.findAll().size();
//        participant.setId(count.incrementAndGet());
//
//        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
//        restParticipantMockMvc
//            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(participant)))
//            .andExpect(status().isMethodNotAllowed());
//
//        // Validate the Participant in the database
//        List<Participant> participantList = participantRepository.findAll();
//        assertThat(participantList).hasSize(databaseSizeBeforeUpdate);
//    }
//
//    @Test
//    @Transactional
//    void partialUpdateParticipantWithPatch() throws Exception {
//        // Initialize the database
//        participantRepository.saveAndFlush(participant);
//
//        int databaseSizeBeforeUpdate = participantRepository.findAll().size();
//
//        // Update the participant using partial update
//        Participant partialUpdatedParticipant = new Participant();
//        partialUpdatedParticipant.setId(participant.getId());
//
//        partialUpdatedParticipant.email(UPDATED_EMAIL).password(UPDATED_PASSWORD);
//
//        restParticipantMockMvc
//            .perform(
//                patch(ENTITY_API_URL_ID, partialUpdatedParticipant.getId())
//                    .contentType("application/merge-patch+json")
//                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedParticipant))
//            )
//            .andExpect(status().isOk());
//
//        // Validate the Participant in the database
//        List<Participant> participantList = participantRepository.findAll();
//        assertThat(participantList).hasSize(databaseSizeBeforeUpdate);
//        Participant testParticipant = participantList.get(participantList.size() - 1);
//        assertThat(testParticipant.getPrenom()).isEqualTo(DEFAULT_PRENOM);
//        assertThat(testParticipant.getNom()).isEqualTo(DEFAULT_NOM);
//        assertThat(testParticipant.getEmail()).isEqualTo(UPDATED_EMAIL);
//        assertThat(testParticipant.getPassword()).isEqualTo(UPDATED_PASSWORD);
//        assertThat(testParticipant.getTelephone()).isEqualTo(DEFAULT_TELEPHONE);
//        assertThat(testParticipant.getDateDeNaissance()).isEqualTo(DEFAULT_DATE_DE_NAISSANCE);
//        assertThat(testParticipant.getSexe()).isEqualTo(DEFAULT_SEXE);
//        assertThat(testParticipant.getCin()).isEqualTo(DEFAULT_CIN);
//    }
//
//    @Test
//    @Transactional
//    void fullUpdateParticipantWithPatch() throws Exception {
//        // Initialize the database
//        participantRepository.saveAndFlush(participant);
//
//        int databaseSizeBeforeUpdate = participantRepository.findAll().size();
//
//        // Update the participant using partial update
//        Participant partialUpdatedParticipant = new Participant();
//        partialUpdatedParticipant.setId(participant.getId());
//
//        partialUpdatedParticipant
//            .prenom(UPDATED_PRENOM)
//            .nom(UPDATED_NOM)
//            .email(UPDATED_EMAIL)
//            .password(UPDATED_PASSWORD)
//            .telephone(UPDATED_TELEPHONE)
//            .dateDeNaissance(UPDATED_DATE_DE_NAISSANCE)
//            .sexe(UPDATED_SEXE)
//            .cin(UPDATED_CIN);
//
//        restParticipantMockMvc
//            .perform(
//                patch(ENTITY_API_URL_ID, partialUpdatedParticipant.getId())
//                    .contentType("application/merge-patch+json")
//                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedParticipant))
//            )
//            .andExpect(status().isOk());
//
//        // Validate the Participant in the database
//        List<Participant> participantList = participantRepository.findAll();
//        assertThat(participantList).hasSize(databaseSizeBeforeUpdate);
//        Participant testParticipant = participantList.get(participantList.size() - 1);
//        assertThat(testParticipant.getPrenom()).isEqualTo(UPDATED_PRENOM);
//        assertThat(testParticipant.getNom()).isEqualTo(UPDATED_NOM);
//        assertThat(testParticipant.getEmail()).isEqualTo(UPDATED_EMAIL);
//        assertThat(testParticipant.getPassword()).isEqualTo(UPDATED_PASSWORD);
//        assertThat(testParticipant.getTelephone()).isEqualTo(UPDATED_TELEPHONE);
//        assertThat(testParticipant.getDateDeNaissance()).isEqualTo(UPDATED_DATE_DE_NAISSANCE);
//        assertThat(testParticipant.getSexe()).isEqualTo(UPDATED_SEXE);
//        assertThat(testParticipant.getCin()).isEqualTo(UPDATED_CIN);
//    }
//
//    @Test
//    @Transactional
//    void patchNonExistingParticipant() throws Exception {
//        int databaseSizeBeforeUpdate = participantRepository.findAll().size();
//        participant.setId(count.incrementAndGet());
//
//        // If the entity doesn't have an ID, it will throw BadRequestAlertException
//        restParticipantMockMvc
//            .perform(
//                patch(ENTITY_API_URL_ID, participant.getId())
//                    .contentType("application/merge-patch+json")
//                    .content(TestUtil.convertObjectToJsonBytes(participant))
//            )
//            .andExpect(status().isBadRequest());
//
//        // Validate the Participant in the database
//        List<Participant> participantList = participantRepository.findAll();
//        assertThat(participantList).hasSize(databaseSizeBeforeUpdate);
//    }
//
//    @Test
//    @Transactional
//    void patchWithIdMismatchParticipant() throws Exception {
//        int databaseSizeBeforeUpdate = participantRepository.findAll().size();
//        participant.setId(count.incrementAndGet());
//
//        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
//        restParticipantMockMvc
//            .perform(
//                patch(ENTITY_API_URL_ID, count.incrementAndGet())
//                    .contentType("application/merge-patch+json")
//                    .content(TestUtil.convertObjectToJsonBytes(participant))
//            )
//            .andExpect(status().isBadRequest());
//
//        // Validate the Participant in the database
//        List<Participant> participantList = participantRepository.findAll();
//        assertThat(participantList).hasSize(databaseSizeBeforeUpdate);
//    }
//
//    @Test
//    @Transactional
//    void patchWithMissingIdPathParamParticipant() throws Exception {
//        int databaseSizeBeforeUpdate = participantRepository.findAll().size();
//        participant.setId(count.incrementAndGet());
//
//        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
//        restParticipantMockMvc
//            .perform(
//                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(participant))
//            )
//            .andExpect(status().isMethodNotAllowed());
//
//        // Validate the Participant in the database
//        List<Participant> participantList = participantRepository.findAll();
//        assertThat(participantList).hasSize(databaseSizeBeforeUpdate);
//    }
//
//    @Test
//    @Transactional
//    void deleteParticipant() throws Exception {
//        // Initialize the database
//        participantRepository.saveAndFlush(participant);
//
//        int databaseSizeBeforeDelete = participantRepository.findAll().size();
//
//        // Delete the participant
//        restParticipantMockMvc
//            .perform(delete(ENTITY_API_URL_ID, participant.getId()).accept(MediaType.APPLICATION_JSON))
//            .andExpect(status().isNoContent());
//
//        // Validate the database contains one less item
//        List<Participant> participantList = participantRepository.findAll();
//        assertThat(participantList).hasSize(databaseSizeBeforeDelete - 1);
//    }
//}
