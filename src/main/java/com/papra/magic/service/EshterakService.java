package com.papra.magic.service;

import com.papra.magic.service.dto.EshterakDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.papra.magic.domain.Eshterak}.
 */
public interface EshterakService {
    /**
     * Save a eshterak.
     *
     * @param eshterakDTO the entity to save.
     * @return the persisted entity.
     */
    EshterakDTO save(EshterakDTO eshterakDTO);

    /**
     * Partially updates a eshterak.
     *
     * @param eshterakDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<EshterakDTO> partialUpdate(EshterakDTO eshterakDTO);

    /**
     * Get all the eshteraks.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<EshterakDTO> findAll(Pageable pageable);

    /**
     * Get the "id" eshterak.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<EshterakDTO> findOne(Long id);

    /**
     * Delete the "id" eshterak.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
