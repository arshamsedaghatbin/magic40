package com.papra.magic.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.papra.magic.IntegrationTest;
import com.papra.magic.domain.Eshterak;
import com.papra.magic.domain.enumeration.UntilType;
import com.papra.magic.repository.EshterakRepository;
import com.papra.magic.service.dto.EshterakDTO;
import com.papra.magic.service.mapper.EshterakMapper;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link EshterakResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class EshterakResourceIT {

    private static final String DEFAULT_TEXT = "AAAAAAAAAA";
    private static final String UPDATED_TEXT = "BBBBBBBBBB";

    private static final Long DEFAULT_AMOUNT = 1L;
    private static final Long UPDATED_AMOUNT = 2L;

    private static final Long DEFAULT_UNTIL = 1L;
    private static final Long UPDATED_UNTIL = 2L;

    private static final UntilType DEFAULT_TYPE = UntilType.MONTH;
    private static final UntilType UPDATED_TYPE = UntilType.DAY;

    private static final String ENTITY_API_URL = "/api/eshteraks";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private EshterakRepository eshterakRepository;

    @Autowired
    private EshterakMapper eshterakMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restEshterakMockMvc;

    private Eshterak eshterak;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Eshterak createEntity(EntityManager em) {
        Eshterak eshterak = new Eshterak().text(DEFAULT_TEXT).amount(DEFAULT_AMOUNT).until(DEFAULT_UNTIL).type(DEFAULT_TYPE);
        return eshterak;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Eshterak createUpdatedEntity(EntityManager em) {
        Eshterak eshterak = new Eshterak().text(UPDATED_TEXT).amount(UPDATED_AMOUNT).until(UPDATED_UNTIL).type(UPDATED_TYPE);
        return eshterak;
    }

    @BeforeEach
    public void initTest() {
        eshterak = createEntity(em);
    }

    @Test
    @Transactional
    void createEshterak() throws Exception {
        int databaseSizeBeforeCreate = eshterakRepository.findAll().size();
        // Create the Eshterak
        EshterakDTO eshterakDTO = eshterakMapper.toDto(eshterak);
        restEshterakMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(eshterakDTO)))
            .andExpect(status().isCreated());

        // Validate the Eshterak in the database
        List<Eshterak> eshterakList = eshterakRepository.findAll();
        assertThat(eshterakList).hasSize(databaseSizeBeforeCreate + 1);
        Eshterak testEshterak = eshterakList.get(eshterakList.size() - 1);
        assertThat(testEshterak.getText()).isEqualTo(DEFAULT_TEXT);
        assertThat(testEshterak.getAmount()).isEqualTo(DEFAULT_AMOUNT);
        assertThat(testEshterak.getUntil()).isEqualTo(DEFAULT_UNTIL);
        assertThat(testEshterak.getType()).isEqualTo(DEFAULT_TYPE);
    }

    @Test
    @Transactional
    void createEshterakWithExistingId() throws Exception {
        // Create the Eshterak with an existing ID
        eshterak.setId(1L);
        EshterakDTO eshterakDTO = eshterakMapper.toDto(eshterak);

        int databaseSizeBeforeCreate = eshterakRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restEshterakMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(eshterakDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Eshterak in the database
        List<Eshterak> eshterakList = eshterakRepository.findAll();
        assertThat(eshterakList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllEshteraks() throws Exception {
        // Initialize the database
        eshterakRepository.saveAndFlush(eshterak);

        // Get all the eshterakList
        restEshterakMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(eshterak.getId().intValue())))
            .andExpect(jsonPath("$.[*].text").value(hasItem(DEFAULT_TEXT)))
            .andExpect(jsonPath("$.[*].amount").value(hasItem(DEFAULT_AMOUNT.intValue())))
            .andExpect(jsonPath("$.[*].until").value(hasItem(DEFAULT_UNTIL.intValue())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())));
    }

    @Test
    @Transactional
    void getEshterak() throws Exception {
        // Initialize the database
        eshterakRepository.saveAndFlush(eshterak);

        // Get the eshterak
        restEshterakMockMvc
            .perform(get(ENTITY_API_URL_ID, eshterak.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(eshterak.getId().intValue()))
            .andExpect(jsonPath("$.text").value(DEFAULT_TEXT))
            .andExpect(jsonPath("$.amount").value(DEFAULT_AMOUNT.intValue()))
            .andExpect(jsonPath("$.until").value(DEFAULT_UNTIL.intValue()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()));
    }

    @Test
    @Transactional
    void getNonExistingEshterak() throws Exception {
        // Get the eshterak
        restEshterakMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewEshterak() throws Exception {
        // Initialize the database
        eshterakRepository.saveAndFlush(eshterak);

        int databaseSizeBeforeUpdate = eshterakRepository.findAll().size();

        // Update the eshterak
        Eshterak updatedEshterak = eshterakRepository.findById(eshterak.getId()).get();
        // Disconnect from session so that the updates on updatedEshterak are not directly saved in db
        em.detach(updatedEshterak);
        updatedEshterak.text(UPDATED_TEXT).amount(UPDATED_AMOUNT).until(UPDATED_UNTIL).type(UPDATED_TYPE);
        EshterakDTO eshterakDTO = eshterakMapper.toDto(updatedEshterak);

        restEshterakMockMvc
            .perform(
                put(ENTITY_API_URL_ID, eshterakDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(eshterakDTO))
            )
            .andExpect(status().isOk());

        // Validate the Eshterak in the database
        List<Eshterak> eshterakList = eshterakRepository.findAll();
        assertThat(eshterakList).hasSize(databaseSizeBeforeUpdate);
        Eshterak testEshterak = eshterakList.get(eshterakList.size() - 1);
        assertThat(testEshterak.getText()).isEqualTo(UPDATED_TEXT);
        assertThat(testEshterak.getAmount()).isEqualTo(UPDATED_AMOUNT);
        assertThat(testEshterak.getUntil()).isEqualTo(UPDATED_UNTIL);
        assertThat(testEshterak.getType()).isEqualTo(UPDATED_TYPE);
    }

    @Test
    @Transactional
    void putNonExistingEshterak() throws Exception {
        int databaseSizeBeforeUpdate = eshterakRepository.findAll().size();
        eshterak.setId(count.incrementAndGet());

        // Create the Eshterak
        EshterakDTO eshterakDTO = eshterakMapper.toDto(eshterak);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEshterakMockMvc
            .perform(
                put(ENTITY_API_URL_ID, eshterakDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(eshterakDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Eshterak in the database
        List<Eshterak> eshterakList = eshterakRepository.findAll();
        assertThat(eshterakList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchEshterak() throws Exception {
        int databaseSizeBeforeUpdate = eshterakRepository.findAll().size();
        eshterak.setId(count.incrementAndGet());

        // Create the Eshterak
        EshterakDTO eshterakDTO = eshterakMapper.toDto(eshterak);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEshterakMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(eshterakDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Eshterak in the database
        List<Eshterak> eshterakList = eshterakRepository.findAll();
        assertThat(eshterakList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamEshterak() throws Exception {
        int databaseSizeBeforeUpdate = eshterakRepository.findAll().size();
        eshterak.setId(count.incrementAndGet());

        // Create the Eshterak
        EshterakDTO eshterakDTO = eshterakMapper.toDto(eshterak);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEshterakMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(eshterakDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Eshterak in the database
        List<Eshterak> eshterakList = eshterakRepository.findAll();
        assertThat(eshterakList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateEshterakWithPatch() throws Exception {
        // Initialize the database
        eshterakRepository.saveAndFlush(eshterak);

        int databaseSizeBeforeUpdate = eshterakRepository.findAll().size();

        // Update the eshterak using partial update
        Eshterak partialUpdatedEshterak = new Eshterak();
        partialUpdatedEshterak.setId(eshterak.getId());

        partialUpdatedEshterak.text(UPDATED_TEXT).until(UPDATED_UNTIL);

        restEshterakMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEshterak.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedEshterak))
            )
            .andExpect(status().isOk());

        // Validate the Eshterak in the database
        List<Eshterak> eshterakList = eshterakRepository.findAll();
        assertThat(eshterakList).hasSize(databaseSizeBeforeUpdate);
        Eshterak testEshterak = eshterakList.get(eshterakList.size() - 1);
        assertThat(testEshterak.getText()).isEqualTo(UPDATED_TEXT);
        assertThat(testEshterak.getAmount()).isEqualTo(DEFAULT_AMOUNT);
        assertThat(testEshterak.getUntil()).isEqualTo(UPDATED_UNTIL);
        assertThat(testEshterak.getType()).isEqualTo(DEFAULT_TYPE);
    }

    @Test
    @Transactional
    void fullUpdateEshterakWithPatch() throws Exception {
        // Initialize the database
        eshterakRepository.saveAndFlush(eshterak);

        int databaseSizeBeforeUpdate = eshterakRepository.findAll().size();

        // Update the eshterak using partial update
        Eshterak partialUpdatedEshterak = new Eshterak();
        partialUpdatedEshterak.setId(eshterak.getId());

        partialUpdatedEshterak.text(UPDATED_TEXT).amount(UPDATED_AMOUNT).until(UPDATED_UNTIL).type(UPDATED_TYPE);

        restEshterakMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEshterak.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedEshterak))
            )
            .andExpect(status().isOk());

        // Validate the Eshterak in the database
        List<Eshterak> eshterakList = eshterakRepository.findAll();
        assertThat(eshterakList).hasSize(databaseSizeBeforeUpdate);
        Eshterak testEshterak = eshterakList.get(eshterakList.size() - 1);
        assertThat(testEshterak.getText()).isEqualTo(UPDATED_TEXT);
        assertThat(testEshterak.getAmount()).isEqualTo(UPDATED_AMOUNT);
        assertThat(testEshterak.getUntil()).isEqualTo(UPDATED_UNTIL);
        assertThat(testEshterak.getType()).isEqualTo(UPDATED_TYPE);
    }

    @Test
    @Transactional
    void patchNonExistingEshterak() throws Exception {
        int databaseSizeBeforeUpdate = eshterakRepository.findAll().size();
        eshterak.setId(count.incrementAndGet());

        // Create the Eshterak
        EshterakDTO eshterakDTO = eshterakMapper.toDto(eshterak);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEshterakMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, eshterakDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(eshterakDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Eshterak in the database
        List<Eshterak> eshterakList = eshterakRepository.findAll();
        assertThat(eshterakList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchEshterak() throws Exception {
        int databaseSizeBeforeUpdate = eshterakRepository.findAll().size();
        eshterak.setId(count.incrementAndGet());

        // Create the Eshterak
        EshterakDTO eshterakDTO = eshterakMapper.toDto(eshterak);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEshterakMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(eshterakDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Eshterak in the database
        List<Eshterak> eshterakList = eshterakRepository.findAll();
        assertThat(eshterakList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamEshterak() throws Exception {
        int databaseSizeBeforeUpdate = eshterakRepository.findAll().size();
        eshterak.setId(count.incrementAndGet());

        // Create the Eshterak
        EshterakDTO eshterakDTO = eshterakMapper.toDto(eshterak);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEshterakMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(eshterakDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Eshterak in the database
        List<Eshterak> eshterakList = eshterakRepository.findAll();
        assertThat(eshterakList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteEshterak() throws Exception {
        // Initialize the database
        eshterakRepository.saveAndFlush(eshterak);

        int databaseSizeBeforeDelete = eshterakRepository.findAll().size();

        // Delete the eshterak
        restEshterakMockMvc
            .perform(delete(ENTITY_API_URL_ID, eshterak.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Eshterak> eshterakList = eshterakRepository.findAll();
        assertThat(eshterakList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
